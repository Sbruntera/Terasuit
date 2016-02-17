package connection;

import server.Filter;
import server.Lobby;
import server.Map;
import server.Server;

/**
 * 
 * @author Simeon
 *
 */
public class MenuAnalyser implements Analyser {

	Server server;
	Connection connection;
	short id;

	public MenuAnalyser(Server server, Connection connection, short id) {
		this.server = server;
		this.connection = connection;
		this.id = id;
	}

	/**
	 * Analysiert die Nachricht als Menü Nachricht
	 * 
	 * @param input
	 */
	@Override
	public void analyse(String input) {
		byte[] bytes = input.getBytes();
		switch (bytes[0]) {
		case (0): // Stats
			System.out.println("stats");
			// TODO: Get Stats
			break;
		case (1): // logout
			System.out.println("logout");
			connection.loggOut();
			break;
		case (2): // Serverliste
			System.out.println("serverlist");
			Lobby[] lobbyList = server.getLobbylist(getFilter(input
					.substring(1)));
			connection.sendGameList(lobbyList);
			break;
		case (3): // Spiel erstellen
			String[] splitted;
			String password;
			if (bytes.length > 3) {
				System.out.println("create");
				splitted = getSplitString(input, 2);
				password = null;
				if (splitted.length > 1) {
					password = splitted[1];
				}
				server.createLobby(connection, splitted[0],
						password, getMap(bytes[1]));
			}
			break;
		case (4): // Spiel beitreten
			System.out.println("join");
			if (bytes.length > 1) {
				if (server.hasLobby(bytes[1])) {
					server.getLobby(bytes[1]).addPlayer(connection,
								input.substring(2));
				}
			}
			break;
		case (5): // Einloggen
			System.out.println("login");
			if (!connection.isLoggedIn()) {
				System.out.println("loginin");
				splitted = getSplitString(input, 1);
				password = "";
				switch (splitted.length) {
				case (2):
					password = splitted[1];
				case (1):
					if (server.loginClient(splitted[0], password, id)) {
						System.out.println("logininim");
						connection.sendLogin(splitted[0]);
						connection.loggIn(splitted[0]);
					}
					break;
				}
			}
			break;
		case (6): // Registrieren
			System.out.println("register");
			if (!connection.isLoggedIn()) {
				splitted = getSplitString(input, 1);
				if (splitted.length == 4) {
					server.registerClient(splitted[0], splitted[1],
							splitted[2], splitted[3], id);
				}
			}
			break;
		case (7):
			System.out.println("disconnect");
			server.diconnect(id);
			break;
		}
	}

	/**
	 * Entnimmt der Bytefolge den Filter
	 * 
	 * @param input
	 *            : byte[]
	 * @return: Filter
	 */
	private Filter getFilter(String input) {
		byte[] bytes = input.getBytes();
		boolean noPassword;
		if (bytes.length > 0) {
			Map map = null;
			String name = "";
			noPassword = (bytes[0] & 16) != 0;
			int minPlayers = bytes[0] & 243;
			int maxPlayers = bytes[0] & 252;
			if (bytes.length > 1) {
				map = getMap(bytes[1]);
				if (bytes.length > 2) {
					name = input.substring(2);
				}
			}
			return new Filter(noPassword, name, map, minPlayers, maxPlayers);
		}
		return null;
	}

	/**
	 * Entnimmt der Bytefolge die Map
	 * 
	 * @param b
	 * @return
	 */
	private Map getMap(byte b) {
		Map map = null;
		switch (b) {
		case (1):
			map = Map.Nightsun;
			break;
		}
		return map;
	}

	/**
	 * Schneidet aus einem String die ersten beiden Char ab und trennt nach ","
	 * 
	 * @param string
	 * @return
	 */
	private String[] getSplitString(String string, int bytesToCut) {
		return string.substring(bytesToCut, string.length()).split(",");
	}
}
