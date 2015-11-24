package inGame;

import javax.swing.JLabel;

public class Buildings {
	
	int building_Live = 0;
	int type = 0;
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
