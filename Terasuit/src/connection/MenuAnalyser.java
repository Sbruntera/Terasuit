package connection;

import server.Server;

public class MenuAnalyser implements Analyser {

	Server server;
	int id;

	public MenuAnalyser(Server server, int id) {
		this.server = server;
		this.id = id;
	}

	@Override
	public void analyse(String input) {
		byte[] bytes = input.getBytes();
		switch (bytes[0] & 192) {
		case (0): // Stats
			// TODO: Get Stats
			break;
		case (64): // logout
			server.logout(id);
			break;
		case (128): // Serverliste
			// TODO: Serverliste
			break;
		case (192): // Spiel betreten
			// TODO: Spiel beitreten
			break;
		}
	}
}
