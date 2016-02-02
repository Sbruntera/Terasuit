package server;

/**
 * 
 * @author Simeon
 *
 */
public enum Map {
	Nightsun ((byte) 0);
	
	private byte id;
	
	Map(byte id) {
		this.id = id;
	}
	
	public byte getID() {
		return id;
	}
}
