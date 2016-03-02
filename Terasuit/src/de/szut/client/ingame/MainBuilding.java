package de.szut.client.ingame;

/**
 * 
 * @author Simeon
 *
 */
public class MainBuilding extends Buildings implements Attackable {

	private int health;

	@Override
	public boolean isFlyingEntity() {
		return false;
	}

	@Override
	public double getEntityPositionX() {
		return getX();
	}

	@Override
	public double getEntityPositionY() {
		return getY();
	}

	@Override
	public int getEntityLive() {
		return health;
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public void dealDamage(int value) {
		health -= value;
	}

	@Override
	public void heal(int value) {
		health += value;
	}

}
