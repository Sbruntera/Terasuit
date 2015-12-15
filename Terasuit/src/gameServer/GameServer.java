package gameServer;

import java.util.HashMap;

import server.Server;
import main.Listener;
import main.Writer;
import world.Building;
import world.MainBuilding;
import world.Unit;
import world.WorldConstants;

public class GameServer {
	
	Server server;
	boolean[] players;
	Listener[] listeners;
	Writer[] writers;
	HashMap<Integer, Unit>[] units;
	MainBuilding[] mainBuildings;
	Building[][] buildings;
	int[][] recources;
	
	public GameServer(Listener[] listeners, Writer[] writers, Server server) {
		this.listeners = listeners;
		this.writers = writers;
		this.server = server;
		units = new HashMap[writers.length];
		for (int i = 0; i < units.length; i++) {
			units[i] = new HashMap<Integer, Unit>();
		}
		buildings = new Building[writers.length][WorldConstants.BUILDINGSCOUNT];
		mainBuildings = new MainBuilding[2];
		for (int i = 0; i < units.length; i++) {
			mainBuildings[i] = new MainBuilding();
		}
		//TODO: Startrecourcen festlegen.
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
		
	}

	public void disconnect(int id) {
		players[id] = false;
		//TODO:
	}
}
