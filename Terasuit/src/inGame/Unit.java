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
	String Entityname = "";
	boolean flyingEntity = false;
	boolean newEntity = false;
	boolean EntityMove = false;
	boolean EntityRushLeft = true;
	boolean EntityFire = false;
	boolean Entitymarked = false;
	
	public boolean isFlyingEntity() {
		return flyingEntity;
	}

	public void setFlyingEntity(boolean flyingEntity) {
		this.flyingEntity = flyingEntity;
	}

	
	public boolean isEntityMove() {
		return EntityMove;
	}

	public void setEntityMove(boolean entityMove) {
		EntityMove = entityMove;
	}

	public boolean isEntityRushLeft() {
		return EntityRushLeft;
	}

	public void setEntityRushLeft(boolean entityRushLeft) {
		EntityRushLeft = entityRushLeft;
	}

	public String getEntityname() {
		return Entityname;
	}

	public void setEntityname(String entityname) {
		Entityname = entityname;
	}

	public boolean isNewEntity() {
		return newEntity;
	}

	public void setNewEntity(boolean newEntity) {
		this.newEntity = newEntity;
	}
	
	public boolean isEntitymarked() {
		return Entitymarked;
	}

	public void setEntitymarked(boolean entitymarked) {
		Entitymarked = entitymarked;
	}

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
