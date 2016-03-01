package de.szut.server.world;

import java.awt.geom.Point2D;

public abstract class Unit implements Attackable {
	
	protected short id;
	protected byte playerID;

	protected Point2D position;
	protected int direction;
	protected boolean running;
	protected int health;

	public abstract byte getType();

	public short getID() {
		return id;
	}
	
	@Override
	public byte getPlayer() {
		return playerID;
	}

	public abstract boolean isFlying();

	public abstract int getBuildTime();
	
	public abstract int[] getPrice();

	public abstract boolean canAttackGround();

	public abstract boolean canAttackAir();

	public abstract int getDamage(boolean flying);

	public abstract int getRange(boolean flying);

	public abstract int getShootSpeed(boolean flying);

	public abstract int getSplashDamage(boolean flying);

	public abstract double getBulletSpeed(boolean flying);

	public boolean hasInRange(Attackable[] attackables) {
		if (attackables[0] != null && canAttackGround()) {
			if (Math.abs(getPosition().getY() - attackables[0].getPosition().getX()) - getRange(true) <= 0) {
				return true;
			}
		}
		if (attackables[01] != null && canAttackAir()) {
			if (Math.abs(getPosition().getX() - attackables[0].getPosition().getX()) - getRange(false) <= 0) {
				return true;
			}
		}
		return false;
	}
	
	public abstract Bullet shoot(Attackable[] attackables);

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
	
	public abstract double getSpeed();

	@Override
	public Point2D getPosition() {
		return position;
	}

	public boolean isRunning() {
		return running;
	}

	public void setDirection(int direction) {
		this.direction = direction - direction/3 - 1;
		running = (direction == 3);
	}

	public int getDirection() {
		return direction;
	}

	public void move() {
		if (position.getX() >= 294 && position.getX() <= 1344) {
			position.setLocation(position.getX() + getSpeed() * direction, position.getY());
		}
	}
}
