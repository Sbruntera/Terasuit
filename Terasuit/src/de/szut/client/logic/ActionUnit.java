package de.szut.client.logic;

import java.util.ArrayList;

import de.szut.client.ingame.Unit;

/**
 * 
 * @author Alexander
 *
 */
public class ActionUnit {

	Unit unit = new Unit();
	ArrayList<Unit> UpdateList = new ArrayList<Unit>();

	/**
	 * Gibt die UpdateListe zurück
	 * 
	 * @return
	 */
	public ArrayList<Unit> getUpdateList() {
		return UpdateList;
	}

	/**
	 * Setz die UpgradeListe
	 * 
	 * @param updateList
	 */
	public void setUpdateList(ArrayList<Unit> updateList) {
		UpdateList = updateList;
	}

	/**
	 * Fügt Einheiten der Liste hinzu
	 * 
	 * @param List
	 */
	public void updateEntityList(ArrayList<Unit> List) {

		for (int i = List.size(); i != 0; i--) {
			UpdateList.add(List.get(i));
		}

	}

}
