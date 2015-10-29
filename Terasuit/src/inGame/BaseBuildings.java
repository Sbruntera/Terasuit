package inGame;

import grafig.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BaseBuildings {
	
	int default_position_X = 200;
	int default_position_Y = 200;
	int default_interval = 50;
	ArrayList<Buildings> BuildingsEntity = new ArrayList<Buildings>();
	Buildings building = new Buildings();
	JLabel label = new JLabel("");
	
	public void createEntity(Panel panel, String Entitytype){
		building = new Buildings();
		
		ImageIcon pic = new ImageIcon(Entitytype);
		label = new JLabel("");
		label.setIcon(pic);
		label.setBounds(300, 200, pic.getIconWidth(), pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent objUnit) {
				System.out.println("Ein gebüuder wurde fokusiert1");
			}
		});
		building.setLabel(label);
		BuildingsEntity.add(building);
		panel.add(label);
		panel.repaint();
		return;
	}
}
