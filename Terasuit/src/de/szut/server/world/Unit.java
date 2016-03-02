package de.szut.server.world;

public abstract class Unit implements Attackable {
	
	protected short id;
	protected byte playerID;

	protected double xPosition;
	protected double yPosition;
	protected int direction;
	protected boolean running;
	protected int health;
	private boolean alreadyDead;

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
			if (Math.abs(getXPosition() - attackables[0].getXPosition()) - getRange(true) <= 0) {
				return true;
			}
		}
		if (attackables[1] != null && canAttackAir()) {
			if (Math.abs(getXPosition() - attackables[1].getXPosition()) - getRange(false) <= 0) {
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
	public double getXPosition() {
		return xPosition;
	}

	@Override
	public double getYPosition() {
		return yPosition;
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
		if (xPosition >= 294 && xPosition <= 1344) {
			xPosition = xPosition + getSpeed() * direction;
		}
	}

	@Override
	public boolean wasAlreadyDead() {
		return alreadyDead;
	}

	@Override
	public void setAlreadyDead() {
		alreadyDead = true;
	}
}
