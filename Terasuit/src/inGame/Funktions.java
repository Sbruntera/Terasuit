package inGame;

import grafig.Panel;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Funktions {
	
	ArrayList<Unit> entity = new ArrayList<Unit>();
	ArrayList<Integer> selectedEntitysID = new ArrayList<Integer>();
	CreateUnit cunit = new CreateUnit();
	SelectedUnits selectedUnit = new SelectedUnits();
	
	public ArrayList<Unit> getEntity() {
		return entity;
	}

	public void setEntity(ArrayList<Unit> entity) {
		this.entity = entity;
	}

	// Erstellt eine neue Einheit auf dem Spielfeld und fügt es der Unitliste hinzu
	public void createEntity(Panel field, String Entitytype, int color, boolean airUnit){
		entity = cunit.createEntity(field, Entitytype, entity, color, airUnit, this);
	}
	
	public void findEntity(MouseEvent objUnit) {
		selectedEntitysID = selectedUnit.getUnit(getEntity(), selectedEntitysID, objUnit);
		for (int id : selectedEntitysID) {
			String type = entity.get(id-1).getEntityname();
			boolean directionLeft = entity.get(id-1).isEntityRushLeft();
			int color = entity.get(id-1).getEntitymembership();
			ImageIcon pic = new ImageIcon(cunit.mark(type, directionLeft, color, false));
			Unit unit2 = new Unit();
			unit2 = entity.get(id-1);
			unit2.getLabel().setIcon(pic);
			entity.set(id-1, unit2);
		}
	}
	
	
	// Sucht alle Einheiten in einem Auswahlbereich
	public void findAllEntitys(int minX, int minY, int w, int h) {
		selectedEntitysID = selectedUnit.getGroupOfUnits(getEntity(), selectedEntitysID, minX, minY, w, h);
		for (int id : selectedEntitysID) {
			String type = entity.get(id-1).getEntityname();
			boolean directionLeft = entity.get(id-1).isEntityRushLeft();
			int color = entity.get(id-1).getEntitymembership();
			ImageIcon pic = new ImageIcon(cunit.mark(type, directionLeft, color, false));
			Unit unit = new Unit();
			unit = entity.get(id-1);
			unit.getLabel().setIcon(pic);
			entity.set(id-1, unit);
		}
	}
	
	// Iteriert über eine Liste mit IDs von Einheiten in der Entity List und verändert ihre Helligkeit zu dunkel
	public void deMarkEntittys(){
		for (int id : selectedEntitysID) {
			String type = entity.get(id-1).getEntityname();
			boolean directionLeft = entity.get(id-1).isEntityRushLeft();
			int color = entity.get(id-1).getEntitymembership();
			ImageIcon pic = new ImageIcon(cunit.mark(type, directionLeft, color, true));
			Unit unit = new Unit();
			unit = entity.get(id-1);
			unit.getLabel().setIcon(pic);
			entity.set(id-1, unit);
		}
		selectedEntitysID.clear();
	}
	
	/**
	 * Updated die Userliste mit den aktuellen gesendeten Serverpatch
	 * @param panel
	 * @param NEWentity
	 * @param OLDentity
	 */
	public void UpdateGameEngine(Panel panel, ArrayList<Unit> NEWentity, ArrayList<Unit> OLDentity){
		
		// Übergebende neue Angaben, werden in die Userliste übertragen
		for (int i = 0; i != NEWentity.size(); i++){
			int numb = NEWentity.get(i).getEntityNummer();
			for (int n = 0; n != OLDentity.size(); n++){
				if (OLDentity.get(n).getEntityNummer() == numb){
					OLDentity.set(n, NEWentity.get(i));
				}
			}
		}
	}
	
	public void UpdateBuildingList(Panel panel, ArrayList<Buildings> NEWbuildings, ArrayList<Buildings> OLDbuilding){
		
		// Übergebende neue Gebäude werden in die Userliste (Buildings) übertragen
		for (int i = 0; i != NEWbuildings.size(); i++){
			int numb = NEWbuildings.get(i).getNumber();
			for (int n = 0; n != OLDbuilding.size(); n++){
				if (OLDbuilding.get(n).getNumber() == numb){
					OLDbuilding.set(n, NEWbuildings.get(i));
				}
			}
		}
		
	}
	
	public void UpdatedGameEntity(Panel panel, ArrayList<Unit> entity){
		for (int i = 0; i != entity.size(); i++){
			if (entity.get(i).isEntityFire() == false){
				if (entity.get(i).isEntityMove() == true){
					if (entity.get(i).isEntityRushLeft() == true){
						// Läuft nach links
					}else{
						// Läuft nach rechts
					}
				}
			}else{
				// Einheit feuert auf gegner
			}
		}
	}
	
	public void UpdatedGameBuildings(Panel panel, ArrayList<Buildings> building){
		for (int i = 0; i != building.size(); i++){
			
		}
	}
	
	/**
	 * Sucht im Panel nach Panels und gibt nach Wunsch das richtige zurück
	 * @return
	 */
	public Panel searchForThePanel(Panel panel){
		
		ArrayList<JPanel> list = new ArrayList<JPanel>();
		Component[] components = panel.getComponents();
		for (Component component : components) {
		    if (component.getClass().equals(JPanel.class)) {
		        list.add((JPanel)component);
		    }
		}
		
		return null;
	}
	
	
}
