package world;

public class Bank implements Building {
	
	public static final int MAXLVL = 1;
	public static final int BUILDINGTIME = 110;

	private int lvl = 1;
	private int buildTime;
	private byte position;
	private byte player;
	
	public Bank(byte position, byte player) {
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
	public Unit create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}

	@Override
	public byte getUpgrade() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getType() {
		return WorldConstants.BANKID;
	}

	@Override
	public boolean isFinished() {
		return buildTime < 0;
	}
}
