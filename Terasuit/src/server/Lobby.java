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
	 * @param id
	 */
	public Lobby(Server server, Connection host, String gameName,
			String password, Map map, byte id) {
		playerList[0] = host;
		this.host = host;
		this.server = server;
		this.gameName = gameName;
		this.password = password;
		this.map = map;
		this.id = id;
		host.sendGameJoin(this, true, (byte) 0);
	}

	/**
	 * Fügt einen Spieler zu der Lobby hinzu
	 * 
	 * @param player
	 * @param password
	 */
	public boolean addPlayer(Connection player, String password) {
		boolean playerJoined = false;
		byte i = 0;
		if (password.equals(this.password) || !this.hasPassword()) {
			while ((!playerJoined) && i < MAXPLAYERS) {
				if (playerList[i] == null) {
					playerList[i] = player;
					playerJoined = true;
				} else {
					i++;
				}
			}
		}
		if (playerJoined) {
			for (Connection c : playerList) {
				if (c != null) {
					if (c == player) {
						c.sendGameJoin(this, false, i);
					} else {
						c.sendPlayerJoined(i, player.getName());
					}
				}
			}
		}
		return playerJoined;
	}

	/**
	 * Entfernt einen Spieler aus der Lobby
	 * 
	 * @param s
	 * 
	 * @param position
	 */
	public void removePlayer(short senderID, byte playerNumber) {
		if ((getPosition(senderID) == playerNumber || senderID == host.getID())
				&& playerList[playerNumber] != null) {
			for (byte i = 0; i < playerList.length; i++) {
				if (playerList[i] != null) {
					if (i != playerNumber) {
						playerList[i].sendPlayerLeftLobby(playerNumber);
					} else {
						playerList[i].sendLeftLobby();
					}
				}
			}
			if (playerList[playerNumber] == host) { // Der Spieler ist Host
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
				if (!playerFound) {
					closeGame(true);
				}
			}
			playerList[playerNumber] = null;
		}
	}

	public byte getPosition(short playerID) {
		for (byte i = 0; i < playerList.length; i++) {
			if (playerList[i] != null) {
				if (playerList[i].getID() == playerID) {
					return i;
				}
			}
		}
		return -1;
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
		byte position = getPosition(id);
		for (int i = 0; i < playerList.length; i++) {
			if (playerList[i] != null) {
				playerList[i].sendChatMessage(position, msg);
			}
		}
	}

	/**
	 * Verschiebt einen Spieler innnerhalb der Lobby
	 * 
	 * @param id
	 * 
	 * @param oldPosition
	 * @param newPosition
	 */
	public void switchPlayers(short id, byte player1, byte player2) {
		if (host.getID() == id) {
			Connection temp = playerList[player1];
			playerList[player1] = playerList[player2];
			playerList[player2] = temp;
			for (Connection c : playerList) {
				if (c != null) {
					if (playerList[player1] == c) {
						c.sendSwitchPlayers(player1, player2, player1);
					} else if (playerList[player2] == c) {
						c.sendSwitchPlayers(player1, player2, player2);
					} else {
						c.sendSwitchPlayers(player1, player2);
					}
				}
			}
		}
	}

	/**
	 * Schließt die Lobby
	 */
	public void closeGame(boolean empty) {
		if (!empty) {
			for (Connection p : playerList) {
				p.sendLeftLobby();
			}
		}
		server.removeLobby(id);
	}

	/**
	 * Gibt den Namen der Lobby zurück
	 * 
	 * @return Name
	 */
	public String getName() {
		return gameName;
	}

	/**
	 * Gibt zurück ob das eingegebene Passwort dem Passwort der Lobby entspricht
	 * 
	 * @return true: Passwörter gleich
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
	 * Gibt zurück ob ein Passwort vorhanden ist
	 * 
	 * @return true: Passwort vorhanden
	 */
	public boolean hasPassword() {
		if (password.equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * Gibt die Namen der Spieler zurück
	 * 
	 * @return Namen der Spieler Das erste Object ist die IDliste als int[], die
	 *         zweite ist die Namensliste als String[]
	 */
	public String[] getPlayerNames() {
		String[] names = new String[playerList.length];
		for (int i = 0; i < playerList.length; i++) {
			if (playerList[i] != null) {
				names[i] = playerList[i].getName();
			}
		}
		return names;
	}

	/**
	 * Gibt die anzahl der beigetretenen Spieler zurück
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
	 * Gibt die ausgewählte Karte zurück
	 * 
	 * @return Karte der Lobby
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Gibt den Namen des Hosts zurück
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
			server.removeLobby(this.id);
		}
	}

	public Connection[] getConnections() {
		return playerList;
	}
}