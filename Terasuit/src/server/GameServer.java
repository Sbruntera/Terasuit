package server;

import java.util.ArrayList;
import java.util.HashMap;

import connection.Connection;
import connection.MenuAnalyser;
import world.Building;
import world.Bullet;
import world.MainBuilding;
import world.Unit;
import world.WorldConstants;

/**
 * 
 * @author Simeon, Jan-Philipp
 *
 */
public class GameServer implements Runnable {

	private Server server;
	private Connection[] connections;
	private HashMap<Integer, Unit> units;
	private ArrayList<Integer> unitIDs;
	private ArrayList<Bullet> bullets;
	private MainBuilding[] mainBuildings;
	private Building[][] buildings;
	private int[][] recources;
	
	private Unit[] farestUnits;
	private boolean ended;

	public GameServer(Connection[] connections, Server server) {
		this.connections = connections;
		this.server = server;
		unitIDs = new ArrayList<Integer>();
		units = new HashMap<Integer, Unit>();
		bullets = new ArrayList<Bullet>();
		buildings = new Building[connections.length][WorldConstants.BUILDINGSCOUNT];
		mainBuildings = new MainBuilding[2];
		for (int i = 0; i < 2; i++) {
			mainBuildings[i] = new MainBuilding();
		}
		recources = new int[connections.length][];
		// TODO: Startrecourcen festlegen.
		farestUnits = new Unit[2];
	}

	@Override
	public void run() {
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		ArrayList<Unit> unitsToRemove = new ArrayList<Unit>();
		while (!ended) {
			//Kugeln bewegen
			for (Bullet b : bullets) {
				if (b.move()) {
					b.getTarget().dealDamage(b.getDamage());
					bulletsToRemove.add(b);
					if (b.getTarget().isDead()) {
						unitsToRemove.add(b.getTarget());
					}
				}
			}
			for (Bullet b : bulletsToRemove) {
				bullets.remove(b);
			}
			
			for (Unit u : unitsToRemove) {
				units.remove(u.getID());
				unitIDs.remove(u.getID());
			}
			
			
			// Einheiten bewegen
			Unit unit;
			for (Integer i : unitIDs) {
				unit = units.get(i);
				if (unit.getPlayer() < 2) {
					if (unit.getPosition() - unit.getRange() <= farestUnits[0].getPosition()) {
						Bullet b = unit.shoot();
						if (b != null) {
							bullets.add(b);
						}
					} else {
						unit.move();
					}
				} else {
					if (unit.getPosition() + unit.getRange() >= farestUnits[1].getPosition()) {
						Bullet b = unit.shoot();
						if (b != null) {
							bullets.add(b);
						}
					} else {
						unit.move();
					}
				}
			}
			
			//Gebäude bauen
			for (Building[] array : buildings) {
				for (Building b : array) {
					b.build();
				}
			}
		}
	}

	/**
	 * Sendet eine Chatnachricht an alle Nutzer
	 * 
	 * @param msg
	 *            : nachricht die gesendet werden soll
	 * @param id
	 *            : ID des Senders
	 */
	public void broadcast(String msg, short id) {
		for (int i = 0; i < connections.length; i++) {
			connections[i].sendChatMessage(id, msg);
		}
	}

	/**
	 * Gibt zurück ob der gewählte Nutzer ein Gebäude an einer gewählten
	 * position hat
	 * 
	 * @param id
	 *            : Nutzer der geprüft werden soll
	 * @param pos
	 *            : Position des Gebäudes
	 * @return
	 */
	public boolean hasBuildingAt(int id, int pos) {
		return buildings[id][pos] != null;
	}

	/**
	 * Gibt das Gebäude zurück das an einer bestimmten position existiert
	 * 
	 * @param id
	 *            : ID des Nutzers
	 * @param pos
	 *            : Position des Gebäudes
	 * @return
	 */
	public Building getBuildingAt(int id, int pos) {
		return buildings[id][pos];
	}

	/**
	 * Gibt die aktuelle Menge einer bestimmten Recource zurück
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	public int getRecources(int id, int type) {
		return recources[id][type];
	}

	/**
	 * Fordert die Einheiten auf in eine bestimmte richtung zu laufen
	 * 
	 * @param id
	 *            : ID des Besitzers der Einheiten
	 * @param movingUnits
	 *            : Einheiten die bewegt werden sollen
	 * @param direction
	 *            : Richtung in die die einheiten laufen sollen 1: rechts 0:
	 *            stehen -1: links
	 */
	public void moveUnits(int id, int[] movingUnits, int direction) {
		for (int i : movingUnits) {
			if (units.containsKey(i)) {
				if (units.get(i).getPlayer() == id) {
					units.get(i).setDirection(direction);
				}
			}
		}
	}

	/**
	 * Erstellt ein Gebäude an einer bestimmten position
	 * 
	 * @param b
	 *            : ID des Gebäudes
	 * @param buildingPlace
	 *            : position des Gebäudes
	 */
	public void build(byte b, int buildingPlace) {
		// TODO:
	}

	public void destroyBuilding(int buildingPlace) {
		// TODO Auto-generated method stub

	}

	public void disconnect(short id) {
		for (Connection c : connections) {
			if (c != null) {
				if (c.getID() == id) {
					c.setAnalyser(new MenuAnalyser(server, c, id));
					c = null;
				} else {
					c.sendPlayerLeftGame(id);
				}
			}
		}
	}
}
