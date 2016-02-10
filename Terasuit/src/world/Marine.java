package world;

public class Marine implements Unit {
	
	private int damage;
	private int range;
	private int shootSpeed;
	
	private short playerID;
	private short id;
	
	private int health;
	private int position;
	private int direction;
	private boolean running;



	public Marine(short playerID, short id) {
		this.playerID = playerID;
		this.id = id;
	}
	
	@Override
	public short getPlayer() {
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

}
