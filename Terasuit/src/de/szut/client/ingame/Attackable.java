package de.szut.client.ingame;

public interface Attackable {

	boolean isFlyingEntity();
	
	int getEntityPositionX();
	
	int getEntityPositionY();
	
	int getEntityLive();
	
	boolean isAlive();
	
	void dealDamage(int value);

	void heal(int value);

	boolean died();

}
