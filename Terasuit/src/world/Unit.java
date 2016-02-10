package world;

public interface Unit {

	public short getPlayer();

	public void dealDamage(int value);

	public void heal(int value);

	public int getHealth();

	public int getDamage();

	public int getRange();

	public int getShootSpeed();

	public int getPosition();

	public void move();

	public void setDirection(int direction, boolean running);

	public boolean isRunning();

	public Bullet shoot(Unit farestUnits);

	public boolean isAlive();

	public short getID();
}
