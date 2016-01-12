package server;

import connection.Connection;

public class Lobby {

	private static final int MAXPLAYERS = 4;
	private Connection[] playerList = new Connection[MAXPLAYERS];
	private Server server;
	private String gameName;
	private String password;
	private Map map;
	private Connection host;
	private int id;
	
	/**
	 * Erstellt ein neues Spiel
	 * @param lobby
	 * @param host
	 * @param gameName
	 * @param password
	 * @param map
	 */
	public Lobby(Server server, Connection host, String gameName, String password, Map map) {
		playerList[0] = host;
		this.host = host;
		this.server = server;
		this.gameName = gameName;
		this.password = password;
		this.map = map;
	}
	
	/**
	 * Fügt einen Spieler zu der Lobby hinzu
	 * @param player
	 */
	public void addPlayer(Connection player) {
		boolean playerJoined = false;
		int i = 0;
		while (!playerJoined) {
			if (playerList[i] == null) {
				playerList[i] = player;
				playerJoined = true;
			}
			else {
				if (i+1 < MAXPLAYERS) {
					i++;
				}
			}
		}
	}
	
	/**
	 * Entfernt einen Spieler aus der Lobby
	 * @param position
	 */
	public void removePlayer(int position) {
		if (playerList[position] != null) { // An dieser Position existiert ein Spieler
			if (playerList[position] == host) { // Der Spieler ist Host
				boolean playerFound = false;
				int actualPlayer = 0;
				while (!playerFound && actualPlayer < 4) {
					if (playerList[actualPlayer] == null) {
						actualPlayer++;
					}
					else {
						playerFound = true;
						host = playerList[actualPlayer];
					}
				}
			}
			playerList[position].leaveLobby();
			playerList[position] = null;
		}
		if (getNumberOfPlayers() == 0) {
			closeGame();
		}
	}
	
	/**
	 * Verschiebt einen Spieler innnerhalb der Lobby
	 * @param oldPosition
	 * @param newPosition
	 */
	public void movePlayer(int oldPosition, int newPosition) {
		if (playerList[oldPosition] != null && playerList[newPosition] == null) {
			playerList[newPosition] = playerList[oldPosition];
		}
	}
	
	/**
	 * Schließt die Lobby
	 */
	public void closeGame() {
		for (Connection p : playerList) {
			p.leaveLobby();
		}
		server.removeLobby(this);
	}
	
	/**
	 * Gibt den Namen der Lobby zurück
	 * @return Name
	 */
	public String getName() {
		return gameName;
	}
	
	/**
	 * Gibt zurück ob das eingegebene Passwort dem Passwort der Lobby entspricht
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
	 * @return true: Passwort vorhanden
	 */
	public boolean hasPassword() {
		if (password == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Gibt die anzahl der beigetretenen Spieler zurück
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
	 * @return Karte der Lobby
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * Gibt den Namen des Hosts zurück
	 * @return Hostname
	 */
	public String getHostname() {
		return playerList[0].getName();
	}
	
	public int getID() {
		return id;
	}
}