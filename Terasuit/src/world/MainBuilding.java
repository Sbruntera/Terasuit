package world;

public class MainBuilding implements Building, Unit {

	private int health = 5000;

	private int damage;
	private int range;
	private int cooldown;

	private int position = 4;

	public MainBuilding() {
	}

	@Override
	public void dealDamage(int value) {
		health -= value;
	}

	public int getHealth() {
		return health;
	}

	@Override
	public void upgrade() { //Main Building can not be upgraded
	}

	@Override
	public boolean hasUpgrade() {
		return false;
	}

	public int getDamage() {
		return damage;
	}

	public int getRange() {
		return range;
	}

	public boolean canShoot() {
		return cooldown <= 0;
	}

	public boolean coolDown() {
		if (cooldown > 0) {
			cooldown -= 1;
		}
		return cooldown <= 0;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public void build() { // MainBuilding can not be Builded
	}

	@Override
	public short getPlayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void heal(int value) { // MainBuilding can not be healed
	}

	@Override
	public int getShootSpeed() { // MainBuilding can not shoot
		return 0;
	}

	@Override
	public void move() {  // MainBuilding can not move
	}

	@Override
	public void setDirection(int direction, boolean running) { // MainBuilding can not move
	}

	@Override
	public boolean isRunning() { // MainBuilding can not run
		return false;
	}

	@Override
	public Bullet shoot(Unit farestUnits) { // MainBuilding can not shoot
		return null;
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public short getID() {
		return WorldConstants.MAINBUILDINGID;
	}
}
