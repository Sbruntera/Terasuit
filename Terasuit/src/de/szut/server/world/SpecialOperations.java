package de.szut.server.world;

import java.awt.Point;

public class SpecialOperations implements Building {
	
	public static final int MAXLVL = 0;
	public static final int BUILDINGTIME = 100;

	private int lvl = 0;
	private int buildTime;
	private byte position;
	private byte player;
	
	private static final int[] prices =  {80, 30, 30, 00};
	
	public SpecialOperations(byte position, byte player) {
		this.position = position;
		this.player = player;
		buildTime = BUILDINGTIME;
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
	public int[] getPrice(int lvl) {
		return prices;
	}

	@Override
	public int[] getPrice() {
		return null;
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
		return -128;
	}
	
	@Override
	public void cancelUpgrade() {
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
	public boolean createUnit(byte typeID, short unitID, Point position) {
		return false;
	}

	@Override
	public Unit create() {
		return null;
	}
}
