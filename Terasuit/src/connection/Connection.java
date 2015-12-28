package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import server.Server;

public class Connection implements Runnable {

	private Socket socket;
	private Server server;

	private BufferedReader reader;
	private Analyser analyser;

	private PrintStream writer;
	private ConcurrentLinkedQueue<String> queue;

	private String name;
	private int id;

	public Connection(Socket socket, Server server, int id) {
		this.socket = socket;
		this.server = server;
		try {
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			this.writer = new PrintStream(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		queue = new ConcurrentLinkedQueue<String>();
		this.id = id;
	}

	public void setAnalyser(Analyser analyser) {
		this.analyser = analyser;
	}

	public void addMessage(String message) {
		queue.add(message);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (reader.ready()) {
					System.out.println("Testerino");
					String in = reader.readLine();
					analyser.analyse(in);
				}
				if (!queue.isEmpty()) {
					writer.println(queue.remove());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			reader.close();
			writer.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void leaveLobby() {
		setAnalyser(new MenuAnalyser(server, this, id, true)); //TODO: Boolean must be checked maybe
	}

	public void joinLobby(byte b) {
		server.getLobby(b).addPlayer(this);
	}

	// public void run() {
	// String in;
	// try {
	// System.out.println("nachricht");
	// while ((in = reader.readLine()) != null) {
	// analyser.analyse(in);
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
