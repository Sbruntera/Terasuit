package de.szut.client.ingame;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;

/**
 * 
 * @author Simeon
 *
 */
public class Bullet {

	private Point position;
	private Unit attacker;
	private Attackable target;
	private JLabel bullet;

	/**
	 * Initialisiert eine Kugel mit Herkunft und Ziel
	 * 
	 * @param attacker
	 *            Herkunft der Kugel
	 * @param target
	 *            Ziel der Kugel
	 */
	public Bullet(Unit attacker, Attackable target) {
		this.attacker = attacker;
		this.target = target;
		bullet = new JLabel();
		bullet.setSize(2, 2);
		bullet.setBackground(Color.RED);
		bullet.setOpaque(true);
		position = new Point((int) attacker.getEntityPositionX(),
				(int) attacker.getEntityPositionY());
		bullet.setLocation(position);
	}

	/**
	 * Bewegt die Kugel
	 * 
	 * @return
	 */
	public boolean move() {
		double v = attacker.getBulletSpeed()
				/ Math.sqrt(Math.pow(
						target.getEntityPositionX() - position.getX(), 2)
						+ Math.pow(
								target.getEntityPositionY() - position.getY(),
								2));
		position.x += (target.getEntityPositionX() - position.getX()) * v;
		position.y += (target.getEntityPositionY() - position.getY()) * v;
		bullet.setLocation(position);
		return Math.abs(v) > 1;
	}

	/**
	 * Gibt das Label der Kugel zurück
	 * 
	 * @return
	 */
	public JLabel getLabel() {
		return bullet;
	}

	/**
	 * Gibt den Schaden der Kugel zurück
	 * 
	 * @return
	 */
	public int getDamage() {
		return attacker.getEntityFirepower();
	}

	/**
	 * Gibt den Gebietsschaden des Gebäudes zurück
	 * 
	 * @return
	 */
	public int getSplashDamage() {
		return attacker.getEntitySplashDmg();
	}

	/**
	 * Gibt das Ziel der Kugel zurück
	 * 
	 * @return
	 */
	public Attackable getTarget() {
		return target;
	}
}
