package server;

import java.io.BufferedReader;
import java.io.IOException;

public class Listener extends Thread {
	
	BufferedReader reader;
	int id;
	
	public Listener(GameServer server, BufferedReader reader, int id) {
		this.reader = reader;
		this.id = id;
	}
	
	public void run() {
		try {
			String in = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (in.
		}
	}
}
