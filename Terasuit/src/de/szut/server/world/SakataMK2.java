package de.szut.server.world;

import java.awt.Point;

public class SakataMK2 extends Unit {

	private static final int BUILDTIME = 1;
	public static final boolean FLYING = false;
	private static final boolean CANATTACKGROUND = false;
	private static final boolean CANATTACKAIR = true;
	public static final int[] PRICE = new int[] {0, 21, 0, 0};
	public static final int MAXHEALTH = 200;
	private static final double SPEED = 4.4;
	private static final int DAMAGE = 200;
	private static final int RANGE = 200;
	private static final int SHOOTSPEED = 40;
	private static final int SPLASHDAMAGE = 0;
	private static final double BULLETSPEED = 20;
	
	private int cooldown;
	
	public SakataMK2(short id, Point position, byte player) {
		this.id = id;
		this.xPosition = position.getX();
		this.yPosition = position.getY();
		this.playerID = player;
		this.health = MAXHEALTH;
	}

	@Override
	public byte getType() {
		return WorldConstants.SAKATAMK2ID;
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
			if (CANATTACKAIR && nearestUnits[1] != null) {
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
