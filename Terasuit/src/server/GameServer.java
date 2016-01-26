package server;

import java.util.HashMap;

import connection.Connection;
import world.Building;
import world.MainBuilding;
import world.Unit;
import world.WorldConstants;

public class GameServer {

	Server server;
	boolean[] players;
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
		// TODO: Startrecourcen festlegen.
	}
	
	public void broadcast(String msg, short id){
		for(int i=0;i<connections.length;i++){
			connections[i].sendGameChatMessage(id, msg);
		}
	}

	public boolean hasBuildingAt(int id, int pos) {
		return buildings[id][pos] != null;
	}

	public Building getBuildingAt(int id, int pos) {
		return buildings[id][pos];
	}

	public int getRecources(int id, int type) {
		return recources[id][type];
	}

	public void moveUnits(int id, int[] movingUnits, int direction) {
		for (int i : movingUnits) {
			if (units[id].containsKey(i)) {
				units[id].get(i).setDirection(direction);
			}
		}
	}

	public void build(byte b, int buildingPlace) {
		//TODO:
	}

	public void disconnect(int id) {
		players[id] = false;
		// TODO:
	}
}
