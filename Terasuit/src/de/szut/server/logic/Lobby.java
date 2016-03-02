package de.szut.server.logic;

import de.szut.server.connection.Connection;

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
	 * Initialisiert eine neue Lobby
	 * 
	 * @param server
	 *            Hauptserver
	 * @param host
	 *            Host der Lobby
	 * @param gameName
	 *            Name des Spiels
	 * @param password
	 *            Passwort des Spiels
	 * @param map
	 *            Map des Spiels
	 * @param id
	 *            ID des Spiels
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
	 *            Verbindung die hinzugefügt werden soll
	 * @param password
	 *            Passwort das der Nutzer als Lobbypasswort angegeben hat
	 * @return ob der Beitritt erfolgreich war
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
		} else if (!password.equals(this.password)) {
			player.sendFailed((byte) 2);
		} else {
			player.sendFailed((byte) 3);
		}
		return playerJoined;
	}

	/**
	 * Entfernt einen Spieler aus der Lobby
	 * 
	 * @param Sender
	 *            der die Entfernung angefordert hat
	 * 
	 * @param position
	 *            Position des zu entfernenden Spielers
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

	/**
	 * Gibt die Position einer Verbindung mit dieser ID zurück
	 * 
	 * @param playerID
	 *            ID des Spielers
	 * @return Position des Spielers
	 */
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
	public void broadcast(byte[] msg, short id) {
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
	 *            Nummer der Person die diese aktion angefordert hat
	 * 
	 * @param oldPosition
	 *            position1 die getauscht werden soll
	 * @param newPosition
	 *            position2 die getauscht werden soll
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
	 * Gibt die Anzahl der beigetretenen Spieler zurück
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

	/**
	 * Gibt die ID der Lobby zurück
	 * @return ID
	 */
	public byte getID() {
		return id;
	}

	/**
	 * Startet das Spiel
	 * @param id
	 */
	public void startGame(short id) {
		if (host.getID() == id) {
			server.createGame(this);
			server.removeLobby(this.id);
		}
	}

	/**
	 * Gibt alle Verbindungen zurück
	 * @return
	 */
	public Connection[] getConnections() {
		return playerList;
	}
}