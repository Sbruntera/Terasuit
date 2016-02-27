package de.szut.client.ingame;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.ImageIcon;
import javax.swing.JProgressBar;

import de.szut.client.grafik.Panel;
import de.szut.client.logic.UnitData;
import de.szut.client.logic.UnitObject;
import de.szut.client.logic.UnitPics;

public class Funktions implements Runnable {

	HashMap<Integer, Unit> entity;
	ArrayList<Integer> selectedEntitysID;
	CreateUnit cunit;
	SelectedUnits selectedUnit;
	UnitPics pics = new UnitPics();
	private UnitData data = new UnitData();
	private Thread cThread;
	private JProgressBar[] listOfJProgressBar = new JProgressBar[36];
	private ConcurrentLinkedQueue<Unit> unitQueue = new ConcurrentLinkedQueue<Unit>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private boolean ended;

	public Funktions() {
		pics.generateAllEntityPictures();
		this.data.createUnitData();
	}

	public HashMap<Integer, Unit> getEntity() {
		return entity;
	}

	public void setEntity(HashMap<Integer, Unit> entity) {
		this.entity = entity;
	}

	// Erstellt eine neue Einheit auf dem Spielfeld und fügt es der Unitliste
	// hinzu
	public void createEntity(Panel field, String Entitytype, int color,
			boolean airUnit, Game game, short unitID, Point position) {
		unitQueue.add(cunit.createEntity(field, game, Entitytype, color,
				airUnit, this, unitID, position, pics));
	}

	public void findEntity(MouseEvent objUnit, Game game) {
		deMarkEntittys();
		selectedEntitysID = selectedUnit.getUnit(getEntity(),
				selectedEntitysID, objUnit);
		for (int id : selectedEntitysID) {
			if (entity.containsKey(id)) {
				String type = entity.get(id).getEntityname();
				boolean directionLeft = entity.get(id).isEntityRushLeft();
				int color = entity.get(id).getEntitymembership();
				ImageIcon pic = pics.getEntityPic(type, color, directionLeft,
						true);
				entity.get(id).getLabel().setIcon(pic);
				type = splitUp(entity.get(id).getEntityname());
				UnitObject unit = data.returnUnitData(type);
				String description = unit.getDescription();
				setInformationInGame(game, type, description);
			}
		}
	}

	// Sucht alle Einheiten in einem Auswahlbereich
	public void findAllEntitys(int minX, int minY, int w, int h, int playerID) {
		selectedEntitysID = selectedUnit.getGroupOfUnits(getEntity(),
				selectedEntitysID, minX, minY, w, h);
		for (int id : selectedEntitysID) {
			if (entity.containsKey(id)) {
				if (entity.get(id).getEntitymembership() == playerID) {
					String type = entity.get(id).getEntityname();
					boolean directionLeft = entity.get(id).isEntityRushLeft();
					int color = entity.get(id).getEntitymembership();
					ImageIcon pic = pics.getEntityPic(type, color,
							directionLeft, true);
					entity.get(id).getLabel().setIcon(pic);
				}
			}
		}
	}

	// Iteriert über eine Liste mit IDs von Einheiten in der Entity List und
	// verändert ihre Helligkeit zu dunkel
	public void deMarkEntittys() {
		for (int id : selectedEntitysID) {
			if (entity.containsKey(id)) {
				String type = entity.get(id).getEntityname();
				boolean directionLeft = entity.get(id).isEntityRushLeft();
				int color = entity.get(id).getEntitymembership();
				ImageIcon pic = pics.getEntityPic(type, color, directionLeft,
						false);
				entity.get(id).getLabel().setIcon(pic);
			}
		}
		selectedEntitysID.clear();
	}

	public void destroyUserOptions(Panel console, Game game) {
		if (this.selectedEntitysID.size() != 0) {
			game.btnAction.createUserUnitOptions(console);
		} else {
			game.btnAction.destroyUserOptions(console);
		}
	}

	private void setInformationInGame(Game game, String type, String description) {
		game.changeInformation(type, description);
	}

	public String splitUp(String Unitname) {
		String[] parts = Unitname.split("/");
		Unitname = parts[2];
		Unitname = Unitname.substring(0, Unitname.length() - 4);
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
		ended = false;
	}

	@Override
	public void run() {
		addUnits();
		moveUnits();
		buildBuildings();
	}

	private void addUnits() {
		while (!unitQueue.isEmpty()) {
			Unit u = unitQueue.remove();
			entity.put(u.getEntityNummer(), u);
		}
	}

	private void moveUnits() {
		for (Unit e : entity.values()) {
			Unit[] nearestUnits = getNearestUnit(e.getEntityPositionX(),
					(e.getEntitymembership() - 1 & 2) == 2);
			System.out.println(nearestUnits[0]);
			if (e.hasInRange(nearestUnits) && !e.isEntityRunning()) {
				Bullet b = e.shoot(nearestUnits);
				if (b != null) {
					bullets .add(b);
				}
//			} else if (e.hasInRange(new Attackable[] {
//					mainBuildings[e.getEntitymembership() - 1 >> 1], null })) {
//				Bullet b = e.shoot(new Attackable[] {
//						mainBuildings[e.getEntitymembership() - 1 >> 1], null });
//				if (b != null) {
//					bullets.add(b);
//				}
			} else if (e.isEntityMove()) {
				if (e.isEntityRushLeft()) {
					e.setEntityPositionX(e.getEntityPositionX()
							- e.getEntitySpeed());
				} else {
					e.setEntityPositionX(e.getEntityPositionX()
							+ e.getEntitySpeed());
				}
			}
		}
	}

	private void buildBuildings() {
		for (int i = 0; i < listOfJProgressBar.length; i++) {
			if (listOfJProgressBar[i] != null) {
				listOfJProgressBar[i]
						.setValue(listOfJProgressBar[i].getValue() + 1);
				if (listOfJProgressBar[i].getValue() == 100) {
					listOfJProgressBar[i].setVisible(false);
					listOfJProgressBar[i] = null;
				}
			}
		}
	}

	private Unit[] getNearestUnit(int i, boolean right) {
		Unit[] nearestUnits = new Unit[2];
		int[] difference = new int[] { 32767, 32767 };
		for (Unit u : entity.values()) {
			if (Math.abs(u.getEntityPositionX() - i) < difference[Boolean
					.compare(u.isFlyingEntity(), false)]
					&& right != ((u.getEntitymembership() - 1 & 2) == 2)) {
				nearestUnits[Boolean.compare(u.isFlyingEntity(), false)] = u;
				difference[Boolean.compare(u.isFlyingEntity(), false)] = Math
						.abs(u.getEntityPositionX() - i);
			}
		}
		return nearestUnits;
	}
	
	public void end() {
		ended = true;
	}

	public boolean ended() {
		return ended;
	}

	public Unit getEntity(int i) {
		return entity.get(i);
	}

	public void addProgressBar(JProgressBar progressBar, int index) {
		this.listOfJProgressBar[index] = progressBar;
	}

	public JProgressBar[] getListOfJProgressBar() {
		return listOfJProgressBar;
	}
}
