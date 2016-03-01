package de.szut.client.logic;

import java.util.HashMap;

public class UnitData {
	

//	UnitObject UnitO;
//	String description, int money, int electricity,
//	int human, int monarchy, int live, int speed, int dmg, int rpm,
//	int splashDamage, int groundGround, int groundAir, int airAir
	
	HashMap<String, UnitObject> UnitDataHash = new HashMap<String, UnitObject>();
	
	
	
	public void createUnitData(){

		UnitDataHash.put("Marine", new UnitObject("Light armor. Armed with shock rifles and rockets, effective against both ground and air.", 0, 2, 0, 0, 60, 10, 15, 10, 180, 0, 10, true, true));
        UnitDataHash.put("Chronite_Tank", new UnitObject("Chronite (heavy) armor. Armed with explosive shells, but cannot fire at air targets.", 5, 10, 0, 0, 600, 8, 60, 35, 225, 2, 7, true, false));
        UnitDataHash.put("Gröditz", new UnitObject("Chronite (heavy) armor. Armed with standart machine guns.", 5, 16, 0, 0, 340, 9, 40, 8, 140, 0, 9, true, true));
        UnitDataHash.put("A25-Roman", new UnitObject("Titanium armor. Armes with pulse rifles, but cannot fire at air targets.", 0, 0, 5, 0, 250, 11, 80, 32, 160, 0, 10, true, false));
        UnitDataHash.put("Gladiator", new UnitObject("Special armor, resistant against missile damage. Armed with a high frequency pulse rifle. Not as effective against air targets.", 30, 10, 25, 0, 500, 12, 25, 5, 170, 0, 10, true, true));
        UnitDataHash.put("Hover_Tank", new UnitObject("Special armor, resistant against missile damage. Armed with high impact, linear Positron cannons.", 0, 30, 10, 0, 370, 9, 80, 37, 200, 0, 8, true, false));
        UnitDataHash.put("Meditec", new UnitObject("Light armor. Can heal/repair any friendly ground unit.", 0, 6, 0, 0, 100, 10, 20, 18, 130, 0, 10, true, true));
        UnitDataHash.put("Modified_Sakata", new UnitObject("Modified to be faster in movment and firing.", 0, 0, 0, 50, 450, 12, 65, 9, 140, 0, 10, true, true));
        UnitDataHash.put("Sakata_Spider", new UnitObject("Chronite (heavy) armor. Armed with singualar voltage beams.", 0, 0, 23, 0, 350, 11, 60, 11, 170, 0, 10, true, false));
        UnitDataHash.put("Sakata-MK2", new UnitObject("Special armor. Armed with anti-air Hadron cannons. cannot attack ground targets.", 0, 0, 21, 0, 200, 11, 200, 40, 200, 0, 10, false, true));
        UnitDataHash.put("Sniper", new UnitObject("Light armor. Equipped with armorpiercing Positron rifles. Not as effective against air targets.", 0, 7, 12, 0, 150, 10, 70, 30, 425, 0, 14, true, true));
        UnitDataHash.put("Sphinx", new UnitObject("Composite Armor, effective against heavy attacks, but weak against light attacks. Fires anti-special armor shells.", 0, 0, 0, 80, 1300, 9, 45, 20, 160, 0, 6, true, true));
      
        UnitDataHash.put("Phantom", new UnitObject("Titanium armor. Armed with area-damage rapid cannons.", 23, 5, 3, 0, 320, 11, 100, 14, 180, 0, 10, true, true));
        UnitDataHash.put("Scout", new UnitObject("Titanium armor. Armed with pulse lasers.", 3, 0, 7, 0, 140, 11, 40, 9, 210, 0, 11, true, true)) ;
        UnitDataHash.put("Black_Queen", new UnitObject("Special armor, resistant against missile damage. Armed with anti-air missiles and long ranged bombs.", 30, 50, 25, 0, 550, 11, 150, 20, 190, 0, 9, true, true));
        UnitDataHash.put("Modified_Phantom", new UnitObject("Modified to have stronger waepons and more durable armor.", 0, 0, 0, 35, 400, 11, 120, 12, 180, 0, 11, true, true));
        UnitDataHash.put("Saint", new UnitObject("Spezial armor. Armed with anti-air Positron cannons and rapid ground rifles.", 0, 0, 0, 50, 400, 11, 100, 10, 185, 0, 10, true, true));
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
