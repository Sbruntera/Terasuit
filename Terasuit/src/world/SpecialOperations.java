package world;

public class SpecialOperations implements Building {
	
	public static final int MAXLVL = 1;
	public static final int BUILDINGTIME = 110;

	private int lvl = 0;
	private int buildTime;
	private byte position;
	private byte player;
	
	public SpecialOperations(byte position, byte player) {
		this.position = position;
		this.player = player;
	}

	@Override
	public byte getType() {
		return WorldConstants.SPECIALOPERATIONSID;
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
	public boolean createUnit(byte typeID, short unitID, short position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Unit create() {
		// TODO Auto-generated method stub
		return null;
	}
}
