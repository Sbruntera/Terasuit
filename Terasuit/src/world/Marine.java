package world;

public class Marine implements Unit {
	
	private static int damage;
	private static int range;
	private static int shootSpeed;
	
	private byte playerID;
	private short id;
	
	private int health;
	private int position;
	private int direction;
	private boolean running;



	public Marine(byte playerID, short id) {
		this.playerID = playerID;
		this.id = id;
	}
	
	@Override
	public byte getPlayer() {
		return playerID;
	}

	@Override
	public void dealDamage(int value) {
		health -= value;
	}

	@Override
	public void heal(int value) {
		health += value;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public int getRange() {
		return range;
	}

	@Override
	public int getShootSpeed() {
		return shootSpeed;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public void move() {
		position += direction;
	}

	@Override
	public void setDirection(int direction, boolean running) {
		this.direction = direction;
		this.running = running;
	}
	
	public boolean isRunning() {
		return running;
	}

	@Override
	public Bullet shoot(Unit farestUnit) {
		return new Bullet(this, farestUnit);
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public short getID() {
		return id;
	}

	@Override
	public boolean hasInRange(Unit unit) {
		if (unit != null) {
			if (playerID < 2 && position + range  >= unit.getPosition()) {
				return true;
			} else if (playerID > 1 && position - range <= unit.getPosition()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public byte getType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
