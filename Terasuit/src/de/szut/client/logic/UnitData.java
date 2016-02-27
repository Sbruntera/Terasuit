package de.szut.client.logic;

import java.util.HashMap;

public class UnitData {
	

//	UnitObject UnitO;
//	String description, int money, int electricity,
//	int human, int monarchy, int live, int speed, int dmg, int rpm,
//	int splashDamage, int groundGround, int groundAir, int airAir
	
	HashMap<String, UnitObject> UnitDataHash = new HashMap<String, UnitObject>();
	
	
	
	public void createUnitData(){

		 UnitDataHash.put("Marine", new UnitObject("Light armor. Armed with shock rifles and rockets, effective against both ground and air.", 0, 2, 0, 0, 62, 20, 200, 1, 500, 0, 2, 2, 0));
	        UnitDataHash.put("Chronite_Tank", new UnitObject("Chronite (heavy) armor. Armed with explosive shells, but cannot fire at air targets.", 5, 10, 0, 0, 310, 1, 2, 1, 5, 2, 5, 0, 0));
	        UnitDataHash.put("Gröditz", new UnitObject("Chronite (heavy) armor. Armed with standart machine guns.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("A25-Roman", new UnitObject("Titanium armor. Armes with pulse rifles, but cannot fire at air targets.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Gladiator", new UnitObject("Special armor, resistant against missile damage. Armed with a high frequency pulse rifle. Not as effective against air targets.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Hover_Tank", new UnitObject("Special armor, resistant against missile damage. Armed with high impact, linear Positron cannons.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Meditec", new UnitObject("Light armor. Can heal/repair any friendly ground unit.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Modified_Sakata", new UnitObject("Modified to be faster in movment, firing and repairing.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Sakata_Spider", new UnitObject("Chronite (heavy) armor. Armed with singualar voltage beams. Self repairing technology.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Sakata-MK2", new UnitObject("Special armor. Armed with anti-air Hadron cannons. cannot attack ground targets.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Sniper", new UnitObject("Light armor. Equipped with armorpiercing Positron rifles. Not as effective against air targets.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Sphinx", new UnitObject("Composite Armor, effective against heavy attacks, but weak against light attacks. Fires anti-special armor shells.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	       
	        UnitDataHash.put("Phantom", new UnitObject("Titanium armor. Armed with area-damage rapid cannons.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Scout", new UnitObject("Titanium armor. Armed with pulse lasers.", 3, 0, 7, 0, 140, 1, 2, 1, 5, 0, 0, 2, 2)) ;
	        UnitDataHash.put("Black_Queen", new UnitObject("Special armor, resistant against missile damage. Armed with anti-air missiles and long ranged bombs.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Modified_Phantom", new UnitObject("Modified to have stronger waepons and more durable armor.", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
	        UnitDataHash.put("Saint", new UnitObject("Spezial armor. Armed with anti-air Positron cannons and rapid ground rifles. Self-repairing technology", 0, 0, 5, 0, 250, 1, 2, 1, 5, 0, 2, 0, 0));
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
