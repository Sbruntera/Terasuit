package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import logic.BCrypt;

public class Server extends Thread {
	private final ServerSocket server;
	private Socket socket;
	private DB db;
	ArrayList<Thread> listenerThreads = new ArrayList<Thread>();
	ArrayList<Listener> listeners = new ArrayList<Listener>();
	ArrayList<Thread> writerThreads = new ArrayList<Thread>();
	ArrayList<Writer> writers = new ArrayList<Writer>();
	int idIterator;

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
	
	public void createConnection(Socket socket){
		System.out.println("test");
		ClientConnection clientConnection = new ClientConnection(this, idIterator);
		try {
			Listener listener = new Listener(new BufferedReader(new InputStreamReader(
					socket.getInputStream())), clientConnection);
			listeners.add(listener);
			Thread listenerThread = new Thread();
			listenerThreads.add(listenerThread);
			listenerThread.start();
			
			Writer writer = new Writer(new PrintStream(socket.getOutputStream(), true));
			writers.add(writer);
			Thread writerThread = new Thread(writer);
			writerThreads.add(writerThread);
			writerThread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void registerClient(String name, String pw, String email, String mode, int id) {
		String hashed = BCrypt.hashpw(pw, BCrypt.gensalt());
		if (!db.search(name)) {
			db.addUser(name, hashed, email, mode);
		} else {
			// Fehlermeldung
			writers.get(id).setMessage("Miep");
		}
		System.out.println("Regist");
	}
	
	public void loginClient(String name, String pw, int id) {
		if (BCrypt.checkpw(pw, db.getUser(name))) { // hashed: hash steht in der
			// DB
			writers.get(id).setMessage("Success");
			System.out.println("It matches");
		} else {
			System.out.println("It does not match");
		}
	}
}
