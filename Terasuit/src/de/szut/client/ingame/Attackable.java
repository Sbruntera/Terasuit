package de.szut.client.ingame;

/**
 * 
 * @author Simeon
 *
 */
public interface Attackable {

	/**
	 * Gibt zurück ob das Objekt fliegt
	 * 
	 * @return
	 */
	boolean isFlyingEntity();

	/**
	 * Gibt die X-Position des Objektes zurück
	 * 
	 * @return
	 */
	double getEntityPositionX();

	/**
	 * Gibt die Y-Position des Objektes zurück
	 * 
	 * @return
	 */
	double getEntityPositionY();

	/**
	 * Gibt das Leben des Objektes zurück
	 * 
	 * @return
	 */
	int getEntityLive();

	/**
	 * Gibt zurück ob das Objekt lebt
	 * 
	 * @return
	 */
	boolean isAlive();

	/**
	 * Verletzt das Objekt um einen übergebenen Wert
	 * 
	 * @param value
	 *            Schaden der gemacht werden soll
	 */
	void dealDamage(int value);

	/**
	 * Heilt das Objekt um einen bestimmten Wert
	 * 
	 * @param value
	 *            Leben um das geheilt werden soll
	 */
	void heal(int value);

}
