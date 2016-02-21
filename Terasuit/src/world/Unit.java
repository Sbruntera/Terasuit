package world;

import java.awt.Point;

public interface Unit {

	public byte getPlayer();

	public void dealDamage(int value);

	public void heal(int value);

	public int getHealth();

	public boolean canAttackGround();

	public boolean canAttackAir();

	public int getDamage(boolean ground);

	public int getRange(boolean ground);

	public int getShootSpeed(boolean ground);

	public int getSplashDamage(boolean ground);

	public boolean isFlying();

	public Point getPosition();

	public void move();

	public void setDirection(int direction, boolean running);

	public boolean isRunning();

	public Bullet shoot(Unit[] farestUnits);

	public boolean isAlive();

	public short getID();

	public boolean hasInRange(Unit[] nearesUnits);

	public byte getType();

	public int getBuildTime();
}
