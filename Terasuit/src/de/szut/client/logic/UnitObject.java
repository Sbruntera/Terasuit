package de.szut.client.logic;

public class UnitObject {
	
	String description = "";
	int money = 0;
	int electricity = 0;
	int human = 0;
	int monarchy = 0;
	int live = 0;
	int speed = 0;
	int dmg = 0;
	int rpm = 0;
	int range = 0;
	int splashDamage = 0;
	int GroundGround = 0;
	int GroundAir = 0;
	int AirAir = 0;
	
	public UnitObject(String description, int money, int electricity,
			int human, int monarchy, int live, int speed, int dmg, int rpm, int range,
			int splashDamage, int groundGround, int groundAir, int airAir) {
		this.description = description;
		this.money = money;
		this.electricity = electricity;
		this.human = human;
		this.monarchy = monarchy;
		this.live = live;
		this.speed = speed;
		this.dmg = dmg;
		this.rpm = rpm;
		this.range = range;
		this.splashDamage = splashDamage;
		GroundGround = groundGround;
		GroundAir = groundAir;
		AirAir = airAir;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getElectricity() {
		return electricity;
	}

	public void setElectricity(int electricity) {
		this.electricity = electricity;
	}

	public int getHuman() {
		return human;
	}

	public void setHuman(int human) {
		this.human = human;
	}

	public int getMonarchy() {
		return monarchy;
	}

	public void setMonarchy(int monarchy) {
		this.monarchy = monarchy;
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

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public int getRpm() {
		return rpm;
	}

	public void setRpm(int rpm) {
		this.rpm = rpm;
	}

	public int getSplashDamage() {
		return splashDamage;
	}

	public void setSplashDamage(int splashDamage) {
		this.splashDamage = splashDamage;
	}

	public int getGroundGround() {
		return GroundGround;
	}

	public void setGroundGround(int groundGround) {
		GroundGround = groundGround;
	}

	public int getGroundAir() {
		return GroundAir;
	}

	public void setGroundAir(int groundAir) {
		GroundAir = groundAir;
	}

	public int getAirAir() {
		return AirAir;
	}

	public void setAirAir(int airAir) {
		AirAir = airAir;
	}
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}


}
