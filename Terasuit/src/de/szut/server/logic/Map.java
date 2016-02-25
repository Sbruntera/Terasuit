package de.szut.server.logic;

/**
 * 
 * @author Simeon
 *
 */
public enum Map {
	
	Nightsun ((byte) 2);
	
	private byte id;
	
	Map(byte id) {
		this.id = id;
	}
	
	public byte getID() {
		return id;
	}
}
