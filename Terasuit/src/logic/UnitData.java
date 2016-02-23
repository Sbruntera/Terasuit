package logic;

import java.util.HashMap;
import java.util.Map.Entry;

public class UnitData {
	

//	UnitObject UnitO;
//	String description, int money, int electricity,
//	int human, int monarchy, int live, int speed, int dmg, int rpm,
//	int splashDamage, int groundGround, int groundAir, int airAir
	
	HashMap<String, UnitObject> UnitDataHash = new HashMap<String, UnitObject>();
	
	
	
	public void createUnitData(){

		UnitDataHash.put("Marine", new UnitObject("Marine_Description", 0, 2, 0, 0, 62, 1, 2, 1, 5, 0, 2, 2, 0));
		UnitDataHash.put("Chronite_Tank", new UnitObject("Chronite_Tank_Description", 5, 10, 0, 0, 310, 1, 2, 1, 5, 2, 5, 0, 0));
		UnitDataHash.put("Gröditz", new UnitObject("Gröditz_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("A25-Roman", new UnitObject("A25_Roman_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Gladiator", new UnitObject("Gladiator_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Hover_Tank", new UnitObject("Hover_Tank_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Meditec", new UnitObject("Meditec_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Modified_Sakata", new UnitObject("Modified_Sakata_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Sakata_Spider", new UnitObject("Sakata Spide_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Sakata-MK2", new UnitObject("Sakata_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Sakata", new UnitObject("Sakata_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Sniper", new UnitObject("Sniper_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Sphinx", new UnitObject("Sphinx_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		
		UnitDataHash.put("Phantom", new UnitObject("Phantom_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Scout", new UnitObject("Scout_Description", 3, 0, 7, 0, 140, 1, 2, 1, 5, 0, 0, 2, 2)) ;
		UnitDataHash.put("Black_Queen", new UnitObject("Black Queen_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Modified_Phantom", new UnitObject("Modified_Phantom_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
		UnitDataHash.put("Saint", new UnitObject("Saint_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	}
	
	/**
	 * Iteriert über die Hashmap mit den Einheitendaten und gibt sie zurück
	 * @param Unitname (No Location => "Unit/Ground/Marine.png")
	 * @return unitObject
	 */
	public UnitObject returnUnitData(String Unitname){
	    Unitname = replaceBlancs(Unitname);
		return UnitDataHash.get(Unitname);
	}
	
	private String replaceBlancs(String Unitname){
		return Unitname.replace(" ", "_");
	}
}
