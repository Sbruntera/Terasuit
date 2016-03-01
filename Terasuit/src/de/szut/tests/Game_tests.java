package de.szut.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Test;

import de.szut.client.grafik.Loader;
import de.szut.client.logic.ServerConnection;
import de.szut.server.connection.Connection;
import de.szut.server.logic.Filter;
import de.szut.server.logic.GameServer;
import de.szut.server.logic.Map;
import de.szut.server.logic.Server;
/**
 * Macht noch absolut garnichts
 * 
 * @author Rogge
 *
 */
@SuppressWarnings("unused")
public class Game_tests {

	private Server s;
	private ServerConnection sc;
	private Loader ld;
	public Game_tests(){
		try {
			new Thread(s = new Server(3142)).start();
		} catch (IOException e) {
		}
		ld = new Loader();
		
	}
	@Test
	public void place_test() {
		ld.connection.createGroup(2, "Lobby", "123");
		ld.connection.startGame();
		ld.connection.createBuilding(1, "Outpost");
	}

}
