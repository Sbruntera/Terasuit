package world;

public interface Unit {

	public int getPlayer();

	public void dealDamage(int value);

	public void heal(int value);

	public int getHealth();

	public int getDamage();

	public int getRange();

	public int getShootSpeed();

	public int getPosition();

	public void move();

	public void setDirection(int direction);

	public Bullet shoot();

	public boolean isDead();

	public Integer getID();
}
