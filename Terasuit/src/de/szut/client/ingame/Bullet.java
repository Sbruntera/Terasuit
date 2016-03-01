package de.szut.client.ingame;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;

public class Bullet {

	private Point position;
	private Unit attacker;
	private Attackable target;
	private JLabel bullet;

	public Bullet(Unit attacker, Attackable target) {
		this.attacker = attacker;
        this.target = target;
        bullet = new JLabel();
        bullet.setSize(10, 10);
        bullet.setBackground(Color.RED);
        bullet.setOpaque(true);
        position = new Point((int) attacker.getEntityPositionX(),
                (int) attacker.getEntityPositionY());
        bullet.setLocation(position);
	}

	public boolean move() {
		double v = attacker.getBulletSpeed()
				/ Math.sqrt(Math.pow(target.getEntityPositionX() - position.x,
						2)
						+ Math.pow(target.getEntityPositionY() - position.y, 2));
		position.x += (target.getEntityPositionX() - position.x) * v;
		position.y += (target.getEntityPositionY() - position.y) * v;
		bullet.setLocation(position);
		return Math.abs(v) > 1;
	}
	
	public JLabel getLabel(){
        return bullet;
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
