package inGame;

import grafig.Loader;
import grafig.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BaseBuildings {
	
	
	public String red = "Buildings/base_red.png";
	public String blue = "Buildings/base_blue.png";
	public String grun = "Buildings/base_grün.png";
	public String gelb = "Buildings/base_gelb.png";
	public String base = "Buildings/base.png";
	public String barracks = "Buildings/barracks.png";
	public int default_position_Leftside_x = 40;
	public int default_position_Leftside_y = 400;
	public int default_position_Rightside_x = 900;
	public int default_position_Rightside_y = 400;
	int default_ender = 200;
	int default_interval = 45;
	ArrayList<Buildings> BuildingsEntity = new ArrayList<Buildings>();
	Buildings building = new Buildings();
	JLabel label = new JLabel("");
	ActionButton btnAction = new ActionButton();
	
	
	public void buildBase(Panel panel, Loader load, Funktions func, String FirstColor, String SecColor, int default_position_X, int default_position_Y){
		createEntity(panel, load, func, barracks, default_position_X, default_position_Y);
		// Rot
		createEntity(panel, load, func, FirstColor, default_position_X, default_position_Y);
		createEntity(panel, load, func, FirstColor, default_position_X+default_interval, default_position_Y+default_interval);
		createEntity(panel, load, func, FirstColor, default_position_X+default_interval*2, default_position_Y+default_interval*2);
		createEntity(panel, load, func, FirstColor, default_position_X+default_interval*3, default_position_Y+default_interval);
		// Blau
		createEntity(panel, load, func, SecColor, default_position_X+default_interval, default_position_Y-default_interval);
		createEntity(panel, load, func, SecColor, default_position_X+default_interval*2, default_position_Y-default_interval*2);
		createEntity(panel, load, func, SecColor, default_position_X+default_interval*3, default_position_Y-default_interval);
		createEntity(panel, load, func, SecColor, default_position_X+default_interval*4, default_position_Y);
		// MAIN_BASE
		createEntity(panel, load, func, base, default_position_X+default_interval*2, default_position_Y);
	}
	
	public void createEntity(Panel panel, Loader loader, Funktions func, String Entitytype, int  X, int Y){
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
		building.setDescription("Ich bin ein ganz wichtiges Gebäude!");
		building.setName("Barracks");
		building.setSpwanableEntity(searchForPossibleEntitys());
		BuildingsEntity.add(building);
		panel.add(label);
		panel.repaint();
		return;
	}
	
	private String[] searchForPossibleEntitys(int BuildingType){
		
		// 0 Emty Field
		// 1-3 Kasserne
		// 4-6 Forge
		// 7 Armory
		// 8-9 Energy Gebäude
		// 10-11 Geld Gebäude
		// 12-13 Hospital
		// 14 Rocketped
		
		switch (BuildingType) {
		case 0:
			return new String[]{"Outpost", "Forge", "Hospital", "Bank", "Armory", "Generator", "Special Operations"};
			break;
		case 1:
			return new String[]{"Marine", "Chronite Tank", "Recruit", "Barracks", "Destroy"};
			break;
		case 2:
			return new String[]{"Marine", "Chronite Tank", "Sniper", "Gröditz", "Recruit", "Arsenal", "Destroy"};
			break;
		case 3:
			return new String[]{"Marine", "Chronite Tank", "Gröditz", "Hover Tank", "Black Queen", "Recruit", "Destroy"};
			break;
		case 4:
			return new String[]{"A25-Roman", "Scout", "Salvage", "Destroy"};
			break;
		case 5:
			return new String[]{"A25-Roman", "Scout", "Phantom", "Sakata-MK2", "Salvage", "Destroy"};
			break;
		case 6:
			return new String[]{"A25-Roman", "Scout", "Phantom", "Sakata-MK2", "Sakata Spider", "Gladiator", "Salvage", "Destroy"};
			break;
		case 7:
			return new String[]{"Financel Support", "Reinforcments", "Reserve Energy", "Modified Phantom", "Destroy"};
			break;
		case 8:
			return new String[]{"Power", "Solar Grid", "Destroy"};
			break;
		case 9:
			return new String[]{"Power", "Modified Sakata", "Destroy"};
			break;
		case 10:
			return new String[]{"Exhange", "Treasury", "Destroy"};
			break;
		case 11:
			return new String[]{"Traiding", "Destroy"};
			break;
		case 12:
			return new String[]{"Resuscitate", "Meditec", "Rescue Team", "War Sanctum", "Destroy"};
			break;
		case 13:
			return new String[]{"Recover", "Meditec", "Rescue Team", "Saint", "Sphinx", "Destroy"};
			break;
		case 14:
			return new String[]{"Launch Missile Green", "Launch Missile Blue", "Black Operations", "Special Froces", "Far Sniper", "A27-Pride", "Destroy"};
			break;

	}
}
