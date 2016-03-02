package de.szut.server.world;

import java.awt.Point;

public class ChroniteTank extends Unit {

	private static final int BUILDTIME = 1;
	public static final boolean FLYING = false;
	private static final boolean CANATTACKGROUND = true;
	private static final boolean CANATTACKAIR = false;
	public static final int[] PRICE = new int[] {5, 0, 10, 0};
	public static final int MAXHEALTH = 600;
	private static final double SPEED = 3.2;
	private static final int DAMAGE = 60;
	private static final int RANGE = 225;
	private static final int SHOOTSPEED = 35;
	private static final int SPLASHDAMAGE = 2;
	private static final double BULLETSPEED = 14;
	
	private int cooldown = 0;
	
	public ChroniteTank(short id, Point position, byte player) {
		this.id = id;
		this.xPosition = position.getX();
		this.yPosition = position.getY();
		this.playerID = player;
		this.health = MAXHEALTH;
	}

	@Override
	public byte getType() {
		return WorldConstants.CHRONITETANKID;
	}

	@Override
	public boolean isFlying() {
		return FLYING;
	}

	@Override
	public int getBuildTime() {
		return BUILDTIME;
	}

	@Override
	public int[] getPrice() {
		return PRICE;
	}

	@Override
	public boolean canAttackGround() {
		return CANATTACKGROUND;
	}

	@Override
	public boolean canAttackAir() {
		return CANATTACKAIR;
	}

	@Override
	public int getDamage(boolean ground) {
		return DAMAGE;
	}

	@Override
	public int getRange(boolean ground) {
		return RANGE;
	}

	@Override
	public int getShootSpeed(boolean ground) {
		return SHOOTSPEED;
	}

	@Override
	public int getSplashDamage(boolean ground) {
		return SPLASHDAMAGE;
	}

	@Override
	public double getBulletSpeed(boolean ground) {
		return BULLETSPEED;
	}

	@Override
	public Bullet shoot(Attackable[] nearestUnits) {
		if (cooldown <= 0) {
			if (CANATTACKGROUND && nearestUnits[0] != null) {
				cooldown = SHOOTSPEED;
				return new Bullet(this, nearestUnits[0]);
			}
		} else {
			cooldown--;
		}
		return null;
	}

	@Override
	public double getSpeed() {
		return SPEED;
	}
}
