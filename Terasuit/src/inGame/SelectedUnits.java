package inGame;

import grafig.Panel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SelectedUnits {
	
	ActionButton actionBtn = new ActionButton();

	public void getUnit(ArrayList<Unit> entity, MouseEvent objUnit, Panel panel){
		for (int i = 0; i < entity.size(); i++) {
			if (entity.get(i).getLabel() == objUnit.getSource()){
				actionBtn.Entity(i, panel, entity);
			}
		}
	}
	
	public void getGroupOfUnits(ArrayList<Unit> entity, int x, int y, int h, int w){
		System.out.println("#####");
		for (int i = 0; i < entity.size(); i++) {
			if (entity.get(i).getEntityPositionX() > x && entity.get(i).getEntityPositionY() > y){
				if (entity.get(i).getEntityPositionX() < (x+h) && entity.get(i).getEntityPositionY() < (y+w)){
					System.out.println(entity.get(i).getEntityNummer());
				}
			}
		}
	}
}
