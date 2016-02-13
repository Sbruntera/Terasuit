package logic;

public class Lobby {

	private byte iD;
	private String name;
	private byte mapID;
	private boolean hasPassword;
	private byte numberOfPlayers;
	
	public Lobby(byte iD, String name, byte mapID, boolean hasPassword, byte numberOfPlayers) {
		this.iD = iD;
		this.name = name;
		this.mapID = mapID;
		this.hasPassword = hasPassword;
		this.numberOfPlayers = numberOfPlayers;
	}

	/**
	 * @return the id
	 */
	public byte getID() {
		return iD;
	}

	/**
	 * @return the id
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the id
	 */
	public byte getMapID() {
		return mapID;
	}

	/**
	 * @return the id
	 */
	public boolean hasPassword() {
		return hasPassword;
	}

	/**
	 * @return the id
	 */
	public byte getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	
}
