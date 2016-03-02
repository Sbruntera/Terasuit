package de.szut.client.logic;

/**
 * 
 * @author Simeon
 *
 */
public class GameLobby {
	
	private String[] players;

	/**
	 * Initialisiert eine Lobby
	 * @param players
	 */
	public GameLobby(String[] players) {
		this.players = players;
	}
	
	/**
	 * Gibt den Namen eines Nutzers zurück
	 * @param id
	 * @return
	 */
	public String getPlayerName(byte id) {
		return players[id];
	}

	/**
	 * Gibt die Namen der Spieler zurück 
	 * @return
	 */
	public String[] getPlayerNames() {
		return players;
	}
	
	/**
	 * Fügt einen Spieler hinzu
	 * @param id
	 * @param name
	 */
	public void addPlayer(byte id, String name) {
		players[id] = name;
	}
	
	/**
	 * Entfernt einen Spieler
	 * @param id
	 */
	public void removePlayer(byte id) {
		players[id] = "";
	}
	
	/**
	 * Tauscht zwei Spieler
	 * @param id1
	 * @param id2
	 */
	public void switchPlayers(byte id1, byte id2) {
		String playerTemp = players[id1];
		players[id1] = players[id2];
		players[id2] = playerTemp;
	}
}
