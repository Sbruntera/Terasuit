package inGame;

import grafig.Panel;
import java.util.ArrayList;
import javax.swing.ImageIcon;

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
	public void createEntity(Panel panel, String Entitytype, int color){
		cunit.createEntity(panel, Entitytype, entity, color);
	}
	
	// Sucht alle Einheiten in einem Auswahlbereich
	public void findAllEntitys(int minX, int minY, int w, int h) {
		selectedUnit.getGroupOfUnits(entity, minX, minY, w, h);	
	}
	
	public void deMarkEntittys(){
		for (int i = 0; i < entity.size(); i++) {
			if (entity.get(i).isEntitymarked() == true){
				entity.get(i).getLabel().setIcon(new ImageIcon("Unit/Soldat_Blau_Rechts2.png"));
				entity.get(i).setEntitymarked(false);
			}
		}
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
	
	
}
