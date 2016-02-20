package world;

public class Armory implements Building {
	
	public static final int MAXLVL = 1;
	public static final int BUILDINGTIME = 100;

	private int lvl = 1;
	private int buildTime;
	private byte position;
	private Unit unit;
	private byte player;
	
	public Armory(byte position, byte player) {
		this.position = position;
		this.player = player;
	}
	
	@Override
	public void upgrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasUpgrade() {
		return lvl < MAXLVL;
	}

	@Override
	public byte getSlotID() {
		return position;
	}

	@Override
	public void build() {
		buildTime--;
	}

	@Override
	public Unit create() {
		buildTime--;
		return unit;
	}
	

	@Override
	public boolean createUnit(byte type, short id, short position) {
		unit = WorldConstants.getUnit(type, id, position);
		return false;
	}

	@Override
	public byte getPlayer() {
		return player;
	}

	@Override
	public byte getUpgrade() {
		// TODO Auto-generated method stub
		return 0;
	}
}
