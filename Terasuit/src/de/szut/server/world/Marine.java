package de.szut.server.world;

import java.awt.Point;

public class Marine extends Unit {

	private static final int BUILDTIME = 1;
	public static final boolean FLYING = false;
	private static final boolean CANATTACKGROUND = true;
	private static final boolean CANATTACKAIR = true;
	public static final int[] PRICE = new int[] {0, 0, 2, 0};
	public static final int MAXHEALTH = 60;

	private static double speed = 4;
	private static int damage = 15;
	private static int range = 180;
	private static int shootSpeed = 10;
	private int splashDamage = 0;
	private double bulletSpeed = 20;
	private int cooldown;
	
	public Marine(short id, Point position, byte player) {
		this.id = id;
		this.xPosition = position.getX();
		this.yPosition = position.getY();
		this.playerID = player;
		this.health = MAXHEALTH;
	}

	@Override
	public byte getType() {
		return WorldConstants.MARINEID;
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
		return damage;
	}

	@Override
	public int getRange(boolean ground) {
		return range;
	}

	@Override
	public int getShootSpeed(boolean ground) {
		return shootSpeed;
	}

	@Override
	public int getSplashDamage(boolean ground) {
		return splashDamage;
	}

	@Override
	public double getBulletSpeed(boolean ground) {
		return bulletSpeed;
	}

	@Override
	public Bullet shoot(Attackable[] nearestUnits) {
		if (cooldown  <= 0) {
			if (CANATTACKGROUND && nearestUnits[0] != null) {
				return new Bullet(this, nearestUnits[0]);
			} else if (CANATTACKAIR && nearestUnits[1] != null) {
				return new Bullet(this, nearestUnits[1]);
			}
		} else {
			cooldown--;
		}
		return null;
	}

	@Override
	public double getSpeed() {
		return speed;
	}
}
