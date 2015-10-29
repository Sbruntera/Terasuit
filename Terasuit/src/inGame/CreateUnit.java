package inGame;

import grafig.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CreateUnit {
	
	Unit unit = new Unit();
	JLabel label = new JLabel("");
	SelectedUnits select = new SelectedUnits();
	
	public void createEntity(Panel panel, String Entitytype, ArrayList<Unit> entity){
		unit = new Unit();
		unit.setEntityNummer(entity.size()+1);
		
		int randValue1 = random(50);
		int randValue2 = random(200);
		
		unit.setEntityPositionX(300+randValue1);
		unit.setEntityPositionY(200+randValue2);
		
		ImageIcon pic = new ImageIcon(Entitytype);
		label = new JLabel("");
		label.setIcon(pic);
		label.setBounds(300 + (randValue1), 200 + (randValue2), pic.getIconWidth(), pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent objUnit) {
				select.getUnit(entity, objUnit);
			}
		});
		unit.setLabel(label);
		entity.add(unit);
		panel.add(label);
		panel.repaint();
		return;
	}
	
	public int random(int zahl){
		int rand = (int) (Math.random()*zahl)+1;
		return rand;
	}
	
}
