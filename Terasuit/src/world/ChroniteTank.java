package world;

import java.awt.Point;

public class ChroniteTank implements Unit {

	private static final int BUILDTIME = 1;
	public static final boolean FLYING = false;
	public static final boolean CANATTACKGROUND = true;
	public static final boolean CANATTACKAIR = true;
	private short id;
	private byte playerID;

	private int health;
	private Point position;
	private int direction;
	private boolean running;

	public ChroniteTank(short id, Point position, byte player) {
		this.id = id;
		this.position = position;
		this.playerID = player;
	}

	@Override
	public byte getPlayer() {
		return playerID;
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
	public int getHealth() {
		return health;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRange(boolean ground) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getShootSpeed(boolean ground) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSplashDamage(boolean ground) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFlying() {
		return FLYING;
	}

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDirection(int direction, boolean running) {
		this.direction = direction;
		this.running = running;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public Bullet shoot(Unit[] farestUnits) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public short getID() {
		return id;
	}

	@Override
	public boolean hasInRange(Unit[] units) {
		if (units[0] != null && canAttackGround()) {
			if (Math.abs(getPosition().x - units[0].getPosition().x) - getRange(true) <= 0) {
				return true;
			}
		}
		if (units[01] != null && canAttackAir()) {
			if (Math.abs(getPosition().x - units[0].getPosition().x) - getRange(false) <= 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public byte getType() {
		return WorldConstants.CHRONITETANKID;
	}

	@Override
	public int getBuildTime() {
		return BUILDTIME;
	}

}
