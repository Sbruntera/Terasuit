package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import server.GameServer;
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
	private boolean running;
	private boolean loggedIn;

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
		running = true;
		loggOut();
	}

	public void setAnalyser(Analyser analyser) {
		this.analyser = analyser;
	}

	private void addMessage(String message) {
		queue.add(message);
	}

	public void loggIn(String name) {
		this.name = name;
		loggedIn = true;
	}

	public void loggOut() {
		this.name = "Guest" + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10) + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10);
		loggedIn = false;
	}

	public boolean isLoggedIn() {
		return loggedIn;
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
			while (running) {
				if (reader.ready()) {
					String in = reader.readLine();
					if (in.length() != 0) {
						analyser.analyse(in);
					}
				}
				if (!queue.isEmpty()) {
					writer.println(queue.remove());
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			running = false;
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
	// Menü

	public void sendStats() {
		addMessage("");
	}

	/**
	 * Sendet die gefilterte Liste der aktuellen Lobby an den Client
	 * 
	 * @param lobbys
	 */
	public void sendGameList(Lobby[] lobbys) {
		String message = String.valueOf((char) 1);
		boolean first = true;
		for (Lobby l : lobbys) {
			if (!first) {
				message += ",";

			} else {
				first = false;
			}
			message += String.valueOf((char) l.getID())
					+ (char) l.getMap().getID()
					+ (char) ((Boolean.compare(l.hasPassword(), false) << 3) + l
							.getNumberOfPlayers()) + l.getName();
		}
		addMessage(message);
	}

	/**
	 * Sendet dem Spieler das er der Lobby beitreten darf
	 * 
	 * @param lobby
	 *            : angeforderte Lobby zum beitreten
	 */
	public void sendGameJoin(Lobby lobby, boolean host) {
		if (lobby != null) {
			joinLobby(lobby);
			String message = String.valueOf((char) 2)
					+ (char) lobby.getMap().getID()
					+ (char) Boolean.compare(host, false);
			String[] names = lobby.getPlayerNames();
			for (int i = 0; i < names.length; i++) {
				if (i != 0) {
					message += ",";
				}
				if (names[i] != null) {
					message += names[i];
				} else {
					message += (char) 0;
				}
			}
			addMessage(message);
		}
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
	public void sendSwitchPlayers(byte player1, byte player2) {
		addMessage(String.valueOf((char) 16) + (char) player1
				+ (char) player2);
	}

	/**
	 * Unterrichtet den Client, dass ein Spieler dem Spiel beitritt
	 * 
	 * @param position
	 *            position des neuen Spielers
	 * @param name
	 *            Name des neuen Spielers
	 */
	public void sendPlayerJoined(byte position, String name) {
		addMessage(String.valueOf((char) 17) + (char) position + name);
	}

	/**
	 * Unterrichtet den Client, dass ein Spieler das Spiel verlassen hat
	 * 
	 * @param player
	 *            Der neue Spieler
	 */
	public void sendPlayerLeftLobby(byte player) {
		addMessage(String.valueOf((char) 18) + (char) player);
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
		addMessage(String.valueOf((char) 18));
	}

	/**
	 * Unterrichtet den Client, dass das Spiel gestartet wird
	 */
	public void sendStarting(GameServer server) {
		setAnalyser(new GameAnalyser(server, id, server.getPosition(this)));
		addMessage(String.valueOf((char) 19));
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Game

	public void sendCreateOrUpgradeBuilding(byte playerNumber, byte position,
			byte id) {
		addMessage(String.valueOf((char) 32) + (char) playerNumber
				+ (char) position + (char) id);
	}

	public void sendGenerateUnit(byte buildingPlace, byte typeID) {
		addMessage(String.valueOf((char) 33) + (char) typeID
				+ (char) buildingPlace);
	}

	public void sendCreateUnit(short playerNumber, short position, byte typeID,
			short unitID) {
		addMessage(String.valueOf((char) 34) + (char) (playerNumber >> 8)
				+ (char) playerNumber + (char) (position >> 8)
				+ (char) (position) + (char) typeID + (char) (unitID >> 8)
				+ (char) unitID);
	}

	public void sendMoveUnit(short playerNumber, byte direction, short[] unitIDs) {
		String msg = String.valueOf((char) 35) + (char) (playerNumber >> 8)
				+ (char) playerNumber + (char) direction;
		for (short s : unitIDs) {
			msg += (char) (s << 8) + (char) direction;
		}
		addMessage(msg);
	}

	public void sendUnitStartsShooting(short[][] units, short[][] targets) {
		String msg = String.valueOf((char) 36);
		for (int x = 0; x < units.length; x++) {
			for (int y = 0; y < units[x].length; y++) {
				msg += (char) (units[x][y] << 8) + (char) (units[x][y])
						+ (char) (targets[x][y] << 8) + (char) (targets[x][y]);
			}
			msg += (char) 255;
		}
		addMessage(msg);
	}

	public void sendUnitDied(short[][] units) {
		String msg = String.valueOf((char) 37);
		for (short[] sA : units) {
			for (short s : sA) {
				msg += (char) (s << 8) + (char) s;
			}
			msg += (char) 255;
		}
		addMessage(msg);
	}

	public void sendPlayerLeftGame(short playerID) {
		addMessage(String.valueOf((char) 38) + (char) (playerID >> 8)
				+ (char) playerID);
	}

	public void sendGameEnded(boolean won) {
		switchToMenu();
		addMessage(String
				.valueOf((char) (39 + (Boolean.compare(won, false) << 4))));
	}

	public void sendChatMessage(byte id, String message) {
		addMessage(String.valueOf((char) 20) + (char) id
				+ message);
	}
}
