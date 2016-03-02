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
	private ArrayList<Bullet> bullets;
	private MainBuilding[] mainBuildings;
	private Building[][] buildings;
	private double[][] resources;
	private int[] unitkills;

	ArrayList<Bullet> bulletsToRemove;
	ArrayList<Short> unitsToRemove;

	private short unitIDCounter;
	private AtomicBoolean ended = new AtomicBoolean(false);

	private AtomicBoolean tick = new AtomicBoolean(false);
	private int defaultSpawnLeft = 300;
	private int defaultSpawnRight = 1200;
	private int defaultSpawnGround = 350;
	private int defaultSpawnAir = 120;

	/**
	 * Initialisiert einen GameServer mit einer übergebenen Liste an Spielern
	 * und dem Server
	 * 
	 * @param connections
	 *            Liste der Spielerverbindungen
	 * @param server
	 */
	public GameServer(Connection[] connections, Server server) {
		this.connections = connections;
		this.server = server;
		units = new HashMap<Integer, Unit>();
		bullets = new ArrayList<Bullet>();
		buildings = new Building[connections.length][WorldConstants.BUILDINGSCOUNT];
		mainBuildings = new MainBuilding[2];
		for (int i = 0; i < 3; i += 2) {
			mainBuildings[i >> 1] = new MainBuilding((byte) i);
		}
		resources = new double[connections.length][];
		for (int i = 0; i < resources.length; i++) {
			resources[i] = WorldConstants.getStartResources();
		}
		bulletsToRemove = new ArrayList<Bullet>();
		unitsToRemove = new ArrayList<Short>();
		unitIDCounter = 1;
		unitkills = new int[4];
		Thread controlThread = new Thread(this);
		controlThread.start();
	}

	@Override
	public void run() {
		while (!ended.get()) {
			// Kugeln bewegen
			long waitTimer = System.currentTimeMillis();
			tick.set(true);
			for (Bullet b : bullets) {
				if (b.move()) {
					b.getTarget().dealDamage(b.getDamage());
					bulletsToRemove.add(b);
					if (!b.getTarget().isAlive()) { // Ziel gestorben
						if (b.getTarget() instanceof Unit && !b.getTarget().wasAlreadyDead()) { // Einheit
							b.getTarget().setAlreadyDead();
							unitsToRemove.add(((Unit) b.getTarget()).getID());
							unitkills[b.getPlayer()] += 1;
						} else if (!(b.getTarget() instanceof Unit)) { // Hauptgebäude
							if (!ended.get()) {
								ended.set(true);
								for (int i = 0; i < connections.length; i++) {
									if (connections[i] != null) {
										connections[i].sendGameEnded((b.getTarget()
												.getPlayer() >> 1) != (i >> 1));
										if (connections[i].isLoggedIn()) {
											server.writeStats(
													connections[i].getName(),
													unitkills[i],
													(b.getTarget().getPlayer() >> 1) != (i >> 1),
													b.getPlayer() == i);
										}
									}
								}
							}
						}
					}
				}
			}
			for (Bullet b : bulletsToRemove) {
				bullets.remove(b);
			}

			bulletsToRemove.clear();

			for (Connection c : connections) {
				if (c != null) {
					c.sendUnitDied(unitsToRemove
							.toArray(new Short[unitsToRemove.size()]));
				}
			}

			for (Short s : unitsToRemove) {
				units.remove(new Integer(s.shortValue()));
			}

			unitsToRemove.clear();

			// Einheiten bewegen
			for (Unit u : units.values()) {
				Unit[] nearestUnits = getNearestUnit(u.getXPosition(),
						(u.getPlayer() & 2) == 2);
				if (u.hasInRange(nearestUnits)
						&& !u.isRunning()
						&& (u.getDirection() == -1) == ((u.getPlayer() & 2) == 2)) {
					Bullet b = u.shoot(nearestUnits);
					if (b != null) {
						bullets.add(b);
					}
				} else if (u.hasInRange(new Attackable[] {
						mainBuildings[1 - (u.getPlayer() >> 1)], null })
						&& (u.getDirection() == -1) == ((u.getPlayer() & 2) == 2)) {
					Bullet b = u.shoot(new Attackable[] {
							mainBuildings[1 - (u.getPlayer() >> 1)], null });
					if (b != null) {
						bullets.add(b);
					}
				} else {
					u.move();
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
												u.getXPosition(),
												u.getYPosition(), u.getType(),
												u.getID());
									}
								}
							}
						}
					}
				}
			}

			// Resources auffüllen
			double[] gainedResources = WorldConstants.getResources();
			for (double[] array : resources) {
				for (int i = 0; i < array.length; i++) {
					array[i] += gainedResources[i];
				}
			}
			tick.set(false);
			try {
				Thread.sleep(100 - (System.currentTimeMillis() - waitTimer));
			} catch (Exception e) {
			}

		}
	}

	/**
	 * Sucht zu einer Position die nähesten Gegner
	 * 
	 * @param position
	 *            Position zu der gesucht werden soll
	 * @param right
	 *            Seite zu der die Einheit gehört
	 * @return Die nähesten Einheiten, position0 = Boden; position 1= Fliegend
	 */
	private Unit[] getNearestUnit(double position, boolean right) {
		Unit[] nearestUnits = new Unit[2];
		double[] difference = new double[] { 32767, 32767 };
		for (Unit u : units.values()) {
			if (Math.abs(u.getXPosition() - position) < difference[Boolean
					.compare(u.isFlying(), false)]
					&& right != ((u.getPlayer() & 2) == 2)) {
				nearestUnits[Boolean.compare(u.isFlying(), false)] = u;
				difference[Boolean.compare(u.isFlying(), false)] = Math.abs(u
						.getXPosition() - position);
			}
		}
		return nearestUnits;
	}

	/**
	 * Gibt die position des übergebenen Spielers zurück
	 * 
	 * @param connection
	 *            Spieler dessen ID gesucht wird
	 * @return Position des Spielers
	 */
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
	 * @param bs
	 *            Nachricht die gesendet werden soll
	 * @param id
	 *            ID des Senders
	 */
	public void broadcast(byte[] bs, short id) {
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
				connections[i].sendChatMessage(position, bs);
			}
		}
	}

	/**
	 * Fordert die Einheiten auf in eine bestimmte Richtung zu laufen
	 * 
	 * @param id
	 *            ID des Besitzers der Einheiten
	 * @param movingUnits
	 *            Einheiten die bewegt werden sollen
	 * @param direction
	 *            Richtung in die die einheiten laufen sollen 1: rechts 0:
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
	 *            Nutzer der geprüft werden soll
	 * @param pos
	 *            Position des Gebäudes
	 * @return
	 */
	public boolean hasBuildingAt(int id, int pos) {
		return buildings[id][pos] != null;
	}

	/**
	 * Erstellt ein Gebäude an einer bestimmten position
	 * 
	 * @param b
	 *            ID des Gebäudes
	 * @param buildingPlace
	 *            position des Gebäudes
	 */
	public void build(byte position, byte buildingPlace, byte id) {
		if (buildings[position][buildingPlace] == null) {
			Building building = WorldConstants.getBuilding(id, buildingPlace,
					position);
			if (payPrice(position, building.getPrice(0))) {
				buildings[position][buildingPlace] = building;
				connections[position].sendStartCreateOrUpgradeBuilding(
						position, buildingPlace, id);
			}
		} else if (buildings[position][buildingPlace].getUpgrade() == id) {
			if (payPrice(position,
					buildings[position][buildingPlace].getPrice())) {
				buildings[position][buildingPlace].upgrade();
				connections[position].sendStartCreateOrUpgradeBuilding(
						position, buildingPlace, id);
			}
		} else {
			return;
		}
	}

	/**
	 * Zerstört das Gebäude an der Position
	 * 
	 * @param buildingPlace
	 *            Position des Gebäudes
	 * @param position
	 *            Position des Bsesitzers
	 */
	public void destroyBuilding(byte buildingPlace, byte position) {
		buildings[position][buildingPlace] = null;
		for (Connection c : connections) {
			if (c != null) {
				c.sendDestroyBuilding(position, buildingPlace);
			}
		}
	}

	/**
	 * Bricht die Ererichtung eines Gebäudes ab.
	 * 
	 * @param player
	 *            Spieler dem das Gebäude gehört
	 * @param position
	 *            Position des Gebäudes
	 */
	public void cancelBuilding(byte player, byte position) {
		if (buildings[player][position] != null) {
			if (!buildings[player][position].isFinished()) {
				getResources(player, buildings[player][position].getPrice(-1));
				if (buildings[player][position].getLevel() == 0) {
					buildings[player][position] = null;
				} else {
					buildings[player][position].cancelUpgrade();
				}
				for (Connection c : connections) {
					if (c != null) {
						c.sendCancel(player, position);
					}
				}
			}
		}
	}

	/**
	 * erstellt eine Einheit
	 * 
	 * @param playerPosition
	 *            position des Spielers
	 * @param id
	 *            Typ der Einheit
	 * @param buildingPlace
	 *            Position des produzierenden gebäudes
	 */
	public void createUnit(byte playerPosition, byte id, byte buildingPlace) {
		Random generator = new Random();
		if (playerPosition < connections.length && playerPosition >= 0
				&& buildingPlace < WorldConstants.BUILDINGSCOUNT
				&& buildingPlace >= 0) {
			if (payPrice(playerPosition, WorldConstants.getUnitPrice(id))) {
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

					connections[playerPosition].sendGenerateUnit(id,
							buildingPlace);
				}
			}
		}
	}

	/**
	 * Entfernt einen Spieler aus dem Spiel
	 * @param id
	 */
	public void disconnect(short id) {
		int number = -1;
		for (int i = 0; i < connections.length; i++) {
			if (connections[i] != null && connections[i].getID() == id) {
				number = i;
			}
		}
		boolean found = false;
		if (number != -1) {
			for (Connection c : connections) {
				if (c != null) {
					if (c.getID() == id) {
						c.setAnalyser(new MenuAnalyser(server, c, id));
						c = null;
					} else {
						c.sendPlayerLeftGame((byte) number);
						found = true;
					}
				}
			}
		}
		if (!found) {
			ended.set(true);
		}
	}

	/**
	 * Gibt zurück ob das Spiel vorbei ist
	 * @return
	 */
	public boolean ended() {
		return ended.get();
	}

	/**
	 * Bezahlt einen Preis für einen Spieler
	 * @param player Spieler der zahlt
	 * @param price Preis der gezahlt wird
	 * @return ob die Zahlung möglich war
	 */
	private boolean payPrice(int player, int[] price) {
		boolean possible = true;
		for (int i = 0; i < resources[player].length; i++) {
			if (resources[player][i] < price[i]) {
				possible = false;
			}
		}
		if (possible) {
			for (int i = 0; i < resources[player].length; i++) {
				resources[player][i] -= price[i];
			}
		}
		return possible;
	}

	/**
	 * Gibt dem Spieler die Rohstoffe zurück 
	 * @param player Spieler der die rohstoffe erhalten soll
	 * @param price Rohstoffe die übergeben werden sollen
	 */
	private void getResources(int player, int[] price) {
		for (int i = 0; i < resources[player].length; i++) {
			resources[player][i] += price[i];
		}

	}
}
