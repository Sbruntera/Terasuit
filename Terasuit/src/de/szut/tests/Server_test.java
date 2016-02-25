package de.szut.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import de.szut.client.logic.ServerConnection;
import de.szut.server.logic.Filter;
import de.szut.server.logic.Map;
import de.szut.server.logic.Server;
/**
 * 
 * @author Rogge
 *
 */
public class Server_test {

	private Server s;
	private ServerConnection sc;
	/**
	 * Startet einen neuen Server Thread sowie eine ServerConnection
	 */
	public Server_test(){
		try {
			new Thread(s = new Server(3142)).start();
			sc = new ServerConnection(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Der Test erstellt eine Lobby und überprüft ob die LobbyList danach 1 anzeigt
	 */
	@Test
	public void test() {
		sc.createGroup((byte) 1, "Lobby", "123");
		s.getLobbylist(new Filter(true, "", Map.Nightsun, 0, 4));
		assertEquals(1,s.getLobbylist(new Filter(true, "", Map.Nightsun, 0, 4)).length );
	}

}
