package de.szut.client.ingame;

import java.awt.Point;

public class Bullet {

	private Point position;
	private Unit attacker;
	private Attackable target;

	public Bullet(Unit attacker, Attackable target) {
		this.attacker = attacker;
		this.target = target;
		position = new Point(attacker.getEntityPositionX(),
				attacker.getEntityPositionY());
	}

	public boolean move() {
		double v = attacker.getBulletSpeed()
				/ Math.sqrt(Math.pow(target.getEntityPositionX() - position.x,
						2)
						+ Math.pow(target.getEntityPositionY() - position.y, 2));
		position.x += (target.getEntityPositionX() - position.x) * v;
		position.y += (target.getEntityPositionY() - position.y) * v;
		return Math.abs(v) > 1;
	}

	public int getDamage() {
		return attacker.getEntityFirepower();
	}

	public int getSplashDamage() {
		return attacker.getEntitySplashDmg();
	}

	public Attackable getTarget() {
		return target;
	}
}
