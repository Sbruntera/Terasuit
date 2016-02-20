package world;

public class Armory implements Building {
	
	public static final int MAXLVL = 0;
	public static final int BUILDINGTIME = 110;

	private int lvl = 0;
	private int buildTime;
	private byte position;
	private Unit unit;
	private byte player;
	
	public Armory(byte position, byte player) {
		this.position = position;
		this.player = player;
		buildTime = BUILDINGTIME;
	}

	@Override
	public byte getType() {
		return WorldConstants.ARMORYID;
	}

	@Override
	public byte getPlayer() {
		return player;
	}

	@Override
	public byte getSlotID() {
		return position;
	}

	@Override
	public boolean hasUpgrade() {
		return lvl < MAXLVL;
	}

	@Override
	public byte getUpgrade() {
		return -128;
	}

	@Override
	public boolean isFinished() {
		return buildTime == 0;
	}
	
	@Override
	public void upgrade() {}

	@Override
	public boolean build() {
		buildTime--;
		return buildTime == 0;
	}

	@Override
	public boolean createUnit(byte type, short id, short position) {
		unit = WorldConstants.getUnit(type, id, position);
		return false;
	}

	@Override
	public Unit create() {
		buildTime--;
		return unit;
	}
}
