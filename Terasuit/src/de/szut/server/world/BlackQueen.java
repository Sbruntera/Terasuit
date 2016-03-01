package de.szut.server.world;

import java.awt.Point;

public class BlackQueen extends Unit {

	private static final int BUILDTIME = 1;
	public static final boolean FLYING = true;
	private static final boolean CANATTACKGROUND = true;
	private static final boolean CANATTACKAIR = true;
	public static final int[] PRICE = new int[] {30, 25, 50, 0};
	public static final int MAXHEALTH = 550;

	private static double speed = 4.4;
	private static int damage = 150;
	private static int range = 190;
	private static int shootSpeed = 20;
	private int splashDamage = 0;
	private double bulletSpeed = 18;
	private int cooldown;
	
	public BlackQueen(short id, Point position, byte player) {
		this.id = id;
		this.xPosition = position.getX();
		this.yPosition = position.getY();
		this.playerID = player;
		this.health = MAXHEALTH;
	}

	@Override
	public byte getType() {
		return WorldConstants.BLACKQUEENID;
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
