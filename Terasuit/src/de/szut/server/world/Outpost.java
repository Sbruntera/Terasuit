package de.szut.server.world;

import java.awt.Point;

public class Outpost implements Building {

	public static final int MAXLVL = 2;
	public static final int BUILDINGTIME = 110;

	private int lvl = 0;
	private int buildTime;
	private int createTime;
	private byte position;
	private byte player;

	private Unit unit;

	private static final byte[] unitIDs = { 1, 2, 3, 4, 5, 6 };
	private static final int[] numberOfUnits = { 2, 4, 6 };

	public Outpost(byte position, byte player) {
		this.position = position;
		this.player = player;
		buildTime = BUILDINGTIME;
	}

	@Override
	public byte getType() {
		switch (lvl) {
		case (0):
			return WorldConstants.OUTPOSTID;
		case (1):
			return WorldConstants.BARRACKSID;
		case (2):
			return WorldConstants.ARSENALID;
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
			return WorldConstants.BARRACKSID;
		case (1):
			return WorldConstants.ARSENALID;
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
		if (buildTime != 0) {
			buildTime--;
		}
		return buildTime == 0;
	}

	@Override
	public boolean createUnit(byte typeID, short unitID, Point position) {
		if (createTime <= 0 && buildTime <= 0) {
			boolean contains = false;
			for (int i = 0; i < numberOfUnits[lvl]; i++) {
				if (unitIDs[i] == typeID) {
					contains = true;
				}
			}
			if (contains) {
				unit = WorldConstants.getUnit(typeID, unitID, position, player);
				createTime = unit.getBuildTime();
				return true;
			}
		}
		return false;
	}

	@Override
	public Unit create() {
		if (createTime >= 0) {
			createTime--;
		}
		if (createTime == 0) {
			return unit;
		} else {
			return null;
		}
	}
}
