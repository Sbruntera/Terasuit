package server;

import java.util.ArrayList;

public class GameServer {
	
	Listener[] listeners;
	Writer[] writers;
	ArrayList<Unit> units;
	ArrayList<Building> building;
	
	public GameServer(Listener[] listeners, Writer[] writers) {
		this.listeners = listeners;
		this.writers = writers;
		units = new ArrayList<Unit>();
		buildings = new ArrayList<Building>();
	}
	
	
}
