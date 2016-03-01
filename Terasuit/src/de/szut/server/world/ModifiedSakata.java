package de.szut.server.world;

import java.awt.Point;

public class ModifiedSakata extends Unit {

	private static final int BUILDTIME = 1;
	public static final boolean FLYING = false;
	private static final boolean CANATTACKGROUND = true;
	private static final boolean CANATTACKAIR = true;
	public static final int[] PRICE = new int[] {0, 0, 0, 50};
	public static final int MAXHEALTH = 450;

	private static double speed = 2.4;
	private static int damage = 65;
	private static int range = 140;
	private static int shootSpeed = 9;
	private int splashDamage = 0;
	private double bulletSpeed = 10;
	
	public ModifiedSakata(short id, Point position, byte player) {
		this.id = id;
		this.position = position;
		this.playerID = player;
		this.health = MAXHEALTH;
	}

	@Override
	public byte getType() {
		return WorldConstants.MODIFIEDSAKATAID;
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
	public Bullet shoot(Attackable[] farestUnits) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getSpeed() {
		return speed;
	}
}
