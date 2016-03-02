package de.szut.client.logic;

public class Lobby {

	private byte iD;
	private String name;
	private byte mapID;
	private boolean hasPassword;
	private byte numberOfPlayers;
	
	/**
	 * Initialisiert eine Lobby
	 * @param iD
	 * @param name
	 * @param mapID
	 * @param hasPassword
	 * @param numberOfPlayers
	 */
	public Lobby(byte iD, String name, byte mapID, boolean hasPassword, byte numberOfPlayers) {
		this.iD = iD;
		this.name = name;
		this.mapID = mapID;
		this.hasPassword = hasPassword;
		this.numberOfPlayers = numberOfPlayers;
	}

	/**
	 * Gibt die ID zurück
	 * @return the id
	 */
	public byte getID() {
		return iD;
	}

	/**
	 * Gibt den Namen zurück 
	 * @return der Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt die MapID zurück
	 * @return
	 */
	public byte getMapID() {
		return mapID;
	}

	/**
	 * Gibt zurück ob die Lobby ein Passwort hat
	 * @return
	 */
	public boolean hasPassword() {
		return hasPassword;
	}

	/**
	 * Gibt die Zahl der Spieler zurück
	 * @return
	 */
	public byte getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	
}