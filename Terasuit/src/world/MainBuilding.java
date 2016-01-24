package world;

public class MainBuilding implements Building {

	private int health = 5000;

	private int damage;
	private int range;
	private int cooldown;

	private int position = 4;

	public MainBuilding() {
	}

	@Override
	public boolean dealDamage(int value) {
		health -= value;
		return health <= 0;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void upgrade() { // Main Building can't be upgraded
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
}
