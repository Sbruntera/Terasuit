package de.szut.server.world;

import java.awt.Point;

public class MainBuilding implements Building, Attackable {

	private int health = 5000;

	private Point position;
	private byte place;
	private byte player;


	public MainBuilding(byte player) {
		this.player = player;
		if ((player&2)==2) {
			position = new Point(1440, 380);
		} else {
			position = new Point(130, 380);
		}
	}
	
	@Override
	public boolean isFlying() {
		return false;
	}

	@Override
	public void upgrade() { //Main Building can not be upgraded
	}

	@Override
	public boolean hasUpgrade() {
		return false;
	}

	@Override
	public byte getSlotID() {
		return place;
	}

	@Override
	public boolean build() { // MainBuilding can not be Builded
		return false;
	}

	@Override
	public boolean createUnit(byte typeID, short unitID, Point position) { //MainBuilding can not create Units
		return false;
	}

	@Override
	public byte getType() {
		return 0;
	}

	@Override
	public int[] getPrice(int lvl) {
		return null;
	}

	@Override
	public int[] getPrice() {
		return null;
	}

	@Override
	public Unit create() {
		return null;
	}

	@Override
	public byte getUpgrade() {
		return 0;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public void dealDamage(int value) {
		health -= value;
	}

	@Override
	public void heal(int value) {
		health += value;
	}

	@Override
	public byte getPlayer() {
		return player;
	}
	
	@Override
	public int getLevel() {
		return 0;
	}
	
	@Override
	public void cancelUpgrade() {
	}

	@Override
	public double getXPosition() {
		return position.getX();
	}

	@Override
	public double getYPosition() {
		return position.getY();
	}

	@Override
	public boolean wasAlreadyDead() {
		return false;
	}

	@Override
	public void setAlreadyDead() {
	}
}
