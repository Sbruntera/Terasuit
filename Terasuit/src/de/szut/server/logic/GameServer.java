package de.szut.server.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Random;

import de.szut.server.connection.Connection;
import de.szut.server.connection.MenuAnalyser;
import de.szut.server.world.Attackable;
import de.szut.server.world.Building;
import de.szut.server.world.Bullet;
import de.szut.server.world.MainBuilding;
import de.szut.server.world.Unit;
import de.szut.server.world.WorldConstants;

/**
 * 
 * @author Simeon, Jan-Philipp, Alexander Feldmann
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

	private short unitIDCounter;
	private AtomicBoolean ended = new AtomicBoolean(false);

	private AtomicBoolean tick = new AtomicBoolean(false);
	private int defaultSpawnLeft = 300;
	private int defaultSpawnRight = 1200;
	private int defaultSpawnGround = 350;
	private int defaultSpawnAir = 120;

	public GameServer(Connection[] connections, Server server) {
		this.connections = connections;
		this.server = server;
		unitIDs = new ArrayList<Integer>();
		units = new HashMap<Integer, Unit>();
		bullets = new ArrayList<Bullet>();
		buildings = new Building[connections.length][WorldConstants.BUILDINGSCOUNT];
		mainBuildings = new MainBuilding[2];
		for (int i = 0; i < 2; i++) {
			mainBuildings[i] = new MainBuilding((byte) i);
		}
		recources = new int[connections.length][];
		// TODO: Startrecourcen festlegen.
		bulletsToRemove = new ArrayList<Bullet>();
		unitsToRemove = new ArrayList<Unit>();
		unitIDCounter = 1;
		Controller controller = new Controller(this);
		Thread controlThread = new Thread(controller);
		controlThread.start();
	}

	@Override
	public void run() {
		// Kugeln bewegen
		tick.set(true);
		for (Bullet b : bullets) {
			if (b.move()) {
				b.getTarget().dealDamage(b.getDamage());
				bulletsToRemove.add(b);
				if (!b.getTarget().isAlive()) {
					if (b.getTarget() instanceof Unit) {
						unitsToRemove.add((Unit) b.getTarget());
					}
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
		for (Unit unit : units.values()) {
			Unit[] nearestUnits = getNearestUnit(unit.getPosition().x, (unit.getPlayer()&2)==2);
			if (unit.hasInRange(nearestUnits) && !unit.isRunning()) {
				Bullet b = unit.shoot(nearestUnits);
				if (b != null) {
					bullets.add(b);
				}
			} else if (unit.hasInRange(new Attackable[] {
					mainBuildings[unit.getPlayer() >> 1], null })) {
				Bullet b = unit.shoot(new Attackable[] {
						mainBuildings[unit.getPlayer() >> 1], null });
				if (b != null) {
					bullets.add(b);
				}
			} else {
				unit.move();
			}
		}

		// Gebäudestuff
		for (Building[] array : buildings) {
			for (Building b : array) {
				if (b != null) {
					if (!b.isFinished()) {
						if (b.build()) {
							for (Connection c : connections) {
								if (c != null) {
									c.sendCreateOrUpgradeBuilding(
											b.getPlayer(), b.getSlotID(),
											b.getType());
								}
							}
						}
					} else {
						Unit u = b.create();
						if (u != null) {
							units.put((int) u.getID(), u);
							for (Connection c : connections) {
								if (c != null) {
									c.sendCreateUnit(u.getPlayer(),
											u.getPosition(), u.getType(),
											u.getID());
								}
							}
						}
					}
				}
			}
		}

		if (!mainBuildings[0].isAlive() && !mainBuildings[1].isAlive()) {
			for (Connection c : connections) {
				if (c != null) {
					c.sendGameEnded(false);
				}
			}
		} else if (!mainBuildings[0].isAlive()) {
			for (int i = 0; i < connections.length; i++) {
				connections[i].sendGameEnded((connections.length - 1) / 2 > 1);
			}
		} else if (!mainBuildings[1].isAlive()) {
			for (int i = 0; i < connections.length; i++) {
				connections[i].sendGameEnded((connections.length - 1) / 2 < 0);
			}
		}
		tick.set(false);
	}

	private Unit[] getNearestUnit(int i, boolean right) {
		Unit[] nearestUnits = new Unit[2];
		int[] difference = new int[] { -32768, -32768 };
		for (Unit u : units.values()) {
			if (Math.abs(u.getPosition().x - i) < difference[Boolean.compare(
					u.isFlying(), false)] && right != ((u.getPlayer()&2)==2)) {
				nearestUnits[Boolean.compare(u.isFlying(), false)] = u;
				difference[Boolean.compare(u.isFlying(), false)] = Math.abs(u
						.getPosition().x - i);
			}
		}
		return nearestUnits;
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
		byte position = 0;
		for (byte i = 0; i < connections.length; i++) {
			if (connections[i] != null) {
				if (connections[i].getID() == id) {
					position = i;
				}
			}
		}
		for (int i = 0; i < connections.length; i++) {
			if (connections[i] != null) {
				connections[i].sendChatMessage(position, msg);
			}
		}
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
	public void moveUnits(byte id, short[] movingUnits, byte direction) {
		for (int i : movingUnits) {
			if (units.containsKey(i)) {
				if (units.get(i).getPlayer() == id) {
					units.get(i).setDirection(direction);
				}
			}
		}
		
		for (Connection c : connections) {
			if (c != null) {
				c.sendMoveUnit(id, direction, movingUnits);
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
	 * Erstellt ein Gebäude an einer bestimmten position
	 * 
	 * @param b
	 *            : ID des Gebäudes
	 * @param buildingPlace
	 *            : position des Gebäudes
	 */
	public void build(byte position, byte buildingPlace, byte id) {
		if (buildings[position][buildingPlace] == null) {
			buildings[position][buildingPlace] = WorldConstants.getBuilding(id,
					buildingPlace, position);
		} else if (buildings[position][buildingPlace].getUpgrade() == id) {
			buildings[position][buildingPlace].upgrade();
		} else {
			return;
		}
		for (Connection c : connections) {
			if (c != null) {
				c.sendStartCreateOrUpgradeBuilding(position,
						buildingPlace, id);
			}
		}
	}

	public void destroyBuilding(byte buildingPlace, byte position) {
		buildings[position][buildingPlace] = null;
		for	(Connection c : connections) {
			if (c != null) {
				c.sendDestroyBuilding(position, buildingPlace);
			}
		}
	}

	public void upgradeBuilding(byte position, byte buildingPlace) {
		if (hasBuildingAt(position, buildingPlace)) {
			if (buildings[position][buildingPlace].hasUpgrade()) {
				buildings[position][buildingPlace].upgrade();
				for (Connection c : connections) {
					if (c != null) {
						c.sendCreateOrUpgradeBuilding(position, buildingPlace,
								(byte) 0);
					}
				}
			}
		}
	}

	public void cancelBuilding(byte player, byte position) {
		if (buildings[player][position] != null) {
			if (!buildings[player][position].isFinished()) {
				buildings[player][position] = null;
				for (Connection c : connections) {
					if (c != null) {
						c.sendCancel(player, position);
					}
				}
			}
		}
	}

	public void createUnit(byte playerPosition, byte id, byte buildingPlace) {
		Random generator = new Random();
		if (playerPosition < connections.length && playerPosition >= 0
				&& buildingPlace < WorldConstants.BUILDINGSCOUNT
				&& buildingPlace >= 0) {
			int xPosition;
			int yPosition;
			if (WorldConstants.isFlying(id)) {
				if ((playerPosition & 2) == 0) {
					xPosition = defaultSpawnLeft + generator.nextInt(70);
					yPosition = defaultSpawnAir + generator.nextInt(150);
				} else {
					xPosition = defaultSpawnRight + generator.nextInt(70);
					yPosition = defaultSpawnAir + generator.nextInt(150);
				}
			} else {
				if ((playerPosition & 2) == 0) {
					xPosition = defaultSpawnLeft + generator.nextInt(70);
					yPosition = defaultSpawnGround + generator.nextInt(150);
				} else {
					xPosition = defaultSpawnRight + generator.nextInt(70);
					yPosition = defaultSpawnGround + generator.nextInt(150);
				}
			}
			if (buildings[playerPosition][buildingPlace].createUnit(id,
					unitIDCounter, new Point(xPosition, yPosition))) {
				unitIDCounter++;
				
				connections[playerPosition].sendGenerateUnit(id, buildingPlace);
			}
		}
	}

	public void disconnect(short id) {
		boolean found = false;
		for (byte i = 0; i < connections.length; i++) {
			if (connections[i] != null) {
				if (connections[i].getID() == id) {
					connections[i].setAnalyser(new MenuAnalyser(server, connections[i], id));
					connections[i] = null;
				} else {
					connections[i].sendPlayerLeftGame(i);
					found = true;
				}
			}
		}
		if(!found){
			ended.set(true);
		}
	}

	public boolean ended() {
		return ended.get();
	}
}
