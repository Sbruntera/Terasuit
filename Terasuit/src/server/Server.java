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
	ArrayList<Lobby> lobbys = new ArrayList<Lobby>();

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
		Connection connection = new Connection(socket, this, idIterator);
		connection.setAnalyser(new MenuAnalyser(this, connection, idIterator,
				false));
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
														// der DB
				connections.get(id).addMessage("Success");
				System.out.println("It matches");
			} else {
				System.out.println("It does not match");
			}
		} else {
			System.out.println("It does not match");
		}
	}

	public void diconnect(int id) {
		connections.get(id).close();
	}

	public Lobby[] getLobbylist(Filter filter) {
		ArrayList<Lobby> filteredList = new ArrayList<Lobby>();
		if (filter != null) {
			for (Lobby lobby : lobbys) {
				if ((lobby.getName().contains(filter.getNameContains()))
						&& (filter.getMap() == lobby.getMap())
						&& (filter.getMaxPlayers() >= lobby
								.getNumberOfPlayers() && lobby
								.getNumberOfPlayers() >= filter.getMinPlayers())
						&& !(filter.isNoPassword() && lobby.hasPassword())) {
					filteredList.add(lobby);
				}
			}
		} else {
			filteredList = (ArrayList<Lobby>) lobbys.clone();
		}
		Lobby[] filteredArray = new Lobby[filteredList.size()];

		return filteredArray;
	}

	public void createLobby(Connection connection, String name,
			String password, Map map) {
		lobbys.add(new Lobby(this, connection, name, password, map));
	}

	public void removeLobby(Lobby lobby) {
		lobbys.remove(lobby);
	}

	public Lobby getLobby(byte id) {
		return lobbys.get(id);
	}
}
