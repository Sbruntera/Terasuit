package de.szut.client.ingame;

import javax.swing.JLabel;

public class Unit implements Attackable{
	
	JLabel label;
	int EntityNummer = 0;
	double EntityPositionX = 0;
	double EntityPositionY = 0;
	int EntitySpeed = 0;
	int Entitymembership = 0;
	int EntityFirerange = 0;
	int EntityFirepower = 0;
	int EntitySplashDmg = 0;
	int EntitySpawntimer = 0;
	private int weaponCooldown = 0;
	int EntityLive = 0;
	String Entityname = "";
	boolean flyingEntity = false;
	boolean newEntity = false;
	boolean EntityMove = false;
	boolean EntityRun = false;
	boolean EntityRushLeft = true;
	boolean EntityFire = false;
	boolean Entitymarked = false;
	private boolean canAttackGround = true;
	private boolean canAttackAir = true;
	private int cooldown;
	private int[] price;
	private int bulletSpeed;
	
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

	public boolean isEntityRunning() {
		return EntityRun;
	}

	public void setEntityRun(boolean entityRunning) {
		EntityRun = entityRunning;
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

	public double getEntityPositionX() {
		return EntityPositionX;
	}

	public void setEntityPositionX(double entityPosition) {
		EntityPositionX = entityPosition;
		if (label != null) {
			label.setLocation((int) (EntityPositionX - label.getIcon().getIconWidth()/2), (int) (EntityPositionY - label.getIcon().getIconHeight()/2));
		}
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

	public void setLabel(JLabel label) {
		this.label = label;
	}
	
	public double getEntityPositionY() {
		return EntityPositionY;
	}

	public void setEntityPositionY(int entityPositionY) {
		EntityPositionY = entityPositionY;
	}
	
	public int getEntitySpawntimer() {
		return EntitySpawntimer;
	}

	public void setEntitySpawntimer(int entitySpawntimer) {
		EntitySpawntimer = entitySpawntimer;
	}

	public void setEntitySpeed(int entitySpeed) {
		this.EntitySpeed = entitySpeed;
	}

	public int getEntitySpeed() {
		return EntitySpeed;
	}
	
	public boolean canAttackGround() {
		return canAttackGround;
	}
	
	public void setCanAttackGround(boolean canAttackGround) {
		this.canAttackGround = canAttackGround;
	}
	
	public boolean canAttackAir() {
		return canAttackAir;
	}
	
	public void setCanAttackAir(boolean canAttackAir) {
		this.canAttackAir = canAttackAir;
	}

	public boolean hasInRange(Attackable[] attackables) {
		if (attackables[0] != null && canAttackGround) {
			if (Math.abs(getEntityPositionX() - attackables[0].getEntityPositionX()) - getEntityFirerange() <= 0) {
				return true;
			}
		}
		if (attackables[01] != null && canAttackAir) {
			if (Math.abs(getEntityPositionX() - attackables[0].getEntityPositionX()) - getEntityFirerange() <= 0) {
				return true;
			}
		}
		return false;
	}

	public Bullet shoot(Attackable[] target) {
		if (canAttackGround() && getCooldown(false)) {
			cooldown = getWeaponCooldown(false);
			return new Bullet(this, target[0]);
		} else if (canAttackAir() && getCooldown(true)) {
			cooldown = getWeaponCooldown(false);
			return new Bullet(this, target[1]);
		} else {
			cooldown--;
		}
		return null;
	}

	public int getWeaponCooldown(boolean b) {
		return weaponCooldown;
	}

	public void setWeaponCooldown(int cooldown) {
		this.weaponCooldown = cooldown;
	}

	private boolean getCooldown(boolean b) {
		return cooldown <= 0;
	}

	@Override
	public boolean isAlive() {
		return EntityLive > 0;
	}

	@Override
	public void dealDamage(int value) {
		EntityLive -= value;
	}

	@Override
	public void heal(int value) {
		EntityLive += value;
	}
	
	public void setBulletSpeed(int bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	public int getBulletSpeed() {
		return bulletSpeed;
	}
	
	public void setPrice(int[] price) {
		this.price = price;
	}

	public int[] getPrice() {
		return price;
	}

	public void setEntityReloadTimer(int rpm) {
		weaponCooldown = rpm;
	}
}
