package de.szut.client.ingame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import de.szut.client.grafik.Loader;
import de.szut.client.grafik.Panel;

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
//	ArrayList<Buildings> buildingsEntity;
	Buildings building = new Buildings();
	JLabel label = new JLabel("");

	
	public void buildBase(Panel field, Game game, Buildings[] buildingsArray, Loader load, Funktions func, String FirstColor, String SecColor, int default_position_X, int default_position_Y, boolean leftSide){
		
		// Rot
		createEntity(field, load, func, game, buildingsArray, FirstColor, "Slot", default_position_X, default_position_Y, leftSide, 1);
		createEntity(field, load, func, game, buildingsArray, FirstColor, "Slot", default_position_X+default_interval, default_position_Y+default_interval, leftSide, 2);
		createEntity(field, load, func, game, buildingsArray, FirstColor, "Slot", default_position_X+default_interval*2, default_position_Y+default_interval*2, leftSide, 3);
		createEntity(field, load, func, game, buildingsArray, FirstColor, "Slot", default_position_X+default_interval*3, default_position_Y+default_interval, leftSide, 4);
		// Blau
		createEntity(field, load, func, game, buildingsArray, SecColor, "Slot", default_position_X+default_interval, default_position_Y-default_interval, leftSide, 5);
		createEntity(field, load, func, game, buildingsArray, SecColor, "Slot", default_position_X+default_interval*2, default_position_Y-default_interval*2, leftSide, 6);
		createEntity(field, load, func, game, buildingsArray, SecColor, "Slot", default_position_X+default_interval*3, default_position_Y-default_interval, leftSide, 7);
		createEntity(field, load, func, game, buildingsArray, SecColor, "Slot", default_position_X+default_interval*4, default_position_Y, leftSide, 8);
		// MAIN_BASE
		createEntity(field, load, func, game, buildingsArray, base, "Base",  default_position_X+default_interval*2, default_position_Y, leftSide, 9);
	}
	
	/*
	 * Läd alle Slot auf dem Spielfeld und gibt ihnen verweise auf mögliche Aktionen 
	 */
	public void createEntity(Panel field, Loader loader, Funktions func, Game game, Buildings[] buildingsArray, String Entitytype,  String EntityName, int  X, int Y, boolean leftSide, int ID){
		building = new Buildings();
		
		ImageIcon pic = new ImageIcon(Entitytype);
		label = new JLabel("");
		label.setIcon(pic);
		label.setBounds(X, Y, pic.getIconWidth(), pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent objUnit) {

				for (int i = 0; i < buildingsArray.length; i++) {

					if (buildingsArray[i] != null){
						if (buildingsArray[i].getLabel() == objUnit.getSource()){
							int slotID = i;
							int primID = 0;
							game.createUserOptions(slotID, primID, buildingsArray);
						}
					}else{
						// Index leer!
					}
				}
			}
		});
		building.setX(X);
		building.setY(Y);
		building.setLabel(label);
		building.setDescription("Ich bin ein ganz wichtiges Gebäude!");
		building.setName(EntityName);
		building.setSpwanableEntity(searchForPossibleEntitys(EntityName));
		if (leftSide){
			building.setNumber(ID);
			buildingsArray[ID] = building;
		}else{
			ID = ID + 9;
			building.setNumber(ID);
			buildingsArray[ID] = building;
		}
		field.add(label);
		field.repaint();
	}
	
	public Buildings[] createPrimaryBuilding(String entityLocation, int X, int Y, Buildings[] buildingsArray, String description, String buildingName, Game game, int slotID, int primID, Panel field){
		building = new Buildings();
		if (buildingsArray[slotID].getPrimerBuilding() != null){
			field.remove(buildingsArray[primID].getLabel());
			buildingsArray[primID-18].setPrimerBuilding(null);
		}
		ImageIcon pic = new ImageIcon(entityLocation);
		label = new JLabel("");
		label.setIcon(pic);
		label.setBounds(X, (Y-getBestOptimum(buildingName)), pic.getIconWidth(), pic.getIconHeight());
		building.setNumber(primID);
		building.setSlotID(slotID);
		label.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent objUnit) {
				for (int i = 0; i < buildingsArray.length; i++) {
					if (buildingsArray[i] != null){
						if (buildingsArray[i].getLabel() == objUnit.getSource()){
							int primID = buildingsArray[i].getNumber();
							int slotID = buildingsArray[i].getSlotID();

							game.createUserOptions(slotID, primID, buildingsArray);

						}
					}
				}
			}
		});
		building.setX(X);
		building.setY(Y);
		building.setLabel(label);
		building.setDescription(description);
		building.setName(buildingName);
		building.setSpwanableEntity(searchForPossibleEntitys(buildingName));
		buildingsArray[slotID].setPrimerBuilding(building);
		buildingsArray[primID] = building;

		field.remove(buildingsArray[slotID].getLabel());
		field.add(label);
		field.add(buildingsArray[slotID].getLabel());

		field.repaint();
		return buildingsArray;
	}
	
	public void destroyPrimaryBuilding( Buildings[] buildingsArray, int i, Panel field, int time){
		if (i >= 18){
			field.remove(buildingsArray[i].getLabel());
			buildingsArray[i] = null;
			buildingsArray[i-18].setPrimerBuilding(null);
			field.repaint();
		}
	}
	
	
	
	private String[] searchForPossibleEntitys(String BuildingName){
		
		// 0 Emty Field
		// 1-3 Kasserne
		// 4-6 Forge
		// 7 Armory
		// 8-9 Energy Gebäude
		// 10-11 Geld Gebäude
		// 12-13 Hospital
		// 14 Rocketped
		// 15 Base
		
		// Recruit
		switch (BuildingName){ 
		case "Slot":
			return new String[]{"Outpost", "Forge", "Hospital", "Bank", "Armory", "Generator", "Special Operations"};
		case "Outpost":
			return new String[]{"Marine", "Chronite Tank", "Recruit", "Barracks", "Destroy"};
		case "Barracks":
			return new String[]{"Marine", "Chronite Tank", "Sniper", "Gröditz", "Recruit", "Arsenal", "Destroy"};
		case "Arsenal":
			return new String[]{"Marine", "Chronite Tank", "Sniper", "Gröditz", "Hover Tank", "Black Queen", "Recruit", "Destroy"};
		case "Forge":
			return new String[]{"A25-Roman", "Scout", "Salvage", "Manufactory", "Destroy"};
		case "Manufactory":
			return new String[]{"A25-Roman", "Scout", "Phantom", "Sakata-MK2", "Salvage", "Mechanics Terminal", "Destroy"};
		case "Mechanics Terminal":
			return new String[]{"A25-Roman", "Scout", "Phantom", "Sakata-MK2", "Sakata Spider", "Gladiator", "Salvage", "Destroy"};
		case "Armory":
			return new String[]{"Financel Support", "Reinforcments", "Reserve Energy", "Modified Phantom", "Destroy"};
		case "Generator":
			return new String[]{"Power", "Solar Grid", "Destroy"};
		case "Solar Grid":
			return new String[]{"Power", "Modified Sakata", "Destroy"};
		case "Bank":
			return new String[]{"Exhange", "Treasury", "Destroy"};
		case "Treasury":
			return new String[]{"Traiding", "Destroy"};
		case "Hospital":
			return new String[]{"Resuscitate", "Meditec", "Rescue Team", "War Sanctum", "Destroy"};
		case "War Sanctum":
			return new String[]{"Recover", "Meditec", "Rescue Team", "Saint", "Sphinx", "Destroy"};
		case "Special Operations":
			return new String[]{"Launch Missile Green", "Launch Missile Blue", "Black Operations", "Special Froces", "Far Sniper", "A27-Pride", "Destroy"};
		case "Base":
			return new String[]{};
		default:
			return new String[]{};
		}
	}
	
	public int getBestOptimum(String buildingName){
		switch (buildingName){ 
		case "Armory":
			return 7;
		case "Hospital":
			return 8;
		case "Outpost":
			return 2;
		case "Special Operations":
			return 2;
		case "War Sanctum":
			return 5;
		case "Treasury":
			return 25;
		default:
			return 0;
		}	
	}
}
