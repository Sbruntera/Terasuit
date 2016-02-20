package world;

public class Outpost implements Building {
	
	public static final int MAXLVL = 2;
	public static final int BUILDINGTIME = 100;

	private int lvl = 0;
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
	public boolean build() {
		buildTime--;
		return buildTime < 0;
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
		switch (lvl) {
		case (0):
			return WorldConstants.BARRACKSID;
		default:
			return -128;
		}
	}

	@Override
	public byte getType() {
		switch (lvl) {
		case (0):
			return WorldConstants.OUTPOSTID;
		case (1):
			return WorldConstants.BARRACKSID;
		default:
			return -128;
		}
	}

	@Override
	public boolean isFinished() {
		return buildTime < 0;
	}
}
