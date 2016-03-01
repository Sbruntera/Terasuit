package de.szut.server.world;

import java.awt.geom.Point2D;

public class Bullet {

	private Point2D position;
	private Unit attacker;
	private Attackable target;

	public Bullet(Unit attacker, Attackable target) {
		this.attacker = attacker;
		this.target = target;
		position = attacker.getPosition();
	}

	public boolean move() {;
		double v = attacker.getBulletSpeed(target.isFlying())
				/ Math.sqrt(Math.pow(target.getPosition().getX() - position.getX(), 2)
						+ Math.pow(target.getPosition().getY() - position.getY(), 2));
		position.setLocation((target.getPosition().getX() - position.getX()) * v, (target.getPosition().getY() - position.getY()) * v);
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
