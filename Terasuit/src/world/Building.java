package world;

public interface Building {
	
	public boolean dealDamage(int value);
	public int getHealth();
	
	public void upgrade();
	public boolean hasUpgrade();
}
