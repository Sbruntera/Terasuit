package inGame;

import javax.swing.JLabel;

public class Unit {
	
	JLabel label = new JLabel("");
	int EntityNummer = 0;
	int EntityPositionX = 0;
	int EntityPositionY = 0;

	int Entitymembership = 0;
	int EntityFirerange = 0;
	int EntityFirepower = 0;
	int EntitySplashDmg = 0;
	int EntityLive = 0;
	boolean EntityFire = false;

	public JLabel getLabel() {
		return label;
	}

	public int getEntityNummer() {
		return EntityNummer;
	}

	public void setEntityNummer(int entityNummer) {
		EntityNummer = entityNummer;
	}

	public int getEntityPositionX() {
		return EntityPositionX;
	}

	public void setEntityPositionX(int entityPositionX) {
		EntityPositionX = entityPositionX;
	}

	public int getEntitymembership() {
		return Entitymembership;
	}

	public void setEntitymembership(int entitymembership) {
		Entitymembership = entitymembership;
	}

	public int getEntityFirerange() {
		return EntityFirerange;
	}

	public void setEntityFirerange(int entityFirerange) {
		EntityFirerange = entityFirerange;
	}

	public int getEntityFirepower() {
		return EntityFirepower;
	}

	public void setEntityFirepower(int entityFirepower) {
		EntityFirepower = entityFirepower;
	}

	public int getEntitySplashDmg() {
		return EntitySplashDmg;
	}

	public void setEntitySplashDmg(int entitySplashDmg) {
		EntitySplashDmg = entitySplashDmg;
	}

	public int getEntityLive() {
		return EntityLive;
	}

	public void setEntityLive(int entityLive) {
		EntityLive = entityLive;
	}

	public boolean isEntityFire() {
		return EntityFire;
	}

	public void setEntityFire(boolean entityFire) {
		EntityFire = entityFire;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}
	
	public int getEntityPositionY() {
		return EntityPositionY;
	}

	public void setEntityPositionY(int entityPositionY) {
		EntityPositionY = entityPositionY;
	}
}
