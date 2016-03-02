package de.szut.server.world;

import java.awt.Point;

public class Scout extends Unit {

	private static final int BUILDTIME = 1;
	public static final boolean FLYING = true;
	private static final boolean CANATTACKGROUND = true;
	private static final boolean CANATTACKAIR = true;
	public static final int[] PRICE = new int[] {3, 7, 0, 0};
	public static final int MAXHEALTH = 140;
	private static final double SPEED = 4.4;
	private static final int DAMAGE = 40;
	private static final int RANGE = 210;
	private static final int SHOOTSPEED = 9;
	private static final int SPLASHDAMAGE = 0;
	private static final double BULLETSPEED = 22;
	
	private int cooldown;
	
	public Scout(short id, Point position, byte player) {
		this.id = id;
		this.xPosition = position.getX();
		this.yPosition = position.getY();
		this.playerID = player;
		this.health = MAXHEALTH;
	}

	@Override
	public byte getType() {
		return WorldConstants.SCOUTID;
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
		if (cooldown  <= 0) {
			if (CANATTACKGROUND && nearestUnits[0] != null) {
				cooldown = SHOOTSPEED;
				return new Bullet(this, nearestUnits[0]);
			} else if (CANATTACKAIR && nearestUnits[1] != null) {
				cooldown = SHOOTSPEED;
				return new Bullet(this, nearestUnits[1]);
			}
		}
		cooldown--;
		return null;
	}

	@Override
	public double getSpeed() {
		return SPEED;
	}
}
