package de.szut.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Test;

import de.szut.client.logic.ServerConnection;
import de.szut.server.connection.Connection;
import de.szut.server.logic.GameServer;
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
	public Game_tests(){
		try {
			new Thread(s = new Server(3142)).start();
			sc = new ServerConnection(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void place_test() {
		sc.createGroup((byte) 1, "Lobby", "123");		
		//TODO Test of Gamefunctions GamerServer.java
	}

}
