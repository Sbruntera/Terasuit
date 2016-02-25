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
			position = new Point(1474, 414);
		} else {
			position = new Point(164, 414);
		}
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
	public Unit create() {
		return null;
	}

	@Override
	public byte getUpgrade() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	public Point getPosition() {
		return position;
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
}
