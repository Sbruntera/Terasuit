package gameServer;

import java.util.HashMap;

import main.Listener;
import main.Writer;
import world.Building;
import world.Unit;

public class GameServer {
	
	Listener[] listeners;
	Writer[] writers;
	HashMap<Integer, Unit>[] units;
	Building[][] buildings;
	int[][]recources;
	
	public GameServer(Listener[] listeners, Writer[] writers) {
		this.listeners = listeners;
		this.writers = writers;
		units = new HashMap[writers.length];
		for (HashMap<Integer, Unit> b : units) {
			b = new HashMap<Integer, Unit>();
		}
		buildings = new Building[writers.length][0]; //TODO: Gebäudezahl?
		//TODO: Startrecourcen festlegen.
	}
	
	public boolean hasBuildingAt(int id, int pos) {
		return buildings[id][pos] != null;
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
}
