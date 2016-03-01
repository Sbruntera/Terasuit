package de.szut.server.world;

import java.awt.Point;

public class Bullet {

	private Point position;
	private Unit attacker;
	private Attackable target;

	public Bullet(Unit attacker, Attackable target) {
		this.attacker = attacker;
		this.target = target;
		position = attacker.getPosition();
	}

	public boolean move() {;
		double v = attacker.getBulletSpeed(target.isFlying())
				/ Math.sqrt(Math.pow(target.getPosition().x - position.x, 2)
						+ Math.pow(target.getPosition().y - position.y, 2));
		position.x += (target.getPosition().x - position.x) * v;
		position.y += (target.getPosition().y - position.y) * v;
		return Math.abs(v) > 1;
	}

	public int getDamage() {
		return attacker.getDamage(target.isFlying());
	}

	public int getSplashDamage() {
		return attacker.getSplashDamage(target.isFlying());
	}

	public Attackable getTarget() {
		return target;
	}
}
