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

	@Override
	public int getDamage(boolean ground) {
		return damage;
	}

	@Override
	public int getRange(boolean ground) {
		return range;
	}

	@Override
	public byte getSlotID() {
		return position;
	}

	@Override
	public boolean build() { // MainBuilding can not be Builded
		return false;
	}

	@Override
	public byte getPlayer() {
		return player;
	}

	@Override
	public void heal(int value) { // MainBuilding can not be healed
	}

	@Override
	public int getShootSpeed(boolean ground) { // MainBuilding can not shoot
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
	public Bullet shoot(Unit[] farestUnits) { // MainBuilding can not shoot
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
	public boolean hasInRange(Unit[] units) { // MainBuilding can not shoot
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
		return 0;
	}

	@Override
	public Unit create() {
		return null;
	}

	@Override
	public int getSplashDamage(boolean ground) { // Can not Attack
		return 0;
	}

	@Override
	public boolean isFlying() {
		return false;
	}

	@Override
	public boolean canAttackGround() {
		return false;
	}

	@Override
	public boolean canAttackAir() {
		return false;
	}

	@Override
	public byte getUpgrade() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFinished() {
		return true;
	}
}
