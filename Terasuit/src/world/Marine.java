package world;

import java.awt.Point;

public class Marine implements Unit {
	
	private static int damage;
	private static int range;
	private static int shootSpeed;
	
	private byte playerID;
	private short id;
	
	private int health;
	private Point position;
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
	public int getSplashDamage() {
		return 0;
	}

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public boolean isFlying() {
		return false;
	}

	@Override
	public void move() {
		position.x += direction;
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
			if (playerID < 2 && position.x + range  >= unit.getPosition().x) {
				return true;
			} else if (playerID > 1 && position.x - range <= unit.getPosition().x) {
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
