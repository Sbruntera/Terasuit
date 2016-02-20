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
	public int getDamage(boolean ground) {
		return damage;
	}

	@Override
	public int getRange(boolean ground) {
		return range;
	}

	@Override
	public int getShootSpeed(boolean ground) {
		return shootSpeed;
	}

	@Override
	public int getSplashDamage(boolean ground) {
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
	public Bullet shoot(Unit[] farestUnit) {
		if (farestUnit[0] == null) {
			if (farestUnit[1] != null) {
				return new Bullet(this, farestUnit[1]);
			} else {
				return null;
			}
		} else if (farestUnit[1] == null) {
			return new Bullet(this, farestUnit[0]);
		} else if (Math.abs(farestUnit[1].getPosition().x - getPosition().x) < Math
				.abs(farestUnit[0].getPosition().x - getPosition().x)) {
			return new Bullet(this, farestUnit[1]);
		} else {
			return new Bullet(this, farestUnit[0]);
		}
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
	public boolean hasInRange(Unit[] units) {
		if (units[0] != null && canAttackGround()) {
			if (Math.abs(getPosition().x - units[0].getPosition().x) - getRange(true) <= 0) {
				return true;
			}
		}
		if (units[01] != null && canAttackAir()) {
			if (Math.abs(getPosition().x - units[0].getPosition().x) - getRange(false) <= 0) {
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

	@Override
	public boolean canAttackGround() {
		return true;
	}

	@Override
	public boolean canAttackAir() {
		return true;
	}

}
