package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import server.Lobby;
import server.Server;

/**
 * 
 * @author Simeon, Jan-Philipp
 *
 */
public class Connection implements Runnable {

	private Socket socket;
	private Server server;

	private BufferedReader reader;
	private PrintStream writer;
	private Analyser analyser;
	private ConcurrentLinkedQueue<String> queue;

	private String name;
	private short id;

	/**
	 * 
	 * @param socket
	 * @param server
	 * @param id
	 */
	public Connection(Socket socket, Server server, short id) {
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

	public short getID() {
		return id;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (reader.ready()) {
					String in = reader.readLine();
					analyser.analyse(in);
					System.out.println("he");
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

	/**
	 * Wechselt den Analyser zum MenuAnalyser
	 */
	private void switchToMenu() {
		setAnalyser(new MenuAnalyser(server, this, id));
		queue.clear();
	}

	/**
	 * Wechselt den Analyser zum LobbyAnalyser
	 * 
	 * @param lobby
	 */
	private void joinLobby(Lobby lobby) {
		setAnalyser(new LobbyAnalyser(lobby, id));
		queue.clear();
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Men�

	public void sendStats() {
		addMessage("");
	}

	/**
	 * Sendet die gefilterte Liste der aktuellen Lobby an den Client
	 * 
	 * @param lobbys
	 */
	public void sendGameList(Lobby[] lobbys) {
		String message = String.valueOf((char) 64);
		boolean first = true;
		for (Lobby l : lobbys) {
			if (!first) {
				message += ",";

			} else {
				first = false;
			}
			message += (char) l.getID() + (char) l.getMap().getID()
					+ (char) (Boolean.compare(l.hasPassword(), false) << 2)
					+ l.getNumberOfPlayers() + l.getName();
		}
		addMessage(message);
	}

	/**
	 * Sendet dem Spieler das er der Lobby beitreten darf
	 * 
	 * @param lobby
	 *            : angeforderte Lobby zum beitreten
	 */
	public void sendGameJoin(Lobby lobby) {
		joinLobby(lobby);
		String message = String.valueOf((char) 128)
				+ (char) lobby.getMap().getID();
		Object[] arrays = lobby.getPlayerNamesAndIDs();
		int[] iDs = (int[]) arrays[0];
		String[] names = (String[]) arrays[1];
		for (int i = 0; i < iDs.length; i++) {
			if (i == 0) {
				message += ",";
			}
			message += iDs[i] + names[i];
		}
		addMessage(message);
	}

	/**
	 * Best�tigt die Login Anfrage des Client
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
		addMessage(String.valueOf((char) position) + (char) player);
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
		addMessage((char) (64 + position) + (id + name));
	}

	/**
	 * Unterrichtet den Client, dass ein Spieler das Spiel verlassen hat
	 * 
	 * @param player
	 *            Der neue Spieler
	 */
	public void sendPlayerLeftLobby(byte player) {
		addMessage(String.valueOf((char) 128) + (char) player);
	}

	/**
	 * Unterrichtet den Client, dass er das Spiel verlassen erfolgreich hat
	 * (leave/kick)
	 * 
	 * @param player
	 *            Der neue Spieler
	 */
	public void sendLeftLobby() {
		switchToMenu();
		addMessage(String.valueOf((char) 128));
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

	public void sendPlayerLeftGame(short i) {
		addMessage("");
	}

	public void sendGameEnded(boolean won) {
		switchToMenu();
		addMessage(String
				.valueOf((char) (160 + (Boolean.compare(won, false) << 4))));
	}

	public void sendGameChatMessage(short id, String message) {
		addMessage(String.valueOf((char) 192) + id + message);
	}
}
