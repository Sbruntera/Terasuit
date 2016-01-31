package server;

import java.util.HashMap;

import connection.Connection;
import world.Building;
import world.MainBuilding;
import world.Unit;
import world.WorldConstants;

/**
 * 
 * @author Simeon, Jan-Philipp
 *
 */
public class GameServer {

	Server server;
	Connection[] connections;
	HashMap<Integer, Unit>[] units;
	MainBuilding[] mainBuildings;
	Building[][] buildings;
	int[][] recources;

	public GameServer(Connection[] connections, Server server) {
		this.connections = connections;
		this.server = server;
		units = new HashMap[connections.length];
		for (int i = 0; i < units.length; i++) {
			units[i] = new HashMap<Integer, Unit>();
		}
		buildings = new Building[connections.length][WorldConstants.BUILDINGSCOUNT];
		mainBuildings = new MainBuilding[2];
		for (int i = 0; i < units.length; i++) {
			mainBuildings[i] = new MainBuilding();
		}
		recources = new int[connections.length][];
		// TODO: Startrecourcen festlegen.
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
			connections[i].sendGameChatMessage(id, msg);
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
			if (units[id].containsKey(i)) {
				units[id].get(i).setDirection(direction);
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

	public void disconnect(int id) {
		connections[id] = null;
		// TODO:
	}
}
