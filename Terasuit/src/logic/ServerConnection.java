package logic;

import grafig.Loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerConnection implements Runnable {

	private boolean serverAccess = false;
	private ConcurrentLinkedQueue<String> queue;
	private OutputStream writer;
	private BufferedReader reader;
	private Analyser analyser;
	
	private boolean isLoggedIn;
	private String name;

	public ServerConnection(Loader loader) {
		Socket socket;
		try {
			socket = new Socket("localhost", 3142);
			this.reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			this.writer = socket.getOutputStream();
			serverAccess = true;
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			System.out.println("Server Not Reachable");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		queue = new ConcurrentLinkedQueue<String>();

		analyser = new Analyser(loader);
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (reader.ready()) {
					String in = reader.readLine();
					analyser.analyse(in);
				}
				if (!queue.isEmpty()) {
					writer.write(queue.remove().getBytes());
					writer.write(10);
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

	private void addMessage(String message) {
		queue.add(message);
	}

	// #################################################################
	// Men�

	/**
	 * Loggt den aktuell eingeloggten Spieler aus
	 */
	public void logout() {
		if (analyser.getState() == State.MENU) {
			addMessage(String.valueOf((char) 1));
		}
	}

	/**
	 * Fordert eine aktuelle Liste der Spiel-Server mit den �bergebenen
	 * Filteroptionen an.
	 * 
	 * @param noPassword
	 *            true: nur Spiele ohne Passwort; false: egal
	 * @param name
	 *            substring der im namen enthalten sein soll
	 * @param minPlayers
	 *            Minimale Anzahl an Spielern wird im Server =max gesetzt wenn
	 *            gr��er als max
	 * @param maxPlayers
	 *            Maximale Anzahl an Spielern
	 * @param mapID
	 *            Die ID der gew�nschten Map; 0 wenn keine spezielle Map
	 *            gew�nscht
	 */
	public void refreshServerList(boolean noPassword, String name,
			int minPlayers, int maxPlayers, int mapID) {
		if (analyser.getState() == State.MENU) {
			addMessage(String.valueOf((char) 2)
					+ (char) ((Boolean.compare(noPassword, false) << 4)
							+ (minPlayers << 2) + maxPlayers) + (char) mapID
					+ name);
		}
	}

	/**
	 * Erstellt eine neue Spiellobby und tritt dieser als Host bei
	 * 
	 * @param mapID
	 *            ID der gew�hlten Map
	 * @param name
	 *            Name des Spiels
	 * @param password
	 *            Passwort des Spiels
	 */
	public void createGroup(int mapID, String name, String password) {
		if (analyser.getState() == State.MENU) {
			queue.clear();
			addMessage(String.valueOf((char) 3) + (char) mapID + name + ","
					+ password);
		}
	}

	/**
	 * Tritt dem Spiel mit der �bergebenen ID bei
	 * 
	 * @param id
	 *            ID des gew�nschten Spiels
	 * @param password
	 *            Passwort des gew�nschten Spiels
	 */
	public void connectGroup(int id, String password) {
		System.out.println(password);
		if (analyser.getState() == State.MENU) {
			queue.clear();
			addMessage(String.valueOf((char) 4) + (char) id + password);
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
			addMessage((char) 5 + user + "," + password.toString());
		}
	}

	/**
	 * Registriert einen neuen Account mit den �bergebenen Daten
	 * 
	 * @param user
	 *            Name des Accounts
	 * @param password1
	 *            Passwort des Accounts
	 * @param mail
	 *            E-Mail Addresse des Accounts
	 */
	public void register(String user, String password1, String mail) {
		if (analyser.getState() == State.MENU) {
			addMessage((char) 6 + user + "," + password1 + "," + mail
					+ ",Admin");
		}
	}

	/**
	 * Beendet die Verbindung zum Server
	 * 
	 * nur aufrufen wenn das Spiel geschlossen wird
	 */
	public void close() {
		queue.clear();
		addMessage(String.valueOf((char) 7));
	}

	// #################################################################
	// Lobby

	/**
	 * wechselt die Positionen zweier Spieler (Ben�tigt Hostrechte)
	 * 
	 * @param player1
	 *            gew�nschter Spieler nur Zahlen 0-3
	 * @param player2
	 *            gew�nschter Spieler nur Zahlen 0-3
	 */
	public void switchPlayers(byte player1, byte player2) {
		if (analyser.getState() == State.LOBBY) {
			addMessage(String.valueOf((char) 16) + (char) ((player1 << 2) + player2));
		}
	}

	/**
	 * Verl�sst die aktuelle Lobby und kehrt ins Men� zur�ck
	 */
	public void returnFromLobby() {
		if (analyser.getState() == State.LOBBY) {
			queue.clear();
			addMessage(String.valueOf((char) 17));
		}
	}

	/**
	 * Entfernt einen Spieler aus der Lobby (Ben�tigt Hostrechte)
	 * 
	 * @param playerNumber
	 *            Die Nummer des Spielers
	 */
	public void kickPlayer(int playerNumber) {
		System.out.println(playerNumber);
		if (analyser.getState() == State.LOBBY) {
			addMessage(String.valueOf((char) 18) + (char) playerNumber);
		}
	}

	/**
	 * Startet ein Spiel mit der aktuellen Lobby (Ben�tigt Hostrechte)
	 */
	public void startGame() {
		if (analyser.getState() == State.LOBBY) {
			queue.clear();
			addMessage(String.valueOf((char) 19));
		}
	}

	public void sendLobbyChatMessage(String msg) {
		if (!msg.equals("")) {
			addMessage((char) 20 + msg);
		}
	}

	// #################################################################
	// Game

	/**
	 * Erstellt ein erw�nschtes Geb�ude an der gew�nschten Position
	 *
	 * @param position
	 *            Position des Geb�udes
	 * @param buildingType
	 *            Typ des Geb�udes
	 */
	public void createBuilding(int position, String buildingType) {
		byte buildingID = 127;
		switch (buildingType) {
		case ("Outpost"):
			buildingID = 0;
			break;
		case ("Forge"):
			buildingID = 8;
			break;
		case ("Hospital"):
			buildingID = 16;
			break;
		case ("Bank"):
			buildingID = 24;
			break;
		case ("Armory"):
			buildingID = 32;
			break;
		case ("Generator"):
			buildingID = 40;
			break;
		case ("Special Operations"):
			buildingID = 48;
			break;
		}
		if (position < 4 && analyser.getState() == State.GAME) {
			addMessage(String.valueOf((char) 32) + (char) position
					+ (char) buildingID);
		}
	}

	/**
	 * Zerst�rt ein ausgew�hltes Geb�ude
	 * 
	 * @param position
	 *            : Position des Geb�udes
	 */
	public void destroyBuilding(int position) {
		if (analyser.getState() == State.GAME) {
			if (position < 4) {
				addMessage(String.valueOf((char) 32) + (char) position
						+ (char) 126);
			}
		}
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
			addMessage(String.valueOf((char) 33) + (char) id
					+ (char) buildingPlace);
		}
	}

	/**
	 * Bewegt die Einheiten in eine gew�nschte richtung
	 * 
	 * @param unitID
	 *            IDs der Einheiten
	 * @param right
	 *            L�uft rechts
	 * @param walking
	 *            l�uft �berhaupt
	 */
	public void moveUnit(int[] unitID, boolean right, boolean walking) {
		if (analyser.getState() == State.GAME) {
			addMessage(String.valueOf((char) 34)
					+ (char) (Boolean.compare(false, right) << 1 + Boolean
							.compare(false, walking)) + unitID);
		}
	}

	/**
	 * Verl�sst ein laufendes Spiel und kehrt zum Men� zur�ck
	 */
	public void leaveGame() {
		if (analyser.getState() == State.GAME) {
			queue.clear();
			addMessage(String.valueOf((char) 35));
			analyser.switchState(State.MENU);
		}
	}

	/**
	 * Sendet eine Chatnachricht an den Server
	 * 
	 * @param message
	 *            Chatnachricht
	 */
	public void sendChatMessage(String message) {
		if (!message.equals("")) {
			addMessage((char) 36 + message);
		}
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
	 * @param isLoggedIn the isLoggedIn to set
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}