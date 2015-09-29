package logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Test_server extends Thread {
	private final ServerSocket server;
	private Socket socket;
	private DB db;
	ArrayList<Thread> threads = new ArrayList<Thread>();

	public Test_server(int port) throws IOException {
		server = new ServerSocket(port);
		db = new DB();
	}

	private void connect() {
		try {
			while (true) {
				socket = server.accept();
				client(socket);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void client(Socket socket){
		System.out.println("test");
		Client_Connection c = new Client_Connection(socket, db);
		Thread t = new Thread(c);
		threads.add(t);
		t.start();
	}

	public static void main(String[] args) throws IOException {
		Test_server server = new Test_server(3142);
		server.connect();
	}
}
