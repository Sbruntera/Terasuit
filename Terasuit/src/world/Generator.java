package world;

import java.awt.Point;

public class Generator implements Building {

	public static final int MAXLVL = 1;
	public static final int BUILDINGTIME = 110;

	private int lvl = 0;
	private int buildTime;
	private int createTime;
	private byte position;
	private byte player;
	
	private Unit unit;

	public Generator(byte position, byte player) {
		this.position = position;
		this.player = player;
		buildTime = BUILDINGTIME;
	}

	@Override
	public byte getType() {
		switch (lvl) {
		case (0):
			return WorldConstants.GENERATORID;
		case (1):
			return WorldConstants.SOLARGRIDID;
		default:
			return -128;
		}
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
		switch (lvl) {
		case (0):
			return WorldConstants.SOLARGRIDID;
		default:
			return -128;
		}
	}

	@Override
	public boolean isFinished() {
		return buildTime == 0;
	}

	@Override
	public void upgrade() {
		if (buildTime == 0) {
			buildTime = BUILDINGTIME;
			lvl++;
		}
	}

	@Override
	public boolean build() {
		buildTime--;
		return buildTime == 0;
	}

	@Override
	public boolean createUnit(byte typeID, short unitID, Point position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Unit create() {
		// TODO Auto-generated method stub
		return null;
	}
}
