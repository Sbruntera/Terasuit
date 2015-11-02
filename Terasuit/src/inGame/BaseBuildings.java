package inGame;

import grafig.Loader;
import grafig.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BaseBuildings {
	
	
	String red = "Buildings/base_red.png";
	String blue = "Buildings/base_blue.png";
	String base = "Buildings/base.png";
	String barracks = "Buildings/barracks.png";
	int default_position_X = 40;
	int default_position_Y = 400;
	int default_ender = 200;
	int default_interval = 45;
	ArrayList<Buildings> BuildingsEntity = new ArrayList<Buildings>();
	Buildings building = new Buildings();
	JLabel label = new JLabel("");
	ActionButton btnAction = new ActionButton();
	
	
	public void buildBase(Panel panel, Loader load, Funktions func){
		createEntity(panel, load, func, barracks, default_position_X, default_position_Y);
		// Rot
		createEntity(panel, load, func, red, default_position_X, default_position_Y);
		createEntity(panel, load, func, red, default_position_X+default_interval, default_position_Y+default_interval);
		createEntity(panel, load, func, red, default_position_X+default_interval*2, default_position_Y+default_interval*2);
		createEntity(panel, load, func, red, default_position_X+default_interval*3, default_position_Y+default_interval);
		// Blau
		createEntity(panel, load, func, blue, default_position_X+default_interval, default_position_Y-default_interval);
		createEntity(panel, load, func, blue, default_position_X+default_interval*2, default_position_Y-default_interval*2);
		createEntity(panel, load, func, blue, default_position_X+default_interval*3, default_position_Y-default_interval);
		createEntity(panel, load, func, blue, default_position_X+default_interval*4, default_position_Y);
		// MAIN_BASE
		createEntity(panel, load, func, base, default_position_X+default_interval*2, default_position_Y);
	}
	
	public void createEntity(Panel panel, Loader loader, Funktions func, String Entitytype, int X, int Y){
		building = new Buildings();
		
		ImageIcon pic = new ImageIcon(Entitytype);
		label = new JLabel("");
		label.setIcon(pic);
		label.setBounds(X, Y, pic.getIconWidth(), pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent objUnit) {
				for (int i = 0; i < BuildingsEntity.size(); i++) {
					if (BuildingsEntity.get(i).getLabel() == objUnit.getSource()){
						btnAction.Building(panel, BuildingsEntity, i, loader, func);
					}
				}
			}
		});
		building.setLabel(label);
		BuildingsEntity.add(building);
		panel.add(label);
		panel.repaint();
		return;
	}
}
