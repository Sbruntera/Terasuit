package de.szut.client.ingame;

import java.awt.Container;
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

/**
 * 
 * @author Alexander, Simeon
 *
 */
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
	private ConcurrentLinkedQueue<Integer> removeQueue = new ConcurrentLinkedQueue<Integer>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private boolean ended;
	private MainBuilding[] mainBuildings = new MainBuilding[2];
	private double[] resources;
	private int wait = 10;

	ArrayList<Bullet> bulletsToRemove;
	HashMap<Integer, Unit> unitsToRemove;
	private Game game;

	/**
	 * Initialisiert ein Funktionsobjekt
	 */
	public Funktions() {
		pics.generateAllEntityPictures();
		this.data.createUnitData();
		bulletsToRemove = new ArrayList<Bullet>();
		unitsToRemove = new HashMap<Integer, Unit>();
	}

	/**
	 * Gibt die Liste der Einheiten zurück
	 * 
	 * @return
	 */
	public HashMap<Integer, Unit> getEntity() {
		return entity;
	}

	/**
	 * Erstellt eine neue Einheit auf dem Spielfeld und fügt es der Queue hinzu
	 * 
	 * @param field
	 * @param Entitytype
	 * @param color
	 * @param airUnit
	 * @param unitID
	 * @param position
	 * @param ownUnit
	 */
	public void createEntity(Panel field, String Entitytype, int color,
			boolean airUnit, short unitID, Point position, boolean ownUnit) {
		Unit u = cunit.createEntity(field, game, Entitytype, color, airUnit,
				this, unitID, position, pics);
		unitQueue.add(u);
		if (ownUnit) {
			payPrice(u.getPrice());
		}
	}

	/**
	 * Markiert eine Einheit
	 * 
	 * @param objUnit
	 */
	public void findEntity(MouseEvent objUnit) {
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
				setInformationInGame(type, description);
			}
		}
	}

	/**
	 * Sucht alle Einheiten in einem Auswahlbereich
	 * 
	 * @param minX
	 * @param minY
	 * @param w
	 * @param h
	 * @param playerID
	 */
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

	/**
	 * Iteriert über eine Liste mit IDs von Einheiten in der Entity List und
	 * verändert ihre Helligkeit zu dunkel
	 */
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

	/**
	 * Entvernt alle Optionsbuttons
	 * 
	 * @param console
	 */
	public void destroyUserOptions(Panel console) {
		if (this.selectedEntitysID.size() != 0) {
			game.btnAction.createUserUnitOptions(console);
		} else {
			game.btnAction.destroyUserOptions(console);
			setInformationInGame("", "");
			game.setAllJProgressBarVisible(false);

		}
	}

	/**
	 * Ändert die Beschreibungsanzege
	 * 
	 * @param type
	 * @param description
	 */
	private void setInformationInGame(String type, String description) {
		game.changeInformation(type, description);
	}

	/**
	 * Teilt einen String namch "/" und gibt den dritten Zeil zurück
	 * 
	 * @param Unitname
	 * @return
	 */
	public String splitUp(String Unitname) {
		String[] parts = Unitname.split("/");
		Unitname = parts[2];
		Unitname = Unitname.substring(0, Unitname.length() - 4);
		return Unitname;
	}

	/**
	 * Setzt das Spiel zurück
	 */
	public void reset() {
		unitQueue = new ConcurrentLinkedQueue<Unit>();
		listOfJProgressBar = new JProgressBar[36];
		entity = new HashMap<Integer, Unit>();
		selectedEntitysID = new ArrayList<Integer>();
		cunit = new CreateUnit();
		selectedUnit = new SelectedUnits();
		bullets = new ArrayList<Bullet>();
		resources = new double[] { 50, 50, 50, 0 };
		cThread = new Thread(this);
		ended = false;
		wait = 10;
	}

	@Override
	public void run() {
		while (!ended) {
			long i = System.currentTimeMillis();
			removeUnits();
			addUnits();
			moveBullets();
			moveUnits();
			buildBuildings();
			generateResources();
			try {
				Thread.sleep(100 - (System.currentTimeMillis() - i));
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Entfernt Einheiten die gestorben sind
	 */
	private void removeUnits() {
		while (!removeQueue.isEmpty()) {
			Integer i = removeQueue.remove();
			if (entity.get(i) != null) {
				entity.get(i).getLabel().getParent()
						.remove(entity.get(i).getLabel());
				;
				entity.remove(i);
			}
		}
	}

	/**
	 * Fügt neue Einheiten hinzu
	 */
	private void addUnits() {
		while (!unitQueue.isEmpty()) {
			Unit u = unitQueue.remove();
			entity.put(u.getEntityNummer(), u);
		}
	}

	/**
	 * Bewegt alle Kugeln
	 */
	private void moveBullets() {
		for (Bullet b : bullets) {
			if (b.move()) {
				b.getTarget().dealDamage(b.getDamage());
				bulletsToRemove.add(b);
				if (!b.getTarget().isAlive()) {
					if (b.getTarget() instanceof Unit) {
						// unitsToRemove.put(((Unit)
						// b.getTarget()).getEntityNummer(), (Unit)
						// b.getTarget());
					}
				}
			}
		}
		for (Bullet b : bulletsToRemove) {
			game.field.remove(b.getLabel());
			bullets.remove(b);
		}

		game.repaint();

		bulletsToRemove.clear();

		for (Unit u : unitsToRemove.values()) {
			entity.remove(u.getEntityNummer());
			Container parent = u.getLabel().getParent();
			if (parent != null) {
				parent.remove((u.getLabel()));
				parent.repaint();
				parent.revalidate();
			}
		}

		unitsToRemove.clear();
	}

	/**
	 * Bewegt alle Einheiten
	 */
	private void moveUnits() {
		for (Unit e : entity.values()) {
			Unit[] nearestUnits = getNearestUnit(e.getEntityPositionX(),
					(e.getEntitymembership() - 1 & 2) == 2);
			if (e.hasInRange(nearestUnits)
					&& !e.isEntityRunning()
					&& e.isEntityRushLeft() == ((e.getEntitymembership() - 1 & 2) == 2)) {
				Bullet b = e.shoot(nearestUnits);
				if (b != null) {
					bullets.add(b);
					game.showBullet(b.getLabel());
				}
			} else if (e
					.hasInRange(new Attackable[] {
							mainBuildings[1 - (e.getEntitymembership() - 1 >> 1)],
							null })
					&& e.isEntityRushLeft() == ((e.getEntitymembership() - 1 & 2) == 2)) {
				Bullet b = e.shoot(new Attackable[] {
						mainBuildings[1 - (e.getEntitymembership() - 1 >> 1)],
						null });
				if (b != null) {
					bullets.add(b);
					game.showBullet(b.getLabel());
				}
			} else if (e.isEntityMove()) {
				if (e.isEntityRushLeft() && e.getEntityPositionX() >= 294) {
					e.setEntityPositionX(e.getEntityPositionX()
							- e.getEntitySpeed() / 5.0);
				} else if (!e.isEntityRushLeft()
						&& e.getEntityPositionX() <= 1344) {
					e.setEntityPositionX(e.getEntityPositionX()
							+ e.getEntitySpeed() / 5.0);
				}
			}
		}
	}

	/**
	 * Baut alle Gebäude
	 */
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

	/**
	 * Generiert neue Rohstoffe
	 */
	private void generateResources() {
		resources[0] += 0.08;
		resources[1] += 0.05;
		resources[2] += 0.05;
		resources[3] += 0.02;
		if (wait > 0) {
			wait--;
		} else {
			game.setResources(resources);
		}
	}

	/**
	 * Gibt die näheste Einheit zu einem Punkt zurück
	 * 
	 * @param position
	 *            Position der Einheit
	 * @param right
	 *            Seite der Einheit
	 * @return Nähesten Feinde der Einheit position0 = Ground; position1 = Air
	 */
	private Unit[] getNearestUnit(double position, boolean right) {
		Unit[] nearestUnits = new Unit[2];
		double[] difference = new double[] { 32767, 32767 };
		for (Unit u : entity.values()) {
			if (Math.abs(u.getEntityPositionX() - position) < difference[Boolean
					.compare(u.isFlyingEntity(), false)]
					&& right != ((u.getEntitymembership() - 1 & 2) == 2)) {
				nearestUnits[Boolean.compare(u.isFlyingEntity(), false)] = u;
				difference[Boolean.compare(u.isFlyingEntity(), false)] = Math
						.abs(u.getEntityPositionX() - position);
			}
		}
		return nearestUnits;
	}

	/**
	 * Laßt das spiel Auslaufen
	 */
	public void end() {
		ended = true;
	}

	/**
	 * Gibt zurück ob das game noch läuft
	 * 
	 * @return
	 */
	public boolean ended() {
		return ended;
	}

	/**
	 * Gibt die Einheit mit einer bestimmten ID zurück
	 * 
	 * @param i
	 * @return
	 */
	public Unit getEntity(int id) {
		return entity.get(id);
	}

	/**
	 * Fügt eine Progressbar hinzu
	 * 
	 * @param progressBar
	 * @param index
	 */
	public void addProgressBar(JProgressBar progressBar, int index) {
		this.listOfJProgressBar[index] = progressBar;
	}

	/**
	 * Gibt die Liste der Progressbars zurück
	 * 
	 * @return
	 */
	public JProgressBar[] getListOfJProgressBar() {
		return listOfJProgressBar;
	}

	/**
	 * Setzt die neuen Hauptgebäude
	 * 
	 * @param mainBuilding1
	 *            Hauptgebäude links
	 * @param mainBuilding2
	 *            Hauptgebäude rechts
	 */
	public void setMainBuildings(MainBuilding mainBuilding1,
			MainBuilding mainBuilding2) {
		mainBuildings[0] = mainBuilding1;
		mainBuildings[1] = mainBuilding2;
	}

	/**
	 * Startet das Spiel
	 * 
	 * @param game
	 */
	public void setGame(Game game) {
		this.game = game;
		cThread.start();
	}

	/**
	 * Bezahlt einen Preis
	 * 
	 * @param price
	 */
	public void payPrice(int[] price) {
		for (int i = 0; i < resources.length; i++) {
			resources[i] -= price[i];
		}
	}

	/**
	 * Gibt einenPreis an den Besitzer zurück
	 * 
	 * @param price
	 */
	public void refundPrice(int[] price) {
		for (int i = 0; i < resources.length; i++) {
			resources[i] += price[i];
		}
	}

	/**
	 * Fügt die übergebenen IDs zu den IDs zu entfernender Einheiten hinzu
	 * 
	 * @param units
	 */
	public void removeUnits(int[] units) {
		for (int i : units) {
			removeQueue.add(i);
		}
	}
}
