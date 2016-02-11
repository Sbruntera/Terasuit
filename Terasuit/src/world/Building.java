package world;

public interface Building {
	
	public byte getPlayer();

	public void upgrade();

	public boolean hasUpgrade();

	public byte getSlotID();

	public Unit create();

	public boolean createUnit(byte typeID, short unitID, short position);

	public byte getUnitType();

	public short getUnitID();

	void build();

}
