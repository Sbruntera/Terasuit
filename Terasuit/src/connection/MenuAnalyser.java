package connection;

import java.util.ArrayList;

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
	public void analyse(byte[] input) {
		switch (input[0]) {
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
			Lobby[] lobbyList = server.getLobbylist(getFilter(input));
			connection.sendGameList(lobbyList);
			break;
		case (3): // Spiel erstellen
			Byte[][] splitted;
			String password;
			if (input.length > 3) {
				System.out.println("create");
				splitted = getSplitString(input, 2);
				password = "";
				if (splitted.length > 1) {
					password = castToString(toPrimal(splitted[1]));
				}
				server.createLobby(connection, castToString(toPrimal(splitted[0])),
						password, getMap(input[1]));
			}
			break;
		case (4): // Spiel beitreten
			System.out.println("join");
			if (input.length > 1) {
				if (server.hasLobby(input[1])) {
					server.getLobby(input[1]).addPlayer(connection,
							castToString(input).substring(2));
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
					password = castToString(toPrimal(splitted[1]));
				case (1):
					if (server.loginClient(castToString(toPrimal(splitted[0])), password,
							id)) {
						connection.sendLogin(castToString(toPrimal(splitted[0])));
						connection.loggIn(castToString(toPrimal(splitted[0])));
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
					server.registerClient(castToString(toPrimal(splitted[0])),
							castToString(toPrimal(splitted[1])),
							castToString(toPrimal(splitted[2])),
							castToString(toPrimal(splitted[3])), id);
				}
			}
			break;
		case (7):
			System.out.println("disconnect");
			server.diconnect(id);
			break;
		}
	}
	
	private byte[] toPrimal(Byte[] splitted) {
		byte[] bytes = new byte[splitted.length];
		for (int i = 0; i < splitted.length; i++) {
			bytes[i] = splitted[i];
		}
		return bytes;
	}

	/**
	 * Entnimmt der Bytefolge den Filter
	 * 
	 * @param input
	 *            : byte[]
	 * @return: Filter
	 */
	private Filter getFilter(byte[] input) {
		boolean noPassword;
		if (input.length > 1) {
			Map map = null;
			String name = "";
			noPassword = (input[1] & 64) != 0;
			int minPlayers = input[1] & 56;
			int maxPlayers = input[1] & 7;
			if (input.length > 2) {
				map = getMap(input[2]);
				if (input.length > 3) {
					name = input.toString().substring(3);
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
		System.out.println(b + "afoljuf");
		Map map = null;
		switch (b) {
		case (2):
			map = Map.Nightsun;
			break;
		}
		return map;
	}

	/**
	 * Schneidet aus einem String die ersten beiden Char ab und trennt nach ","
	 * 
	 * @param input
	 * @return
	 */
	private Byte[][] getSplitString(byte[] input, int bytesToCut) {
		ArrayList<Byte[]> outerArray = new ArrayList<Byte[]>();
		ArrayList<Byte> array = new ArrayList<Byte>();
		for (int i = bytesToCut; i < input.length; i++) {
			if (input[i] == 1) {
				outerArray.add(array.toArray(new Byte[array.size()]));
				array.clear();
			} else {
				array.add(input[i]);
			}
		}
		outerArray.add(array.toArray(new Byte[array.size()]));
		return outerArray.toArray(new Byte[outerArray.size()][]);
	}

	private String castToString(byte[] input) {
		String s = "";
		for (byte i : input) {
			s += (char) i;
		}
		return s;
	}
}
