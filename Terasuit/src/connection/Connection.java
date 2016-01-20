package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import server.Lobby;
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

	private void addMessage(String message) {
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

	private void leaveLobby() {
		setAnalyser(new MenuAnalyser(server, this, id, true)); // TODO: Boolean
																// must be
																// checked maybe
		queue.clear();
	}

	private void joinLobby() {
		setAnalyser(new LobbyAnalyser());
		queue.clear();
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Menü

	public void sendStats() {
		addMessage("");
	}

	public void sendGameList(Lobby[] lobbys) {
		addMessage("");
	}

	public void sendGameJoin(Lobby lobby) {
		joinLobby();
		addMessage("");
	}

	/**
	 * Bestätigt die Login Anfrage des Client
	 */
	public void sendLogin() {
		addMessage(String.valueOf((char) 192));
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Lobby

	/**
	 * Unterrichtet den Client, dass der Spieler die position gewechselt hat
	 * 
	 * @param player
	 *            Spieler der die Position wechselt
	 * @param position
	 *            Position bei der der Spieler landet
	 */
	public void sendSwitchPosition(byte player, byte position) {
		addMessage(String.valueOf((char) (player << 3 + position << 1)));
	}

	/**
	 * Unterrichtet den Client, dass ein Spieler dem Spiel beitritt
	 * 
	 * @param position
	 *            position des neuen Spielers
	 * @param name
	 *            Name des neuen Spielers
	 */
	public void sendPlayerJoined(byte position, byte id, String name) {
		addMessage((char) (64 + position << 3 + id << 1) + name);
	}

	/**
	 * Unterrichtet den Client, dass ein Spieler das Spiel verlassen hat
	 * 
	 * @param player
	 *            Der neue Spieler
	 */
	public void sendPlayerLeftLobby(byte player) {
		addMessage(String.valueOf((char) (128 + player << 3)));
	}

	/**
	 * Unterrichtet den Client, dass das Spiel gestartet wird
	 */
	public void sendStarting() {
		addMessage(String.valueOf((char) 192));
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Game

	public void sendCreateBuilding() {
		addMessage("");
	}

	public void sendUpgradeBuilding() {
		addMessage("");
	}

	public void sendCreateUnit() {
		addMessage("");
	}

	public void sendMoveUnit() {
		addMessage("");
	}

	public void sendPlayerLeftGame(byte playerID) {
		addMessage("");
	}

	public void sendGameEnded(boolean won) {
		addMessage(String.valueOf((char) (160 + Boolean.compare(won, false) << 4)));
	}

	public void sendChatMessage(byte player, String message) {
		addMessage(String.valueOf((char) 192) + message);
	}
}
