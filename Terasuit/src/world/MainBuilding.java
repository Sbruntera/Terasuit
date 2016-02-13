package world;

import java.awt.Point;

public class MainBuilding implements Building, Unit {

	private int health = 5000;

	private int damage;
	private int range;
	private int cooldown;

	private byte position = 4;
	private byte player;


	public MainBuilding(byte player) {
		this.player = player;
	}

	@Override
	public void dealDamage(int value) {
		health -= value;
	}

	public int getHealth() {
		return health;
	}

	@Override
	public void upgrade() { //Main Building can not be upgraded
	}

	@Override
	public boolean hasUpgrade() {
		return false;
	}

	public int getDamage() {
		return damage;
	}

	public int getRange() {
		return range;
	}

	public boolean canShoot() {
		return cooldown <= 0;
	}

	public boolean coolDown() {
		if (cooldown > 0) {
			cooldown -= 1;
		}
		return cooldown <= 0;
	}

	@Override
	public byte getSlotID() {
		return position;
	}

	@Override
	public void build() { // MainBuilding can not be Builded
	}

	@Override
	public byte getPlayer() {
		return player;
	}

	@Override
	public void heal(int value) { // MainBuilding can not be healed
	}

	@Override
	public int getShootSpeed() { // MainBuilding can not shoot
		return 0;
	}

	@Override
	public void move() {  // MainBuilding can not move
	}

	@Override
	public void setDirection(int direction, boolean running) { // MainBuilding can not move
	}

	@Override
	public boolean isRunning() { // MainBuilding can not run
		return false;
	}

	@Override
	public Bullet shoot(Unit farestUnits) { // MainBuilding can not shoot
		return null;
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public short getID() {
		return WorldConstants.MAINBUILDINGID;
	}

	@Override
	public boolean hasInRange(Unit unit) { // MainBuilding can not shoot
		return false;
	}

	@Override
	public boolean createUnit(byte typeID, short unitID, short position) { //MainBuilding can not create Units
		return false;
	}

	@Override
	public Point getPosition() {
		return null;
	}

	@Override
	public byte getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Unit create() {
		return null;
	}

	@Override
	public byte getUnitType() {
		return 0;
	}

	@Override
	public short getUnitID() {
		return 0;
	}

	@Override
	public int getSplashDamage() { // Can not Attack
		return 0;
	}

	@Override
	public boolean isFlying() {
		return false;
	}
}
