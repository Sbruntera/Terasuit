package de.szut.server.world;

public interface Attackable {

	byte getPlayer();

	boolean isFlying();
	
	double getXPosition();

	double getYPosition();
	
	int getHealth();
	
	boolean isAlive();
	
	void dealDamage(int value);

	void heal(int value);

	boolean wasAlreadyDead();

	void setAlreadyDead();

}
