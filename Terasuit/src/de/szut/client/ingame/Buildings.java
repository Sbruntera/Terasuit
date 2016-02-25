package de.szut.client.ingame;

import javax.swing.JLabel;

public class Buildings {
	
	int building_Live = 0;
	int type = 0;
	int number = 0;
	int slotID = 0;
	int x = 0;
	int y = 0;
	boolean attackabel = false;
	JLabel label = new JLabel("");
	String description = "";
	String name = "";
	String[] spwanableEntity;
	Buildings primerBuilding = null;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Buildings getPrimerBuilding() {
		return primerBuilding;
	}
	public void setPrimerBuilding(Buildings primerBuilding) {
		this.primerBuilding = primerBuilding;
	}
	public String[] getSpwanableEntity() {
		return spwanableEntity;
	}
	public void setSpwanableEntity(String[] spwanableEntity) {
		this.spwanableEntity = spwanableEntity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	public int getBuilding_Live() {
		return building_Live;
	}
	public void setBuilding_Live(int building_Live) {
		this.building_Live = building_Live;
	}
	public boolean isAttackabel() {
		return attackabel;
	}
	public void setAttackabel(boolean attackabel) {
		this.attackabel = attackabel;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSlotID() {
		return slotID;
	}
	public void setSlotID(int slotID) {
		this.slotID = slotID;
	}
}
