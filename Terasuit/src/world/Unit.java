package world;

import java.awt.Point;

public interface Unit {
	
	public byte getPlayer();

	public void dealDamage(int value);

	public void heal(int value);

	public int getHealth();

	public int getDamage();

	public int getRange();

	public int getShootSpeed();
	
	public int getSplashDamage();
	
	public boolean isFlying();

	public Point getPosition();

	public void move();

	public void setDirection(int direction, boolean running);

	public boolean isRunning();

	public Bullet shoot(Unit farestUnits);

	public boolean isAlive();

	public short getID();

	public boolean hasInRange(Unit unit);

	public byte getType();
}
