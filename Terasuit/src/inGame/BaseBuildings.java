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
	public int default_position_Leftside_y = 380;
	public int default_position_Rightside_x = 1350;
	public int default_position_Rightside_y = 380;
	int default_ender = 200;
	int default_interval = 45;
	ArrayList<Buildings> BuildingsEntity = new ArrayList<Buildings>();
	Buildings building = new Buildings();
	JLabel label = new JLabel("");
	ActionButton btnAction = new ActionButton();
	
	
	public void buildBase(Panel field, Panel console, Loader load, Funktions func, String FirstColor, String SecColor, int default_position_X, int default_position_Y){
		createEntity(field, console, load, func, barracks, 1, default_position_X, default_position_Y);
		// Rot
		createEntity(field, console, load, func, FirstColor, 0, default_position_X, default_position_Y);
		createEntity(field, console, load, func, FirstColor, 0, default_position_X+default_interval, default_position_Y+default_interval);
		createEntity(field, console, load, func, FirstColor, 0, default_position_X+default_interval*2, default_position_Y+default_interval*2);
		createEntity(field, console, load, func, FirstColor, 0, default_position_X+default_interval*3, default_position_Y+default_interval);
		// Blau
		createEntity(field, console, load, func, SecColor, 0, default_position_X+default_interval, default_position_Y-default_interval);
		createEntity(field, console, load, func, SecColor, 0, default_position_X+default_interval*2, default_position_Y-default_interval*2);
		createEntity(field, console, load, func, SecColor, 0, default_position_X+default_interval*3, default_position_Y-default_interval);
		createEntity(field, console, load, func, SecColor, 0, default_position_X+default_interval*4, default_position_Y);
		// MAIN_BASE
		createEntity(field, console, load, func, base, -1,  default_position_X+default_interval*2, default_position_Y);
	}
	
	public void createEntity(Panel field, Panel console, Loader loader, Funktions func, String Entitytype, int EntityNumber,  int  X, int Y){
		building = new Buildings();
		
		ImageIcon pic = new ImageIcon(Entitytype);
		label = new JLabel("");
		label.setIcon(pic);
		label.setBounds(X, Y, pic.getIconWidth(), pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent objUnit) {
				for (int i = 0; i < BuildingsEntity.size(); i++) {
					if (BuildingsEntity.get(i).getLabel() == objUnit.getSource()){
						btnAction.createUserOptions(console, field, BuildingsEntity, i, loader, func);
					}
				}
			}
		});
		building.setLabel(label);
		building.setDescription("Ich bin ein ganz wichtiges Gebäude!");
		building.setName("Barracks");
		building.setSpwanableEntity(searchForPossibleEntitys(EntityNumber));
		BuildingsEntity.add(building);
		field.add(label);
		field.repaint();
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
		
		// Recruit
		switch (BuildingType){ 
		case 0:
			return new String[]{"Outpost", "Forge", "Hospital", "Bank", "Armory", "Generator", "Special Operations"};
		case 1:
			return new String[]{"Marine", "Chronite Tank", "Scout", "Barracks", "Destroy"};
		case 2:
			return new String[]{"Marine", "Chronite Tank", "Sniper", "Gröditz", "Recruit", "Arsenal", "Destroy"};
		case 3:
			return new String[]{"Marine", "Chronite Tank", "Gröditz", "Hover Tank", "Black Queen", "Recruit", "Destroy"};
		case 4:
			return new String[]{"A25-Roman", "Scout", "Salvage", "Destroy"};
		case 5:
			return new String[]{"A25-Roman", "Scout", "Phantom", "Sakata-MK2", "Salvage", "Destroy"};
		case 6:
			return new String[]{"A25-Roman", "Scout", "Phantom", "Sakata-MK2", "Sakata Spider", "Gladiator", "Salvage", "Destroy"};
		case 7:
			return new String[]{"Financel Support", "Reinforcments", "Reserve Energy", "Modified Phantom", "Destroy"};
		case 8:
			return new String[]{"Power", "Solar Grid", "Destroy"};
		case 9:
			return new String[]{"Power", "Modified Sakata", "Destroy"};
		case 10:
			return new String[]{"Exhange", "Treasury", "Destroy"};
		case 11:
			return new String[]{"Traiding", "Destroy"};
		case 12:
			return new String[]{"Resuscitate", "Meditec", "Rescue Team", "War Sanctum", "Destroy"};
		case 13:
			return new String[]{"Recover", "Meditec", "Rescue Team", "Saint", "Sphinx", "Destroy"};
		case 14:
			return new String[]{"Launch Missile Green", "Launch Missile Blue", "Black Operations", "Special Froces", "Far Sniper", "A27-Pride", "Destroy"};
		default:
			return new String[]{};
		}
	}
}
