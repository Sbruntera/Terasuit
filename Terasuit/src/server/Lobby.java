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
		host.sendGameJoin(this, true);
	}

	/**
	 * Fügt einen Spieler zu der Lobby hinzu
	 * 
	 * @param player
	 * @param password 
	 */
	public boolean addPlayer(Connection player, String password) {
		boolean playerJoined = false;
		int i = 0;
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
						c.sendGameJoin(this, false);
					} else {
						c.sendPlayerJoined((byte) i, player.getName());
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
	public void removePlayer(short senderID, short playerID) {
		if (senderID == playerID || senderID == host.getID()) {
			boolean found = false;
			byte position = 0;
			for (byte i = 0; i < playerList.length; i++) {
				if (playerList[i] != null) {
					if (playerList[i].getID() == playerID) {
						position = i;
						found = true;
					}
				}
			}
			for (byte i = 0; i < playerList.length; i++) {
				if (playerList[i] != null) {
					System.out.println("tadada");
					if (playerList[i].getID() != playerID) {
						playerList[i].sendPlayerLeftLobby(position);
						if (playerList[position] == host) {
							host = playerList[i];
							host.sendGetHost();
						}
					} else {
						System.out.println("LeftLobby" + i);
						playerList[i].sendLeftLobby();
					}
				}
			}
			if (found) {
				if (playerList[position] == host) { // Der Spieler ist Host
					boolean playerFound = false;
					int actualPlayer = 0;
					while (!playerFound && actualPlayer < MAXPLAYERS) {
						if (playerList[actualPlayer] != null
								&& playerList[actualPlayer] != host) {
							playerFound = true;
							System.out.println(actualPlayer + "<ghpo<uh");
							host = playerList[actualPlayer];
						} else {
							actualPlayer++;
						}
					}
					if (!playerFound) {
						closeGame(true);
					}
				}
				playerList[position] = null;
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
		System.out.println(msg);
		byte position = 0;
		for (byte i = 0; i < playerList.length; i++) {
			if (playerList[i] != null) {
				if (playerList[i].getID() == id) {
					position = i;
				}
			}
		}
		System.out.println(msg + position);
		for (int i = 0; i < playerList.length; i++) {
			if (playerList[i] != null) {
				playerList[i].sendChatMessage(position, msg);
			}
		}
	}

	/**
	 * Verschiebt einen Spieler innnerhalb der Lobby
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
					c.sendSwitchPlayers(player1, player2);
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
		if (password == null) {
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
			//System.out.println("Ich bin i;" + i);
			if (playerList[i] != null) {
				players++;
			}
		}
		System.out.println(players + "ist");
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
		}
	}

	public Connection[] getConnections() {
		return playerList;
	}
}