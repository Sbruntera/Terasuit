package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import server.GameServer;
import server.Server;

public class Game_tests {

	public Game_tests(){
		try {
			GameServer gs = new GameServer(null, new Server(3214));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void place_test() {
		//Test of Gamefunctions GamerServer.java
	}

}
