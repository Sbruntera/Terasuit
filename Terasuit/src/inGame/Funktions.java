package inGame;

import grafig.Panel;

import java.util.ArrayList;

public class Funktions {
	
	ArrayList<Unit> entity = new ArrayList<Unit>();
	CreateUnit cunit = new CreateUnit();
	SelectedUnits selectedUnit = new SelectedUnits();
	
	public ArrayList<Unit> getEntity() {
		return entity;
	}

	public void setEntity(ArrayList<Unit> entity) {
		this.entity = entity;
	}

	// Erstellt eine neue Einheit auf dem Spielfeld und fügt es der Unitliste hinzu
	public void createEntity(Panel panel, String Entitytype){
		cunit.createEntity(panel, Entitytype, entity);
	}
	
	// Sucht alle Einheiten in einem Auswahlbereich
	public void findAllEntitys(int minX, int minY, int w, int h) {
		selectedUnit.getGroupOfUnits(entity, minX, minY, w, h);	
	}
}
