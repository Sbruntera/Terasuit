package de.szut.server.world;

import java.awt.Point;

public class Hospital implements Building {
	
	public static final int MAXLVL = 1;
	public static final int BUILDINGTIME = 100;

	private int lvl = 0;
	private int buildTime;
	private int createTime;
	private byte position;
	private byte player;
	
	private Unit unit;

	private static final int[][] prices =  {{40, 30, 10, 00},
											{65, 00, 00, 00}};
	private static final byte[] unitIDs = { 13, 14, 15 };
	private static final int[] numberOfUnits = { 1, 3 };
	
	public Hospital(byte position, byte player) {
		this.position = position;
		this.player = player;
		buildTime = BUILDINGTIME;
	}

	@Override
	public byte getType() {
		switch (lvl) {
		case (0):
			return WorldConstants.HOSPITALID;
		case (1):
			return WorldConstants.WARSANCTUMID;
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
	public int[] getPrice(int lvl) {
		if (lvl > 0) {
			return prices[lvl];
		} else {
			return prices[this.lvl];
		}
	}

	@Override
	public int[] getPrice() {
		return prices[lvl+1];
	}
	
	@Override
	public int getLevel() {
		return lvl;
	}

	@Override
	public boolean hasUpgrade() {
		return lvl < MAXLVL;
	}

	@Override
	public byte getUpgrade() {
		switch (lvl) {
		case (0):
			return WorldConstants.WARSANCTUMID;
		default:
			return -128;
		}
	}
	
	@Override
	public void cancelUpgrade() {
		buildTime = 0;
		lvl--;
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
		if (createTime <= 0 && buildTime <= 0) {
			boolean contains = false;
			for (int i = 0; i < numberOfUnits[lvl]; i++) {
				if (unitIDs[i] == typeID) {
					contains = true;
				}
			}
			if (contains) {
				unit = WorldConstants.getNewUnit(typeID, unitID, position, player);
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
