package connection;

import server.Filter;
import server.Lobby;
import server.Map;
import server.Server;

public class MenuAnalyser implements Analyser {

	Server server;
	Connection connection;
	int id;
	boolean logged;

	public MenuAnalyser(Server server, Connection connection, int id, boolean logged) {
		this.server = server;
		this.connection = connection;
		this.id = id;
		this.logged = logged;
	}

	@Override
	public void analyse(String input) {
		byte[] bytes = input.getBytes();
		System.out.println(bytes.length);
		switch (bytes[0] & 224) {
		case (0): // Stats
			// TODO: Get Stats
			break;
		case (32): // logout
			if (logged) {
				logged = false;
				connection.setName(null);
			}
			break;
		case (64): // Serverliste
			Lobby[] lobbyList = server.getLobbylist(getFilter(input));
			connection.sendGameList();
			break;
		case (96): // Spiel erstellen
			String[] splitted = getSplitString(input);
			server.createLobby(connection, splitted[0].substring(2, splitted[0].length()), splitted[1], getMap(bytes[1]));
			break;
		case(128): //Spiel beitreten
			connection.joinLobby(bytes[1], input.substring(2, input.length()));
			break;
		case (160): // Einloggen
			if (!logged) {
				splitted = getSplitString(input);
				if (server.loginClient(splitted[0].substring(1, splitted[0].length()), splitted[1], id)) {
					connection.sendLogin();
					logged = true;
				}
			}
			break;
		case (192): // Registrieren
			if (!logged) {
				splitted = getSplitString(input);
				server.registerClient(splitted[0].substring(1, splitted[0].length()), splitted[1], splitted[2],
						splitted[3], id);
			}
			break;
		case (224):
			server.diconnect(id);
			break;
		}
	}

	private Filter getFilter(String input) {
		byte[] bytes = input.getBytes();
		boolean noPassword;
		if ((bytes[0] & 16) == 0) {
			noPassword = false;
		}
		else {
			noPassword = true;
		}
		int minPlayers = bytes[0] & 243;
		int maxPlayers = bytes[0] & 252;
		Map map = getMap(bytes[1]);

		return new Filter(noPassword, input.substring(1, input.length()), map, minPlayers, maxPlayers);
	}
	
	private Map getMap(byte b) {
		Map map = null;
		switch (b) {
		case(1):
			map = Map.Nightsun;
			break;
		}
		return map;
	}
	
	private String[] getSplitString(String string) {
		return string.substring(1, string.length()).split(",");
	}
}
