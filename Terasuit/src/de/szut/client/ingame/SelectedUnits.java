package de.szut.client.ingame;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class SelectedUnits {
	
	/**
	 * Gibt die markierte Unit ID wieder zurück
	 * @param hashMap
	 * @param selectedEntitysID
	 * @param objUnit
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getUnit(HashMap<Integer, Unit> hashMap, ArrayList<Integer> selectedEntitysID, MouseEvent objUnit){
		for (Unit u : hashMap.values()) {
			if (u.getLabel() == objUnit.getSource()){
				selectedEntitysID.add(u.getEntityNummer());
				u.setEntitymarked(true);
				return selectedEntitysID;
			}
		}
		return selectedEntitysID;
	}
	
	/**
	 * Gibt eine Liste von UnitIDs zurück, die sich im einem Rechteck befinden
	 * @param hashMap
	 * @param selectedEntitysID
	 * @param x
	 * @param y
	 * @param h
	 * @param w
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getGroupOfUnits(HashMap<Integer, Unit> hashMap, ArrayList<Integer> selectedEntitysID, int x, int y, int h, int w){
		for (Unit u : hashMap.values()) {
			if (u.getEntityPositionX() > x && u.getEntityPositionY() > y){
				if (u.getEntityPositionX() < (x+h) && u.getEntityPositionY() < (y+w)){
					selectedEntitysID.add(u.getEntityNummer());
					u.setEntitymarked(true);
				}
			}
		}
		return selectedEntitysID;
	}
}
