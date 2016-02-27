package de.szut.server.world;

import java.awt.Point;

public interface Attackable {

	boolean isFlying();
	
	Point getPosition();
	
	int getHealth();
	
	boolean isAlive();
	
	void dealDamage(int value);

	void heal(int value);

}
