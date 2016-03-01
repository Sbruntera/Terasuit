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
	private MainBuilding[] mainBuildings = new MainBuilding[2];
	private double[] resources;
	private int wait = 10;

	ArrayList<Bullet> bulletsToRemove;
	HashMap<Integer, Unit> unitsToRemove;
	private Game game;

	public Funktions() {
		pics.generateAllEntityPictures();
		this.data.createUnitData();
		bulletsToRemove = new ArrayList<Bullet>();
		unitsToRemove = new HashMap<Integer, Unit>();
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
			boolean airUnit, short unitID, Point position) {
		Unit u = cunit.createEntity(field, game, Entitytype, color,
				airUnit, this, unitID, position, pics);
		unitQueue.add(u);
		payPrice(u.getPrice());
	}

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

	public void destroyUserOptions(Panel console) {
		if (this.selectedEntitysID.size() != 0) {
			game.btnAction.createUserUnitOptions(console);
		} else {
			game.btnAction.destroyUserOptions(console);
			setInformationInGame("", "");
			game.setAllJProgressBarVisible(false);
			
			
		}
	}

	private void setInformationInGame(String type, String description) {
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
		bullets = new ArrayList<Bullet>();
		resources = new double[] {50, 50, 50, 0};
		//Controller controller = new Controller(this);
		cThread = new Thread(this);
		ended = false;
		wait = 10;
	}

	@Override
	public void run() {
		while (!ended) {
			long i = System.currentTimeMillis();
			addUnits();
			moveBullets();
			moveUnits();
			buildBuildings();
			generateResources();
			try {
				Thread.sleep(50 - (System.currentTimeMillis() - i));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void addUnits() {
		while (!unitQueue.isEmpty()) {
			Unit u = unitQueue.remove();
			entity.put(u.getEntityNummer(), u);
		}
	}
	
	private void moveBullets() {
		for (Bullet b : bullets) {
			if (b.move()) {
				b.getTarget().dealDamage(b.getDamage());
				bulletsToRemove.add(b);
				if (!b.getTarget().isAlive()) {
					if (b.getTarget() instanceof Unit) {
						unitsToRemove.put(((Unit) b.getTarget()).getEntityNummer(), (Unit) b.getTarget());
					}
				}
			}
		}
		for (Bullet b : bulletsToRemove) {
			game.field.remove(b.getLabel());
			bullets.remove(b);
		}

		bulletsToRemove.clear();

		for (Unit u : unitsToRemove.values()) {
			entity.remove(u.getEntityNummer());
			Container parent = u.getLabel().getParent();
			parent.remove((u.getLabel()));
			parent.repaint();
			parent.revalidate();
		}

		unitsToRemove.clear();
	}

	private void moveUnits() {
		for (Unit e : entity.values()) {
			Unit[] nearestUnits = getNearestUnit(e.getEntityPositionX(),
					(e.getEntitymembership() - 1 & 2) == 2);
			if (e.hasInRange(nearestUnits) && !e.isEntityRunning() && e.isEntityRushLeft() == ((e.getEntitymembership()-1&2)==2)) {
				Bullet b = e.shoot(nearestUnits);
				if (b != null) {
					bullets.add(b);
				}
			} else if (e.hasInRange(new Attackable[] {
					mainBuildings[1-(e.getEntitymembership() - 1 >> 1)], null }) && e.isEntityRushLeft() == ((e.getEntitymembership()-1&2)==2)) {
				Bullet b = e.shoot(new Attackable[] {
						mainBuildings[1-(e.getEntitymembership() - 1 >> 1)], null });
				if (b != null) {
					bullets.add(b);
					game.showBullet(b.getLabel());
				}
			} else if (e.isEntityMove()) {
				if (e.isEntityRushLeft() && e.getEntityPositionX() >= 294) {
					e.setEntityPositionX(e.getEntityPositionX()
							- e.getEntitySpeed()/5.0);
				} else if (!e.isEntityRushLeft() && e.getEntityPositionX() <= 1344) {
					e.setEntityPositionX(e.getEntityPositionX()
							+ e.getEntitySpeed()/5.0);
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

	private void generateResources() {
		resources[0] += 0.04;
		resources[1] += 0.025;
		resources[2] += 0.025;
		resources[3] += 0.01;
		if (wait > 0) {
			wait--;
		} else {
			game.setResources(resources);
		}
	}

	private Unit[] getNearestUnit(double d, boolean right) {
		Unit[] nearestUnits = new Unit[2];
		double[] difference = new double[] { 32767, 32767 };
		for (Unit u : entity.values()) {
			if (Math.abs(u.getEntityPositionX() - d) < difference[Boolean
					.compare(u.isFlyingEntity(), false)]
					&& right != ((u.getEntitymembership() - 1 & 2) == 2)) {
				nearestUnits[Boolean.compare(u.isFlyingEntity(), false)] = u;
				difference[Boolean.compare(u.isFlyingEntity(), false)] = Math
						.abs(u.getEntityPositionX() - d);
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

	public void setMainBuildings(MainBuilding mainBuilding1, MainBuilding mainBuilding2) {
		mainBuildings[0] = mainBuilding1;
		mainBuildings[1] = mainBuilding2;
	}

	public void setGame(Game game) {
		this.game = game;
		cThread.start();
	}

	public void payPrice(int[] price) {
		for (int i = 0; i < resources.length; i++) {
			resources[i] -= price[i];
		}
	}

	public void refundPrice(int[] price) {
		for (int i = 0; i < resources.length; i++) {
			resources[i] += price[i];
		}
	}
}
