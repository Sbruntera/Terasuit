package de.szut.client.ingame;

public interface Attackable {

	boolean isFlyingEntity();
	
	double getEntityPositionX();
	
	double getEntityPositionY();
	
	int getEntityLive();
	
	boolean isAlive();
	
	void dealDamage(int value);

	void heal(int value);

}
