package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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
	
	ArrayList<Bullet> bulletsToRemove;
	ArrayList<Unit> unitsToRemove;
	
	private Unit[] farestUnits;
	private boolean ended;
	
	private AtomicBoolean tick = new AtomicBoolean(false);

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
		bulletsToRemove = new ArrayList<Bullet>();
		unitsToRemove = new ArrayList<Unit>();
	}

	@Override
	public void run() {
		while (!ended) {
			System.out.println("tick");
			//Kugeln bewegen
			tick.set(true);
			for (Bullet b : bullets) {
				if (b.move()) {
					b.getTarget().dealDamage(b.getDamage());
					bulletsToRemove.add(b);
					if (!b.getTarget().isAlive()) {
						unitsToRemove.add(b.getTarget());
					}
				}
			}
			for (Bullet b : bulletsToRemove) {
				bullets.remove(b);
			}
			
			bulletsToRemove.clear();
			
			for (Unit u : unitsToRemove) {
				units.remove(u.getID());
				unitIDs.remove(u.getID());
			}
			
			unitsToRemove.clear();
			
			// Einheiten bewegen
			Unit unit;
			for (Integer i : unitIDs) {
				unit = units.get(i);
				
				if (unit.getPlayer() < 2) {
					if (unit.getPosition() - unit.getRange() <= farestUnits[0].getPosition() || unit.isRunning()) {
						Bullet b = unit.shoot(farestUnits[0]);
						if (b != null) {
							bullets.add(b);
						}
					} else {
						unit.move();
					}
				} else {
					if (unit.getPosition() + unit.getRange() >= farestUnits[1].getPosition() || unit.isRunning()) {
						Bullet b = unit.shoot(farestUnits[1]);
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
					if (b != null) {
						b.build();
					}
				}
			}
			tick.set(false);
			
		}
	}

	public byte getPosition(Connection connection) {
		for (byte i = 0; i < connections.length; i++) {
			if (connections[i] == connection) {
				return i;
			}
		}
		return -1;
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
			if(connections[i] != null){
				connections[i].sendChatMessage(id, msg);
			}
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
	public void moveUnits(int id, int[] movingUnits, int direction, boolean running) {
		for (int i : movingUnits) {
			if (units.containsKey(i)) {
				if (units.get(i).getPlayer() == id) {
					units.get(i).setDirection(direction, running);
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
	public void build(byte position, int buildingPlace, int id) {
		buildings[position][buildingPlace] = WorldConstants.getBuilding(id, buildingPlace);
	}

	public void destroyBuilding(int buildingPlace, byte position) {
		buildings[position][buildingPlace] = null;
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
