package logic;

import grafig.Loader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerConnection implements Runnable {

	private boolean serverAccess = false;
	private ConcurrentLinkedQueue<byte[]> queue;
	private OutputStream writer;
	private BufferedInputStream reader;
	private Analyser analyser;

	private boolean isLoggedIn;
	private String name;

	public ServerConnection(Loader loader) {
		Socket socket;
		try {
			socket = new Socket("localhost", 3142);
			this.reader = new BufferedInputStream(socket.getInputStream());
			this.writer = socket.getOutputStream();
			serverAccess = true;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			System.out.println("Server Not Reachable");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		queue = new ConcurrentLinkedQueue<byte[]>();

		analyser = new Analyser(loader);
	}

	@Override
	public void run() {
		try {
			while (true) {
				int ended = 0;
				if (reader.available() != 0) {
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
			serverAccess = false;
		}
	}

	private byte[] splitBreak(byte[] primal) {
		byte[] array = new byte[primal.length - 3];
		for (int i = 0; i < array.length; i++) {
			array[i] = primal[i];
		}
		return array;
	}

	private void addMessage(byte[] message) {
		byte[] msg = new byte[message.length + 3];
		for (int i = 0; i < message.length; i++) {
			msg[i] = message[i];
		}
		msg[msg.length - 3] = (byte) 255;
		msg[msg.length - 2] = (byte) 255;
		msg[msg.length - 1] = (byte) 255;
		queue.add(msg);
	}

	public boolean isServerAccess() {
		return serverAccess;
	}

	/**
	 * @return the isLoggedIn
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * @param isLoggedIn
	 *            the isLoggedIn to set
	 */
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	private byte[] toPrimal(Byte[] array) {
		byte[] bytes = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			bytes[i] = array[i];
		}
		return bytes;
	}

	// #################################################################
	// Menü

	/**
	 * Loggt den aktuell eingeloggten Spieler aus
	 */
	public void logout() {
		if (analyser.getState() == State.MENU) {
			addMessage(new byte[] { 1 });
			setLoggedIn(false);
		}
	}

	/**
	 * Fordert eine aktuelle Liste der Spiel-Server mit den übergebenen
	 * Filteroptionen an.
	 * 
	 * @param noPassword
	 *            true: nur Spiele ohne Passwort; false: egal
	 * @param name
	 *            substring der im namen enthalten sein soll
	 * @param minPlayers
	 *            Minimale Anzahl an Spielern wird im Server =max gesetzt wenn
	 *            größer als max
	 * @param maxPlayers
	 *            Maximale Anzahl an Spielern
	 * @param mapID
	 *            Die ID der gewünschten Map; 255 wenn keine spezielle Map
	 *            gewünscht
	 */
	public void refreshServerList(boolean noPassword, String name,
			int minPlayers, int maxPlayers, int mapID) {
		if (analyser.getState() == State.MENU) {
			byte[] array = new byte[3 + name.length()];
			array[0] = 2;
			array[1] = (byte) ((Boolean.compare(noPassword, false) << 6)
					+ (minPlayers << 3) + maxPlayers);
			array[2] = (byte) mapID;
			for (int i = 0; i < name.length(); i++) {
				array[i + 3] = (byte) name.charAt(i);
			}
			addMessage(array);
		}
	}

	/**
	 * Erstellt eine neue Spiellobby und tritt dieser als Host bei
	 * 
	 * @param mapID
	 *            ID der gewählten Map
	 * @param name
	 *            Name des Spiels
	 * @param password
	 *            Passwort des Spiels
	 */
	public void createGroup(int mapID, String name, String password) {
		if (analyser.getState() == State.MENU) {
			queue.clear();
			ArrayList<Byte> array = new ArrayList<Byte>();
			array.add((byte) 3);
			array.add((byte) mapID);
			for (char c : name.toCharArray()) {
				array.add((byte) c);
			}
			array.add((byte) 1);
			for (char c : password.toCharArray()) {
				array.add((byte) c);
			}
			addMessage(toPrimal(array.toArray(new Byte[array.size()])));
		}
	}

	/**
	 * Tritt dem Spiel mit der übergebenen ID bei
	 * 
	 * @param id
	 *            ID des gewünschten Spiels
	 * @param password
	 *            Passwort des gewünschten Spiels
	 */
	public void connectGroup(int id, String password) {
		if (analyser.getState() == State.MENU) {
			queue.clear();
			byte[] array = new byte[password.length() + 2];
			array[0] = 4;
			array[1] = (byte) id;
			for (int i = 0; i < password.length(); i++) {
				array[i + 2] = (byte) password.charAt(i);
			}
			addMessage(array);
		}
	}

	/**
	 * Loggt einen Account in das Spiel ein
	 * 
	 * @param user
	 *            Name des Accounts
	 * @param password
	 *            Passwort des Accounts
	 */
	public void login(String user, String password) {
		if (analyser.getState() == State.MENU) {
			ArrayList<Byte> array = new ArrayList<Byte>();
			array.add((byte) 5);
			for (char c : user.toCharArray()) {
				array.add((byte) c);
			}
			array.add((byte) 1);
			for (char c : password.toCharArray()) {
				array.add((byte) c);
			}
			addMessage(toPrimal(array.toArray(new Byte[array.size()])));
		}
	}

	/**
	 * Registriert einen neuen Account mit den übergebenen Daten
	 * 
	 * @param user
	 *            Name des Accounts
	 * @param password1
	 *            Passwort des Accounts
	 * @param mail
	 *            E-Mail Addresse des Accounts
	 */
	public void register(String user, String password, String mail) {
		if (analyser.getState() == State.MENU) {
			ArrayList<Byte> array = new ArrayList<Byte>();
			array.add((byte) 6);
			for (char c : user.toCharArray()) {
				array.add((byte) c);
			}
			array.add((byte) 1);
			for (char c : password.toCharArray()) {
				array.add((byte) c);
			}
			array.add((byte) 1);
			for (char c : mail.toCharArray()) {
				array.add((byte) c);
			}
			addMessage(toPrimal(array.toArray(new Byte[array.size()])));
		}
	}

	/**
	 * Beendet die Verbindung zum Server
	 * 
	 * nur aufrufen wenn das Spiel geschlossen wird
	 */
	public void close() {
		queue.clear();
		addMessage(new byte[] { 7 });
	}

	// #################################################################
	// Lobby

	/**
	 * wechselt die Positionen zweier Spieler (Benötigt Hostrechte)
	 * 
	 * @param player1
	 *            gewünschter Spieler nur Zahlen 0-3
	 * @param player2
	 *            gewünschter Spieler nur Zahlen 0-3
	 */
	public void switchPlayers(byte player1, byte player2) {
		if (analyser.getState() == State.LOBBY) {
			addMessage(new byte[] { 16, (byte) ((player1 << 2) + player2) });
		}
	}

	/**
	 * Verlässt die aktuelle Lobby und kehrt ins Menü zurück
	 */
	public void returnFromLobby() {
		if (analyser.getState() == State.LOBBY) {
			queue.clear();
			addMessage(new byte[] { 17 });
		}
	}

	/**
	 * Entfernt einen Spieler aus der Lobby (Benötigt Hostrechte)
	 * 
	 * @param playerNumber
	 *            Die Nummer des Spielers
	 */
	public void kickPlayer(int playerNumber) {
		if (analyser.getState() == State.LOBBY) {
			addMessage(new byte[] { 18, (byte) playerNumber });
		}
	}

	/**
	 * Startet ein Spiel mit der aktuellen Lobby (Benötigt Hostrechte)
	 */
	public void startGame() {
		if (analyser.getState() == State.LOBBY) {
			queue.clear();
			addMessage(new byte[] { 19 });
		}
	}

	public void sendLobbyChatMessage(String msg) {
		if (!msg.equals("") && analyser.getState() == State.LOBBY) {
			queue.clear();
			byte[] array = new byte[msg.length() + 1];
			array[0] = 20;
			for (int i = 0; i < msg.length(); i++) {
				array[i + 1] = (byte) msg.charAt(i);
			}
			addMessage(array);
		}
	}

	// #################################################################
	// Game

	/**
	 * Erstellt ein erwünschtes Gebäude an der gewünschten Position
	 *
	 * @param position
	 *            Position des Gebäudes
	 * @param buildingType
	 *            Typ des Gebäudes
	 */
	public void createBuilding(int position, String buildingType) {
		byte buildingID = 127;
		switch (buildingType) {
		case ("Outpost"):
			buildingID = 1;
			break;
		case ("Barracks"):
			buildingID = 2;
			break;
		case ("Arsenal"):
			buildingID = 3;
			break;
		case ("Forge"):
			buildingID = 4;
			break;
		case ("Manufactory"):
			buildingID = 5;
			break;
		case ("Mechanics Terminal"):
			buildingID = 6;
			break;
		case ("Hospital"):
			buildingID = 7;
			break;
		case ("War Sanctum"):
			buildingID = 8;
			break;
		case ("Bank"):
			buildingID = 9;
			break;
		case ("Treasury"):
			buildingID = 10;
			break;
		case ("Armory"):
			buildingID = 11;
			break;
		case ("Generator"):
			buildingID = 12;
			break;
		case ("Solar Grid"):
			buildingID = 13;
			break;
		case ("Special Operations"):
			buildingID = 14;
			break;
		}
		if (position < 4 && analyser.getState() == State.GAME) {
			addMessage(new byte[] { 32, (byte) position, buildingID });
		}
	}

	/**
	 * Zerstört ein ausgewähltes Gebäude
	 * 
	 * @param position
	 *            : Position des Gebäudes
	 */
	public void destroyBuilding(int position) {
		if (analyser.getState() == State.GAME) {
			addMessage(new byte[] { 32, (byte) (position + 1), 127 });
		}
	}

	/**
	 * Bricht den Bau ab
	 */
	public void cancelBuilding(int id) {
		System.out.println("Cancel");
		addMessage(new byte[] { 33, (byte) ((id - 1) % 4 + 1) });
	}

	/**
	 * Erstellt eine Einheit
	 * 
	 * @param id
	 *            ID der Einheit
	 * @param i
	 */
	public void createUnit(int id, int buildingPlace) {
		if (analyser.getState() == State.GAME) {
			addMessage(new byte[] { 34, (byte) id, (byte) buildingPlace });
		}
	}

	/**
	 * Bewegt die Einheiten in eine gewünschte richtung
	 * 
	 * @param (integers IDs der Einheiten
	 * @param right
	 *            Läuft rechts
	 * @param walking
	 *            läuft Überhaupt
	 * @param running
	 */
	public void moveUnit(Integer[] integers, boolean forward, boolean walking,
			boolean running) {
		if (analyser.getState() == State.GAME) {
			byte[] array = new byte[integers.length * 2 + 2];
			array[0] = 35;
			array[1] = (byte) ((((Boolean.compare(
					((analyser.position & 2) == 2 ^ forward), false) * 2 - 1)
					* Boolean.compare(!running, false)
					* Boolean.compare(walking, false) + 1) + Boolean.compare(
					running, false) * 2));
			for (int i = 0; i < integers.length; i++) {
				array[i * 2 + 2] = (byte) (integers[i] >> 8);
				array[i * 2 + 3] = (byte) (int) integers[i];
			}
			addMessage(array);
		}
	}

	/**
	 * Verlässt ein laufendes Spiel und kehrt zum Menü zurück
	 */
	public void leaveGame() {
		if (analyser.getState() == State.GAME) {
			analyser.switchState(State.MENU);
			queue.clear();
			addMessage(new byte[] { 36 });
		}
	}

	/**
	 * Sendet eine Chatnachricht an den Server
	 * 
	 * @param message
	 *            Chatnachricht
	 */
	public void sendChatMessage(String message) {
		if (!message.equals("") && analyser.getState() == State.GAME) {
			byte[] array = new byte[message.length() + 1];
			array[0] = 20;
			for (int i = 0; i < message.length(); i++) {
				array[i + 1] = (byte) message.charAt(i);
			}
			addMessage(array);
		}
	}
	
	public void stats(){
		if (analyser.getState() == State.MENU) {
			addMessage(new byte[] { 0 });
		}
	}
}