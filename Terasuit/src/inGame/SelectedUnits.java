package inGame;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class SelectedUnits {
	
	public ArrayList<Integer> getUnit(HashMap<Integer, Unit> hashMap, ArrayList<Integer> selectedEntitysID, MouseEvent objUnit){
		for (Unit u : hashMap.values()) {
			if (u.getLabel() == objUnit.getSource()){
				selectedEntitysID.add(u.getEntityNummer());
				u.setEntitymarked(true);
				return selectedEntitysID;
			}
		}
		return selectedEntitysID;
	}
	
	public ArrayList<Integer> getGroupOfUnits(HashMap<Integer, Unit> hashMap, ArrayList<Integer> selectedEntitysID, int x, int y, int h, int w){
		for (Unit u : hashMap.values()) {
			if (u.getEntityPositionX() > x && u.getEntityPositionY() > y){
				if (u.getEntityPositionX() < (x+h) && u.getEntityPositionY() < (y+w)){
					selectedEntitysID.add(u.getEntityNummer());
					u.setEntitymarked(true);
				}
			}
		}
		return selectedEntitysID;
	}
}
