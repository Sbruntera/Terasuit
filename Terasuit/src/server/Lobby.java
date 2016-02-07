package server;

import connection.Connection;

/**
 * 
 * @author Simeon, Jan-Philipp
 *
 */
public class Lobby {

	private static final int MAXPLAYERS = 4;
	private Connection[] playerList = new Connection[MAXPLAYERS];
	private Server server;
	private String gameName;
	private String password;
	private Map map;
	private Connection host;
	private byte id;

	/**
	 * Erstellt ein neues Spiel
	 * 
	 * @param lobby
	 * @param host
	 * @param gameName
	 * @param password
	 * @param map
	 */
	public Lobby(Server server, Connection host, String gameName,
			String password, Map map) {
		playerList[0] = host;
		this.host = host;
		this.server = server;
		this.gameName = gameName;
		this.password = password;
		this.map = map;
	}

	/**
	 * F�gt einen Spieler zu der Lobby hinzu
	 * 
	 * @param player
	 * @param password 
	 */
	public void addPlayer(Connection player, String password) {
		boolean playerJoined = false;
		int i = 0;
		while (!playerJoined && i < MAXPLAYERS) {
			if (playerList[i] == null) {
				playerList[i] = player;
				playerJoined = true;
			}
		}
	}

	/**
	 * Entfernt einen Spieler aus der Lobby
	 * 
	 * @param s
	 * 
	 * @param position
	 */
	public void removePlayer(short senderID, short playerID) {
		if (senderID == playerID || senderID == host.getID()) {
			boolean found = false;
			int i = 0;
			while (!found && i < MAXPLAYERS) {
				if (playerList[0] != null) {
					if (playerList[0].getID() == id) {
						found = true;
					} else {
						i++;
					}
				} else {
					i++;
				}
			}
			if (found) {
				for (Connection c : playerList) {
					if (c == playerList[i]) {
						c.sendLeftLobby();
					} else {
						c.sendPlayerLeftGame(playerList[i].getID());
					}
				}
			}
			if (playerList[i] == host) { // Der Spieler ist Host
				boolean playerFound = false;
				int actualPlayer = 0;
				while (!playerFound && actualPlayer < MAXPLAYERS) {
					if (playerList[actualPlayer] != null
							&& playerList[actualPlayer] != host) {
						playerFound = true;
						host = playerList[actualPlayer];
					} else {
						actualPlayer++;
					}
				}
			}
			playerList[i] = null;
			if (getNumberOfPlayers() == 0) {
				closeGame();
			}
		}
	}

	/**
	 * Sendet eine Nachricht an alle Nutzer im Raum
	 * 
	 * @param msg
	 *            : Nachricht
	 * @param id
	 *            : Nummer des Senders
	 */
	public void broadcast(String msg, short id) {
		for (int i = 0; i < playerList.length; i++) {
			playerList[i].sendGameChatMessage(id, msg);
		}
	}

	/**
	 * Verschiebt einen Spieler innnerhalb der Lobby
	 * 
	 * @param oldPosition
	 * @param newPosition
	 */
	public void movePlayer(short playerID, int newPosition) {
		for (int i = 0; i < playerList.length; i++) {
			if (playerList[i] != null) {
				if (playerList[i].getID() == playerID
						&& playerList[newPosition] != null) {
					playerList[newPosition] = playerList[i];
				}
			}
		}
	}

	/**
	 * Schlie�t die Lobby
	 */
	public void closeGame() {
		for (Connection p : playerList) {
			p.sendLeftLobby();
		}
		server.removeLobby(this);
	}

	/**
	 * Gibt den Namen der Lobby zur�ck
	 * 
	 * @return Name
	 */
	public String getName() {
		return gameName;
	}

	/**
	 * Gibt zur�ck ob das eingegebene Passwort dem Passwort der Lobby entspricht
	 * 
	 * @return true: Passw�rter gleich
	 */
	public boolean checkPassword(String password) {
		if (hasPassword()) {
			if (this.password.equals(password)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gibt zur�ck ob ein Passwort vorhanden ist
	 * 
	 * @return true: Passwort vorhanden
	 */
	public boolean hasPassword() {
		if (password == null) {
			return false;
		}
		return true;
	}

	/**
	 * Gibt die Namen der Spieler zur�ck
	 * 
	 * @return Namen der Spieler Das erste Object ist die IDliste als int[], die
	 *         zweite ist die Namensliste als String[]
	 */
	public Object[] getPlayerNamesAndIDs() {
		String[] names = new String[getNumberOfPlayers()];
		int[] iDs = new int[names.length];
		int counter = 0;
		for (Connection p : playerList) {
			names[counter] = p.getName();
			iDs[counter] = p.getID();
			counter++;
		}
		return new Object[] { iDs, names };
	}

	/**
	 * Gibt die anzahl der beigetretenen Spieler zur�ck
	 * 
	 * @return Anzahl der Spieler
	 */
	public int getNumberOfPlayers() {
		int players = 0;
		for (int i = 0; i < MAXPLAYERS; i++) {
			if (playerList[i] != null) {
				players++;
			}
		}
		return players;
	}

	/**
	 * Gibt die ausgew�hlte Karte zur�ck
	 * 
	 * @return Karte der Lobby
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Gibt den Namen des Hosts zur�ck
	 * 
	 * @return Hostname
	 */
	public String getHostname() {
		return playerList[0].getName();
	}

	public byte getID() {
		return id;
	}

	public void startGame(short id) {
		if (host.getID() == id) {
			server.createGame(this);
		}
	}

	public Connection[] getConnections() {
		return playerList;
	}
}