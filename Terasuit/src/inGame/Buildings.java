package inGame;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Buildings {
	
	int building_Live = 0;
	int type = 0;
	int number = 0;
	String description = "";
	String name = "";
	String[] spwanableEntity;
	

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
	boolean attackabel = false;
	JLabel label = new JLabel("");
	
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


}
