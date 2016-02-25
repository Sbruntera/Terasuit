package de.szut.server.world;

public class Bullet {

	private Attackable target;

	public Bullet(Attackable marine, Attackable target) {
		this.target = target;
	}

	public boolean move() {
		return false;
	}

	public int getDamage() {
		return 0;
	}

	public Attackable getTarget() {
		return target;
	}
}
