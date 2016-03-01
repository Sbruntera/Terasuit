package de.szut.server.world;

import java.awt.Point;
import java.awt.geom.Point2D;

public interface Attackable {

	byte getPlayer();

	boolean isFlying();
	
	Point2D getPosition();
	
	int getHealth();
	
	boolean isAlive();
	
	void dealDamage(int value);

	void heal(int value);

}
