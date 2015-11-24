package main;

import gameServer.GameServer;

import java.util.ArrayList;

public class mainServer {
	
	ArrayList<GameServer> gameList;
	ArrayList<Listener> listeners;
	ArrayList<Writer> writers;
	
	public mainServer() {
		gameList = new ArrayList<GameServer>();
		listeners = new ArrayList<Listener>();
		writers = new ArrayList<Writer>();
	}
}
