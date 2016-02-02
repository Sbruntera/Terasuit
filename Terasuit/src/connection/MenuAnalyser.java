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
		System.out.println(bytes.length);
		switch (bytes[0] & 224) {
		case (0): // Stats
			// TODO: Get Stats
			break;
		case (32): // logout
			connection.setName(null);
			break;
		case (64): // Serverliste
			Lobby[] lobbyList = server.getLobbylist(getFilter(input));
			connection.sendGameList(lobbyList);
			break;
		case (96): // Spiel erstellen
			String[] splitted = getSplitString(input);
			server.createLobby(connection,
					splitted[0].substring(2, splitted[0].length()),
					splitted[1], getMap(bytes[1]));
			connection.sendGameJoin(server.getLobby(bytes[1]));
			break;
		case (128): // Spiel beitreten
			server.getLobby(bytes[1]).addPlayer(connection);
			connection.sendGameJoin(server.getLobby(bytes[1]));
			break;
		case (160): // Einloggen
			if (connection.getName() == null) {
				splitted = getSplitString(input);
				if (server.loginClient(
						splitted[0].substring(1, splitted[0].length()),
						splitted[1], id)) {
					connection.sendLogin();
					connection.setName(splitted[0].substring(1,
							splitted[0].length()));
				}
			}
			break;
		case (192): // Registrieren
			if (connection.getName() == null) {
				splitted = getSplitString(input);
				server.registerClient(
						splitted[0].substring(1, splitted[0].length()),
						splitted[1], splitted[2], splitted[3], id);
			}
			break;
		case (224):
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
		if ((bytes[0] & 16) == 0) {
			noPassword = false;
		} else {
			noPassword = true;
		}
		int minPlayers = bytes[0] & 243;
		int maxPlayers = bytes[0] & 252;
		Map map = getMap(bytes[1]);

		return new Filter(noPassword, input.substring(1, input.length()), map,
				minPlayers, maxPlayers);
	}

	/**
	 * Entnimmt der Bytefolge die Map
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
	 * Schneidet aus einem String den ersten String ab und trennt nach ","
	 * @param string
	 * @return
	 */
	private String[] getSplitString(String string) {
		return string.substring(1, string.length()).split(",");
	}
}
