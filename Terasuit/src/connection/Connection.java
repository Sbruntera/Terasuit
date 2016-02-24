package connection;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
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

	private BufferedInputStream reader;
	private OutputStream writer;
	private Analyser analyser;
	private ConcurrentLinkedQueue<byte[]> queue;

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
			reader = new BufferedInputStream(socket.getInputStream());
			this.writer = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		queue = new ConcurrentLinkedQueue<byte[]>();
		this.id = id;
		running = true;
		loggOut();
	}

	public void setAnalyser(Analyser analyser) {
		this.analyser = analyser;
	}

	private void addMessage(byte[] message) {
		byte[] msg = new byte[message.length + 3];
		for (int i = 0; i < message.length; i++) {
			msg[i] = message[i];
			msg[msg.length-3] = (byte) 255;
			msg[msg.length-2] = (byte) 255;
			msg[msg.length-1] = (byte) 255;
		}
		queue.add(msg);
	}

	public void loggIn(String string) {
		this.name = string;
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
				int ended = 0;
				if (reader.available() != 0) {
					ArrayList<Byte> bytes = new ArrayList<Byte>();
					while (!(ended == 3)) {
						int i = reader.read();
						System.out.println(i);
						if (i == 255) {
							ended++;
						} else if (ended != 0) {
							ended = 0;
						}
						bytes.add((byte) i);
					}
					analyser.analyse(splitBreak(toPrimal(bytes.toArray(new Byte[bytes.size()]))));
				}
				if (!queue.isEmpty()) {
					writer.write(queue.remove());
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

	private byte[] splitBreak(byte[] primal) {
		byte[] array = new byte[primal.length-3];
		for (int i = 0; i < array.length; i++) {
			array[i] = primal[i];
		}
		return array;
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

	public void sendChatMessage(byte id, String message) {
		byte[] msg = new byte[message.length() + 2];
		msg[0] = 21;
		msg[1] = id;
		for (int i = 0; i < message.length(); i++) {
			msg[i + 2] = (byte) message.charAt(i);
		}
		addMessage(msg);
	}

	private byte[] toPrimal(Byte[] array) {
		byte[] bytes = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			bytes[i] = array[i];
		}
		return bytes;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Menü

	public void sendStats() {
		addMessage(new byte[] {});
	}

	/**
	 * Sendet die gefilterte Liste der aktuellen Lobby an den Client
	 * 
	 * @param lobbys
	 */
	public void sendGameList(Lobby[] lobbys) {
		ArrayList<Byte> array = new ArrayList<Byte>();
		array.add((byte) 1);
		boolean first = true;
		for (Lobby l : lobbys) {
			if (!first) {
				array.add((byte) 1);
			} else {
				first = false;
			}
			array.add(l.getID());
			array.add(l.getMap().getID());
			array.add((byte) (128 + (Boolean.compare(l.hasPassword(), false) << 3) + l
					.getNumberOfPlayers()));
			for (char c : l.getName().toCharArray()) {
				array.add((byte) c);
			}
		}
		addMessage(toPrimal(array.toArray(new Byte[array.size()])));
	}

	/**
	 * Sendet dem Spieler das er der Lobby beitreten darf
	 * 
	 * @param lobby
	 *            : angeforderte Lobby zum beitreten
	 */
	public void sendGameJoin(Lobby lobby, boolean host, byte position) {
		if (lobby != null) {
			ArrayList<Byte> array = new ArrayList<Byte>();
			joinLobby(lobby);
			array.add((byte) 2);
			array.add(lobby.getMap().getID());
			array.add((byte) ((Boolean.compare(host, false) << 2) + position));
			String[] names = lobby.getPlayerNames();
			for (int i = 0; i < names.length; i++) {
				if (i != 0) {
					array.add((byte) 1);
				}
				if (names[i] != null) {
					for (char c : names[i].toCharArray()) {
						array.add((byte) c);
					}
				}
			}
			addMessage(toPrimal(array.toArray(new Byte[array.size()])));
		}
	}

	/**
	 * Bestätigt die Login Anfrage des Client
	 * 
	 * @param splitted
	 */
	public void sendLogin(String name) {
		byte[] array = new byte[name.length() + 1];
		array[0] = 3;
		for (int i = 0; i < name.length(); i++) {
			array[i + 1] = (byte) name.charAt(i);
		}
		addMessage(array);
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Lobby

	/**
	 * Unterrichtet den Client, dass der Spieler die Position gewechselt hat
	 * 
	 * @param player
	 *            Spieler der die Position wechselt
	 * @param position
	 *            Position bei der der Spieler landet
	 */
	public void sendSwitchPlayers(byte player1, byte player2, byte ownPosition) {
		addMessage(new byte[] { 16, player1, player2, ownPosition });
	}

	/**
	 * Unterrichtet den Client, dass andere Spieler die Positionen gewechselt
	 * hat
	 * 
	 * @param player
	 *            Spieler der die Position wechselt
	 * @param position
	 *            Position bei der der Spieler landet
	 */
	public void sendSwitchPlayers(byte player1, byte player2) {
		addMessage(new byte[] { 16, (byte) (player1 + 1), (byte) (player2 + 1) });
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
		byte[] array = new byte[name.length() + 2];
		array[0] = 17;
		array[1] = position;
		for (int i = 0; i < name.length(); i++) {
			array[i + 2] = (byte) name.charAt(i);
		}
		addMessage(array);
	}

	/**
	 * Unterrichtet den Client, dass ein Spieler das Spiel verlassen hat
	 * 
	 * @param playerNumber
	 *            Der neue Spieler
	 */
	public void sendPlayerLeftLobby(byte playerNumber) {
		addMessage(new byte[] { 18, playerNumber });
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
		addMessage(new byte[] { 18 });
	}

	public void sendGetHost() {
		addMessage(new byte[] { 19 });
	}

	/**
	 * Unterrichtet den Client, dass das Spiel gestartet wird
	 */
	public void sendStarting(GameServer server) {
		setAnalyser(new GameAnalyser(server, id, server.getPosition(this)));
		addMessage(new byte[] { 20 });
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Game

	public void sendStartCreateOrUpgradeBuilding(byte playerNumber, byte position,
			byte id) {
		addMessage(new byte[] { 32, playerNumber, (byte) (position +1), id });
	}
	
	public void sendCreateOrUpgradeBuilding(byte playerNumber, byte position,
			byte id) {
		addMessage(new byte[] { 32, playerNumber, (byte) (position +1), (byte) (128 + id) });
	}
	
	public void sendDestroyBuilding(byte playerNumber, byte position) {
		addMessage(new byte[] { 32, playerNumber, (byte) (position + 1) });
	}

	public void sendCancel(byte player, byte position) {
		addMessage(new byte[] { 33, player, (byte) (position + 1) });
	}

	public void sendGenerateUnit(byte buildingPlace, byte typeID) {
		addMessage(new byte[] { 34, typeID, buildingPlace });
	}

	public void sendCreateUnit(short playerNumber, Point position, byte typeID,
			short unitID) {
		addMessage(new byte[] { 35, (byte) playerNumber,
				(byte) (position.x >> 8), (byte) position.x,
				(byte) (position.y >> 8), (byte) position.y, typeID,
				(byte) (unitID >> 8), (byte) unitID });
	}

	public void sendMoveUnit(byte playerNumber, byte direction, short[] unitIDs) {
		byte[] array = new byte[unitIDs.length * 2 + 2];
		array[0] = 36;
		array[1] = direction;
		for (int i = 0; i < unitIDs.length; i++) {
			array[i*2 + 2] = (byte) (unitIDs[i] >> 8);
			array[i*2 + 3] = (byte) (unitIDs[i]);
		}
		addMessage(array);
	}

	public void sendUnitStartsShooting(short[][] units, short[][] targets) {
		ArrayList<Byte> array = new ArrayList<Byte>();
		array.add((byte) 37);
		for (int x = 0; x < units.length; x++) {
			for (int y = 0; y < units[x].length; y++) {
				array.add((byte) (units[x][y] << 8));
				array.add((byte) (units[x][y]));
				array.add((byte) (targets[x][y] << 8));
				array.add((byte) (targets[x][y]));
			}
			array.add((byte) 1);
		}
		addMessage(toPrimal(array.toArray(new Byte[array.size()])));
	}

	public void sendUnitDied(short[][] units) {
		ArrayList<Byte> array = new ArrayList<Byte>();
		array.add((byte) 38);
		for (short[] sA : units) {
			for (short s : sA) {
				array.add((byte) (s << 8));
				array.add((byte) s);
			}
			array.add((byte) 1);
		}
		addMessage(toPrimal(array.toArray(new Byte[array.size()])));
	}

	public void sendPlayerLeftGame(byte playerID) {
		addMessage(new byte[] {39, (byte) (playerID+1)});
	}

	public void sendGameEnded(boolean won) {
		switchToMenu();
		addMessage(new byte[] { 40, (byte) (Boolean.compare(won, false) + 1)});
	}

}
