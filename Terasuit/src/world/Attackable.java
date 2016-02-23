package world;

import java.awt.Point;

public interface Attackable {

	Point getPosition();
	
	int getHealth();
	
	boolean isAlive();
	
	void dealDamage(int value);

	void heal(int value);

}
