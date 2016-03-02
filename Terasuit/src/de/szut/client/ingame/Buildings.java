package de.szut.client.ingame;

import javax.swing.JLabel;
/**
 * Hier draus werden Objekte für Gebäude erzeugt.
 * Alle Gebäude besitzen die unten angegebenen Wert
 * @author Sbrun
 */
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
	private int[] price;
	
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
	public void setPrice(int[] price) {
		this.price = price;
	}
	public int[] getPrice() {
		return price;
	}
}
