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

		UnitDataHash.put("Marine", new UnitObject("Marine_Description", 0, 2, 0, 0, 62, 1, 2, 1, 5, 0, 2, 2, 0)) ;
		UnitDataHash.put("Chronite_Tank", new UnitObject("Chronite_Tank_Description", 5, 10, 0, 0, 310, 1, 2, 1, 5, 2, 5, 0, 0)) ;
		UnitDataHash.put("Scout", new UnitObject("Scout_Description", 3, 0, 7, 0, 140, 1, 2, 1, 5, 0, 0, 2, 2)) ;
		UnitDataHash.put("A25_Roman", new UnitObject("A25_Roman_Description", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0)) ;
	}
	
	/**
	 * Iteriert über die Hashmap mit den Einheitendaten und gibt sie zurück
	 * @param Unitname (No Location => "Unit/Ground/Marine.png")
	 * @return unitObject
	 */
	public UnitObject returnUnitData(String Unitname){
	    Unitname = replaceBlancs(Unitname);
		for(Entry<String, UnitObject> entry : UnitDataHash.entrySet()) {
		    String key = entry.getKey();
		    UnitObject unitObject = entry.getValue();
		    if (key.equals(Unitname)){
		    	return unitObject;
		    }
		}
		return null;
	}
	
	private String replaceBlancs(String Unitname){
		return Unitname.replace(" ", "_");
	}
}
