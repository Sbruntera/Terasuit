package world;

import java.awt.Point;

public interface Unit {
	
	public void dealDamage(int value);
	public void heal(int value);
	
	public int getHealth();
	public int getDamage();
	public int getRange();
	public int getShootSpeed();
	public Point getPosition();
	
	public void move();
	public void setGoal(int goal);
}
