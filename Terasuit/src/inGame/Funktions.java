package inGame;

import grafig.Panel;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import logic.UnitData;
import logic.UnitObject;
import logic.UnitPics;

public class Funktions implements Runnable{
	
	HashMap<Integer, Unit> entity;
	ArrayList<Integer> selectedEntitysID;
	CreateUnit cunit;
	SelectedUnits selectedUnit;
	UnitPics pics = new UnitPics();
	private UnitData data = new UnitData();
	private Thread cThread;
	
	public Funktions(){
		pics.generateAllEntityPictures();
		this.data .createUnitData();
	}
	
	public HashMap<Integer, Unit> getEntity() {
		return entity;
	}

	public void setEntity(HashMap<Integer, Unit> entity) {
		this.entity = entity;
	}

	// Erstellt eine neue Einheit auf dem Spielfeld und fügt es der Unitliste hinzu
	public void createEntity(Panel field, String Entitytype, int color, boolean airUnit, Game game, short unitID, Point position){
		entity.put((int) unitID, cunit.createEntity(field, game, Entitytype, color, airUnit, this, unitID, position, pics));
	}
	
	public void findEntity(MouseEvent objUnit, Game game) {
		deMarkEntittys();
		selectedEntitysID = selectedUnit.getUnit(getEntity(), selectedEntitysID, objUnit);
		for (int id : selectedEntitysID) {
			String type = entity.get(id).getEntityname();
			boolean directionLeft = entity.get(id).isEntityRushLeft();
			int color = entity.get(id).getEntitymembership();
			ImageIcon pic = pics.getEntityPic(type, color, directionLeft, true);
			entity.get(id).getLabel().setIcon(pic);
			type = splitUp(entity.get(id).getEntityname());
			UnitObject unit = data.returnUnitData(type);
			String description = unit.getDescription();
			setInformationInGame(game, type, description);
		}
	}
	
	
	// Sucht alle Einheiten in einem Auswahlbereich
	public void findAllEntitys(int minX, int minY, int w, int h, int playerID) {
		selectedEntitysID = selectedUnit.getGroupOfUnits(getEntity(), selectedEntitysID, minX, minY, w, h);
		for (int id : selectedEntitysID) {
			if (entity.get(id).getEntitymembership() == playerID){
				String type = entity.get(id).getEntityname();
				boolean directionLeft = entity.get(id).isEntityRushLeft();
				int color = entity.get(id).getEntitymembership();
				ImageIcon pic = pics.getEntityPic(type, color, directionLeft, true);
				entity.get(id).getLabel().setIcon(pic);
			}
		}
	}
	
	// Iteriert über eine Liste mit IDs von Einheiten in der Entity List und verändert ihre Helligkeit zu dunkel
	public void deMarkEntittys(){
		for (int id : selectedEntitysID) {
			String type = entity.get(id).getEntityname();
			boolean directionLeft = entity.get(id).isEntityRushLeft();
			int color = entity.get(id).getEntitymembership();
			ImageIcon pic = pics.getEntityPic(type, color, directionLeft, false);
			entity.get(id).getLabel().setIcon(pic);
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

	public void destroyUserOptions(Panel console, Game game) {
		if (this.selectedEntitysID.size() != 0){
			game.btnAction.createUserUnitOptions(console);
		}else{
			game.btnAction.destroyUserOptions(console);
		}
	}
	
	private void setInformationInGame(Game game, String type, String description){
		game.changeInformation(type, description);
	}

	public String splitUp(String Unitname){
		String[] parts = Unitname.split("/");
		Unitname = parts[2];
		Unitname = Unitname.substring(0, Unitname.length()-4);	
		return Unitname;
	}
	
	public void reset() {
		entity = new HashMap<Integer, Unit>();
		selectedEntitysID = new ArrayList<Integer>();
		cunit = new CreateUnit();
		selectedUnit = new SelectedUnits();
		Controller controller = new Controller(this);
		cThread = new Thread(controller);
		cThread.start();
	}

	@Override
	public void run() {
		moveUnits();
	}

	private void moveUnits() {
		for (Unit e : entity.values()) {
			if (e.isEntityMove()) {
				if (e.isEntityRushLeft()) {
					e.setEntityPositionX(e.getEntityPositionX() - e.getEntitySpeed());
				} else {
					e.setEntityPositionX(e.getEntityPositionX() + e.getEntitySpeed());
				}
			}
		}
	}

	public boolean ended() {
		return false;
	}

	public Unit getEntity(int i) {
		return entity.get(i);
	}
}
