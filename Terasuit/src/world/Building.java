package world;

public interface Building {

	public boolean dealDamage(int value);

	public int getHealth();

	public void upgrade(byte bytes);

	public boolean hasUpgrade();

	public int getPosition();

	public void build();
}
