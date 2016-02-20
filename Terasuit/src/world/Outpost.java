package world;

public class Outpost implements Building {
	
	public static final int MAXLVL = 1;
	public static final int BUILDINGTIME = 100;

	private int lvl = 1;
	private int buildTime;
	private byte position;
	private byte player;
	
	public Outpost(byte position, byte player) {
		this.position = position;
		this.player = player;
		this. buildTime = BUILDINGTIME;
	}

	@Override
	public void upgrade() {
		this. buildTime = BUILDINGTIME;
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
	public boolean createUnit(byte typeID, short unitID, short position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

	@Override
	public Unit create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte getUpgrade() {
		// TODO Auto-generated method stub
		return 0;
	}
}
