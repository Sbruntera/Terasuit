package de.szut.server.connection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.szut.server.logic.GameServer;
import de.szut.server.logic.Lobby;
import de.szut.server.logic.Server;

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
	 * Initialisiert eine Verbindung zu einem Client
	 * 
	 * @param socket
	 *            Socket über welches diese Verbindung läuft
	 * @param server
	 *            Der Hauptserver
	 * @param id
	 *            ID der Verbindung
	 */
	public Connection(Socket socket, Server server, short id) {
		this.socket = socket;
		this.server = server;
		try {
			reader = new BufferedInputStream(socket.getInputStream());
			this.writer = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		queue = new ConcurrentLinkedQueue<byte[]>();
		this.id = id;
		running = true;
		loggOut();
	}

	/**
	 * Setzt den Analyser für die Nachrichten
	 * 
	 * @param analyser
	 *            Der Analyser
	 */
	public void setAnalyser(Analyser analyser) {
		this.analyser = analyser;
	}

	/**
	 * Fügt einer Nachricht das Ending 0xFF * 3 und fügt sie der Liste hinzu
	 * 
	 * @param message
	 *            Zu sendende Nachricht
	 */
	private void addMessage(byte[] message) {
		byte[] msg = new byte[message.length + 3];
		for (int i = 0; i < message.length; i++) {
			msg[i] = message[i];
			msg[msg.length - 3] = (byte) 255;
			msg[msg.length - 2] = (byte) 255;
			msg[msg.length - 1] = (byte) 255;
		}
		queue.add(msg);
	}

	/**
	 * Setzt den Namen des Nutzers und setzt in auf eingeloggt
	 * 
	 * @param name
	 *            Name des Spielers
	 */
	public void loggIn(String name) {
		this.name = name;
		loggedIn = true;
	}

	/**
	 * Loggt den Spiler aus und weißt ihm einen neuen Gastnamen zu
	 */
	public void loggOut() {
		this.name = "Guest" + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10) + (int) (Math.random() * 10)
				+ (int) (Math.random() * 10);
		loggedIn = false;
	}

	/**
	 * Gibt zurück ob der Nutzer Eingeloggt ist
	 * 
	 * @return
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Gibt den namen des Nutzers zurück
	 * 
	 * @return Name des Nutzers
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt die ID der Verbindung zurück
	 * 
	 * @return ID der Verbindung
	 */
	public short getID() {
		return id;
	}

	@Override
	public void run() {
		try {
			while (running) {
				int ended = 0;
				if (reader.available() != 0) { // Lesen
					ArrayList<Byte> bytes = new ArrayList<Byte>();
					while (!(ended == 3)) {
						int i = reader.read();
						if (i == 255) {
							ended++;
						} else if (ended != 0) {
							ended = 0;
						}
						bytes.add((byte) i);
					}
					analyser.analyse(splitBreak(toPrimal(bytes
							.toArray(new Byte[bytes.size()]))));
				} // Schreiben
				if (!queue.isEmpty()) {
					writer.write(queue.remove());
				}
				try { // Warten
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Entfernt die Endung einer Nachricht
	 * 
	 * @param message
	 *            Nachricht mit Endung
	 * @return Nachricht ohne Endung
	 */
	private byte[] splitBreak(byte[] message) {
		byte[] array = new byte[message.length - 3];
		for (int i = 0; i < array.length; i++) {
			array[i] = message[i];
		}
		return array;
	}

	/**
	 * Schließt die Verbindung zu diesem Client
	 */
	public void close() {
		try {
			running = false;
			reader.close();
			writer.close();
			socket.close();
		} catch (IOException e) {
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
	 * Wechselt den Analyser zum LobbyAnalysermit übergebener Lobby
	 * 
	 * @param lobby
	 *            Die Lobby der beigetreten wurde
	 */
	private void joinLobby(Lobby lobby) {
		setAnalyser(new LobbyAnalyser(lobby, id, this, server));
		queue.clear();
	}

	/**
	 * Sendet eine Chatnachricht
	 * 
	 * @param id
	 *            ID des Senders
	 * @param message
	 *            Nachricht des senders in UTF-16
	 */
	public void sendChatMessage(byte id, byte[] message) {
		byte[] msg = new byte[message.length + 2];
		msg[0] = 21;
		msg[1] = id;
		for (int i = 0; i < message.length; i++) {
			msg[i + 2] = (byte) message[i];
		}
		addMessage(msg);
	}

	/**
	 * Benachrichtigt den Client das ein Fehler aufgetreten ist
	 * 
	 * @param i
	 */
	public void sendFailed(byte i) {
		addMessage(new byte[] { 4, i });
	}

	/**
	 * Wandelt ein Byte[] in ein byte[] um
	 * 
	 * @param array
	 *            umzuwandelndes Byte[]
	 * @return Umgewandeltes byte[]
	 */
	private byte[] toPrimal(Byte[] array) {
		byte[] bytes = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			bytes[i] = array[i];
		}
		return bytes;
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Menü

	/**
	 * Sendet die Stats an den User
	 */
	public void sendStats() {
		String[][] stats = server.getStats(name);
		ArrayList<Byte> array = new ArrayList<Byte>();
		boolean first = true;
		for (String[] innerArray : stats) {
			array.add((byte) 0);
			if (first) {
				first = false;
			} else {
				array.add((byte) 0);
			}
			array.add((byte) (Integer.valueOf(innerArray[1]) >> 8));
			array.add((byte) (int) (Integer.valueOf(innerArray[1])));
			for (char c : innerArray[0].toCharArray()) {
				array.add((byte) ((c & 0xFF00) >> 8));
				array.add((byte) (c & 0x00FF));
			}
		}
		addMessage(toPrimal(array.toArray(new Byte[array.size()])));
	}

	/**
	 * Sendet die gefilterte Liste der aktuellen Lobby an den Client
	 * 
	 * @param lobbys
	 *            gefilterte liste
	 */
	public void sendGameList(Lobby[] lobbys) {
		ArrayList<Byte> array = new ArrayList<Byte>();
		array.add((byte) 1);
		boolean first = true;
		for (Lobby l : lobbys) {
			if (!first) {
				array.add((byte) 0);
				array.add((byte) 0);
			} else {
				first = false;
			}
			array.add(l.getID());
			array.add(l.getMap().getID());
			array.add((byte) ((Boolean.compare(l.hasPassword(), false) << 3) + l
					.getNumberOfPlayers()));
			for (char c : l.getName().toCharArray()) {
				array.add((byte) ((c & 0xFF00) >> 8));
				array.add((byte) (c & 0x00FF));
			}
		}
		addMessage(toPrimal(array.toArray(new Byte[array.size()])));
	}

	/**
	 * Sendet dem Spieler, dass er der Lobby beitreten darf
	 * 
	 * @param lobby
	 *            angeforderte Lobby zum beitreten
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
					array.add((byte) 0);
					array.add((byte) 0);
				}
				if (names[i] != null) {
					for (char c : names[i].toCharArray()) {
						array.add((byte) ((c & 0xFF00) >> 8));
						array.add((byte) (c & 0x00FF));
					}
				}
			}
			addMessage(toPrimal(array.toArray(new Byte[array.size()])));
		}
	}

	/**
	 * Bestätigt die Login Anfrage des Client
	 * 
	 * @param name
	 *            Name des Clients
	 */
	public void sendLogin(String name) {
		loggIn(name);
		byte[] array = new byte[name.length() * 2 + 1];
		array[0] = 3;
		for (int i = 0; i < name.length(); i++) {
			array[i * 2 + 1] = (byte) ((name.charAt(i) & 0xFF00) >> 8);
			array[i * 2 + 2] = (byte) (name.charAt(i) & 0x00FF);
		}
		addMessage(array);
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Lobby

	/**
	 * Unterrichtet den Client, dass er die Position gewechselt hat
	 * 
	 * @param player
	 *            Position 1 die vertauscht wird
	 * @param position
	 *            Position 2 die vertauscht wird
	 * @param ownPosition
	 *            Eigene neue Position
	 */
	public void sendSwitchPlayers(byte player1, byte player2, byte ownPosition) {
		addMessage(new byte[] { 16, player1, player2, ownPosition });
	}

	/**
	 * Unterrichtet den Client, dass andere Spieler die Positionen gewechselt
	 * hat
	 * 
	 * @param player
	 *            Position 1 die vertauscht wird
	 * @param position
	 *            Position 2 die vertauscht wird
	 */
	public void sendSwitchPlayers(byte player1, byte player2) {
		addMessage(new byte[] { 16, (byte) player1, (byte) player2 });
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
		byte[] array = new byte[name.length() * 2 + 2];
		array[0] = 17;
		array[1] = position;
		for (int i = 0; i < name.length(); i++) {
			array[i * 2 + 2] = (byte) ((name.charAt(i) & 0xFF00) >> 8);
			array[i * 2 + 3] = (byte) (name.charAt(i) & 0x00FF);
		}
		addMessage(array);
	}

	/**
	 * Unterrichtet den Client, dass ein Spieler das Spiel verlassen hat
	 * 
	 * @param playerNumber
	 *            Der verschwundene Spieler
	 */
	public void sendPlayerLeftLobby(byte playerNumber) {
		addMessage(new byte[] { 18, playerNumber });
	}

	/**
	 * Unterrichtet den Client, dass er das Spiel verlassen erfolgreich hat
	 * (leave/kick)
	 */
	public void sendLeftLobby() {
		switchToMenu();
		addMessage(new byte[] { 18 });
	}

	public void sendGetHost() {
		addMessage(new byte[] { 19 });
	}

	/**
	 * Unterrichtet den Client, dass das Spiel gestartet wird und setzt den
	 * Analyser auf Spiel
	 */
	public void sendStarting(GameServer server) {
		setAnalyser(new GameAnalyser(server, id, server.getPosition(this), this, this.server));
		addMessage(new byte[] { 20 });
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Game

	/**
	 * Unterrichtet den Client, dass ein Spielr begonnen hat ein Gebäude zu
	 * bauen
	 * 
	 * @param playerNumber
	 *            Nummer des Täters
	 * @param position
	 *            Position des neuen Gebäudes
	 * @param id
	 *            TypID des Gebäudes
	 */
	public void sendStartCreateOrUpgradeBuilding(byte playerNumber,
			byte position, byte id) {
		addMessage(new byte[] { 32, playerNumber, (byte) (position), id });
	}

	/**
	 * Unterrichtet den Client, dass ein gebäude fertiggestellt wurde
	 * 
	 * @param playerNumber
	 *            Nummer des Besitzers
	 * @param position
	 *            position des Gebäudes
	 * @param id
	 *            TypID des Spielers
	 */
	public void sendCreateOrUpgradeBuilding(byte playerNumber, byte position,
			byte id) {
		addMessage(new byte[] { 32, playerNumber, (byte) (position),
				(byte) (128 + id) });
	}

	/**
	 * Zerstört ein Gebäude
	 * 
	 * @param playerNumber
	 *            Besitzer des Gebäudes
	 * @param position
	 *            position des Gebäudes
	 */
	public void sendDestroyBuilding(byte playerNumber, byte position) {
		addMessage(new byte[] { 32, playerNumber, (byte) (position) });
	}

	/**
	 * Bricht eine Gebäudeproduktion ab
	 * 
	 * @param player
	 *            Spieler des Gebäudes
	 * @param position
	 *            Position des Gebäudes
	 */
	public void sendCancel(byte player, byte position) {
		addMessage(new byte[] { 33, player, (byte) (position) });
	}

	/**
	 * Startet die Produktion einer Einheit
	 * 
	 * @param buildingPlace
	 *            Position des Produktionsgebäudes
	 * @param typeID
	 *            Typ der Einheit
	 */
	public void sendGenerateUnit(byte buildingPlace, byte typeID) {
		addMessage(new byte[] { 34, typeID, buildingPlace });
	}

	/**
	 * Beendet die Produktion einer Einheit und plaziert sie auf dem Feld
	 * 
	 * @param playerNumber
	 *            Nummer des Splielers
	 * @param x
	 *            X-Koordinate der Einheit
	 * @param y
	 *            Y-Koordinate der Einheit
	 * @param typeID
	 *            Typ der Einheit
	 * @param unitID
	 *            ID der zu plazierenden Einheit
	 */
	public void sendCreateUnit(short playerNumber, double x, double y,
			byte typeID, short unitID) {
		addMessage(new byte[] { 35, (byte) playerNumber,
				(byte) (((int) x) >> 8), (byte) ((int) x),
				(byte) (((int) y) >> 8), (byte) ((int) y), typeID,
				(byte) (unitID >> 8), (byte) unitID, (byte) 0 });
	}

	/**
	 * Unterrichtet den Client, dass ein Spieler die Bewegung einiger Einheiten
	 * verändert hat
	 * 
	 * @param playerNumber
	 *            Nummer des Spielers
	 * @param direction
	 *            Richtung der Einheit
	 * @param unitIDs
	 *            IDs der Einheiten
	 */
	public void sendMoveUnit(byte playerNumber, byte direction, short[] unitIDs) {
		byte[] array = new byte[unitIDs.length * 2 + 3];
		array[0] = 36;
		array[1] = playerNumber;
		array[2] = direction;
		for (int i = 0; i < unitIDs.length; i++) {
			array[i * 2 + 3] = (byte) (unitIDs[i] >> 8);
			array[i * 2 + 4] = (byte) (unitIDs[i]);
		}
		addMessage(array);
	}

	/**
	 * Unterrichtet den Client das einige Einheiten begonnen haben zu schießen
	 * 
	 * @param units
	 *            IDs der Einheiten
	 * @param targets
	 *            Ziele der Einheiten
	 */
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

	/**
	 * Unterrichtet den Client, dass einige Einheiten gestorben sind
	 * 
	 * @param units
	 *            Verstorbene Einheiten
	 */
	public void sendUnitDied(Short[] units) {
		byte[] message = new byte[units.length * 2 + 1];
		message[0] = 38;
		for (int i = 0; i < units.length; i++) {
			message[i * 2 + 1] = (byte) (units[i] >> 8);
			message[i * 2 + 2] = (byte) units[i].shortValue();
		}
		addMessage(message);
	}

	/**
	 * Unterrichtet den Client, dass ein Spieler das Spiel verlassen hat
	 * 
	 * @param playerID
	 */
	public void sendPlayerLeftGame(byte playerID) {
		addMessage(new byte[] { 39, (byte) (playerID) });
	}

	/**
	 * Unterrichtet den Client, dass das Spiel vorbei ist.
	 * 
	 * @param won
	 *            ob man gewonnen hat
	 */
	public void sendGameEnded(boolean won) {
		switchToMenu();
		addMessage(new byte[] { 40, (byte) (Boolean.compare(won, false)) });
	}

}
