package de.szut.server.world;

import java.awt.Point;

public class Bank implements Building {
	
	public static final int MAXLVL = 1;
	public static final int BUILDINGTIME = 100;

	private int lvl = 0;
	private int buildTime;
	private int createTime;
	private byte position;
	private byte player;
	
	private Unit unit;
	
	private static final int[][] prices =  {{50, 00, 00, 00},
											{50, 00, 00, 00}};
	
	public Bank(byte position, byte player) {
		this.position = position;
		this.player = player;
		buildTime = BUILDINGTIME;
	}

	@Override
	public byte getType() {
		switch (lvl) {
		case (0):
			return WorldConstants.BANKID;
		case (1):
			return WorldConstants.TREASURYID;
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
			return WorldConstants.TREASURYID;
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
		if (buildTime >= 0) {
			buildTime--;
		}
		return buildTime == 0;
	}

	@Override
	public boolean createUnit(byte typeID, short unitID, Point position) {
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
