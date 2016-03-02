package de.szut.client.logic;

/**
 * Aus den Arraylisten, der UnitData.java werden UnitDefaultWerte-Objekte erzeugt
 * @author Sbrun
 */
public class UnitObject {
	
	String description = "";
	int live = 0;
	int speed = 0;
	int dmg = 0;
	int rpm = 0;
	int range = 0;
	int splashDamage = 0;
	int GroundGround = 0;
	int GroundAir = 0;
	int AirAir = 0;
	private int bulletSpeed;
	private int[] price;
	private boolean ground;
	private boolean air;
	
	public UnitObject(String description, int money, int human,
			int electricity, int monarchy, int live, int speed, int dmg, int rpm, int range,
			int splashDamage, int bulletSpeed, boolean ground, boolean air) {
		this.description = description;
		this.price = new int[] {money, electricity, human, monarchy};
		this.live = live;
		this.speed = speed;
		this.dmg = dmg;
		this.rpm = rpm;
		this.range = range;
		this.splashDamage = splashDamage;
		this.bulletSpeed = bulletSpeed;
		this.ground = ground;
		this.air = air;
	}
	
	public String getDescription() {
		return description;
	}

	public int getLive() {
		return live;
	}

	public void setLive(int live) {
		this.live = live;
	}

	public int getSpeed() {
		return speed;
	}

	public int getDmg() {
		return dmg;
	}

	public int getRpm() {
		return rpm;
	}

	public int getSplashDamage() {
		return splashDamage;
	}

	public int getGroundGround() {
		return GroundGround;
	}

	public int getGroundAir() {
		return GroundAir;
	}

	public int getAirAir() {
		return AirAir;
	}
	
	public int getRange() {
		return range;
	}
	
	public void setBulletSpeed() {
		
	}

	public int getBulletSpeed() {
		return bulletSpeed;
	}

	public int[] getPrice() {
		return price;
	}

	public boolean canAttackGround() {
		return ground;
	}

	public boolean canAttackAir() {
		return air;
	}
}
