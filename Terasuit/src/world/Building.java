package world;

public interface Building {

	public void upgrade();

	public boolean hasUpgrade();

	public int getPosition();

	public void build();

	public boolean createUnit(short id);

}
