package de.szut.server.world;

public class Bullet {

	private Unit attacker;
	private Attackable target;

	public Bullet(Unit attacker, Attackable target) {
		this.attacker = attacker;
		this.target = target;
	}

	public boolean move() {
		return false;
	}

	public int getDamage() {
		return attacker.getDamage(target.isFlying());
	}

	public Attackable getTarget() {
		return target;
	}
}
