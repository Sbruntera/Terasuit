package tests;

import static org.junit.Assert.*;

import java.io.IOException;

import logic.ServerConnection;

import org.junit.Test;

import server.Filter;
import server.Map;
import server.Server;

public class Server_test {

	Server s;
	ServerConnection sc;
	public Server_test(){
		try {
			new Thread(s = new Server(3142)).start();
			System.out.println("test2");
			System.out.println("test1");
			sc = new ServerConnection(null);
			System.out.println("test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test() {
		sc.createGroup((byte) 1, "Lobby", "123");
		s.getLobbylist(new Filter(true, "", Map.Nightsun, 0, 4));
		//System.out.println(s.getLobbylist(new Filter(true, "", Map.Nightsun, 0, 4)));
		assertEquals(1,s.getLobbylist(new Filter(true, "", Map.Nightsun, 0, 4)).length );
	}

}
