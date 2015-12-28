package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import connection.Connection;
import connection.MenuAnalyser;
import logic.BCrypt;

public class Server {
	private final ServerSocket server;
	private Socket socket;
	private DB db;
	ArrayList<Thread> connectionThreads = new ArrayList<Thread>();
	ArrayList<Connection> connections = new ArrayList<Connection>();
	int idIterator;
	ArrayList<GameServer> games = new ArrayList<GameServer>();

	public Server(int port) throws IOException {
		server = new ServerSocket(port);
		db = new DB();
		acceptConnection();
		idIterator = 0;
	}

	private void acceptConnection() {
		try {
			while (true) {
				System.out.println("tuff");
				socket = server.accept();
				createConnection(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createConnection(Socket socket) {
		Connection connection = new Connection(socket, new MenuAnalyser(this,
				idIterator), idIterator);
		idIterator++;
		connections.add(connection);
		Thread connectionThread = new Thread(connection);
		connectionThreads.add(connectionThread);
		connectionThread.start();
	}

	public void registerClient(String name, String pw, String email,
			String mode, int id) {
		String hashed = BCrypt.hashpw(pw, BCrypt.gensalt());
		if (!db.search(name)) {
			db.addUser(name, hashed, email, mode);
		} else {
			// TODO: Fehlermeldung
			connections.get(id).addMessage("Miep");
		}
		System.out.println("Regist");
	}

	public void loginClient(String name, String pw, int id) {
		if (db.getUser(name) != null) {
			if (BCrypt.checkpw(pw, db.getUser(name))) { // hashed: hash steht in
														// der
				// DB
				connections.get(id).addMessage("Success");
				System.out.println("It matches");
			} else {
				System.out.println("It does not match");
			}
		} else {
			System.out.println("It does not match");
		}
	}

	public void logout(int id) {
		connections.get(id).close();
	}
}
