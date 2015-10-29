package inGame;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SelectedUnits {

	public void getUnit(ArrayList<Unit> entity, MouseEvent objUnit){
		for (int i = 0; i < entity.size(); i++) {
			if (entity.get(i).getLabel() == objUnit.getSource()){
				System.out.println(entity.get(i).getEntityNummer());
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
