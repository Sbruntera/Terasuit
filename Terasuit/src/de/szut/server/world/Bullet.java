package de.szut.server.world;

import java.awt.Point;

public class Bullet {

	private Point position;
	private Unit attacker;
	private Attackable target;

	public Bullet(Unit attacker, Attackable target) {
		this.attacker = attacker;
		this.target = target;
		position = new Point((int) attacker.getXPosition(), (int) attacker.getYPosition());
	}

	public boolean move() {;
		double v = attacker.getBulletSpeed(target.isFlying())
				/ Math.sqrt(Math.pow(target.getXPosition() - position.getX(), 2)
						+ Math.pow(target.getYPosition() - position.getY(), 2));
		position.x += (target.getXPosition() - position.getX()) * v;
		position.y += (target.getYPosition() - position.getY()) * v;
		return Math.abs(v) > 1;
	}

	public int getDamage() {
		return attacker.getDamage(target.isFlying());
	}

	public int getSplashDamage() {
		return attacker.getSplashDamage(target.isFlying());
	}

	public int getPlayer() {
		return attacker.getPlayer();
	}

	public Attackable getTarget() {
		return target;
	}
}
