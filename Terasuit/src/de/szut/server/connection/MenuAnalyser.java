package de.szut.server.connection;

import java.util.ArrayList;

import de.szut.server.logic.Filter;
import de.szut.server.logic.Lobby;
import de.szut.server.logic.Logging;
import de.szut.server.logic.Map;
import de.szut.server.logic.Server;

/**
 * 
 * @author Simeon
 *
 */
public class MenuAnalyser implements Analyser {

	Server server;
	Connection connection;
	short id;

	/**
	 * Initialisiert einen MenuAnalyser zum analysieren von Menü-Nachrichten
	 * 
	 * @param server
	 *            Server der die Nachrichten erhalten soll
	 * @param connection
	 *            Verbindung zu der dieser Analyser gehört
	 * @param id
	 *            ID der Verbindung
	 */
	public MenuAnalyser(Server server, Connection connection, short id) {
		this.server = server;
		this.connection = connection;
		this.id = id;
	}

	/**
	 * Analysiert die Nachricht als Menü Nachricht
	 * 
	 * @param input
	 *            Zu analysierende Nachricht
	 */
	@Override
	public void analyse(byte[] input) {
		switch (input[0]) {
		case (0): // Stats
			connection.sendStats();
			Logging.log(connection.getName() + " hat Stats angefordert",
					"REQUEST");
			break;
		case (1): // logout
			Logging.log(connection.getName() + " hat sich ausgeloggt",
					"STATUSUPDATE");
			connection.loggOut();
			break;
		case (2): // Serverliste
			Lobby[] lobbyList = server.getLobbylist(getFilter(input));
			connection.sendGameList(lobbyList);
			break;
		case (3): // Spiel erstellen
			byte[][] splitted;
			String password;
			if (input.length > 3) {
				splitted = getSplitString(input, 2);
				password = "";
				if (splitted.length > 1) {
					password = castToString(splitted[1], 0);
				}
				if (splitted[0].length > 0) {
					server.createLobby(connection,
							castToString(splitted[0], 0), password,
							getMap(input[1]));
					if (password.length() > 0) {
						Logging.log(connection.getName()
								+ " hat eine Lobby mit dem Namen "
								+ castToString(splitted[0], 0)
								+ " dem Password " + password + " auf der Map "
								+ getMap(input[1]) + " erstellt",
								"STATUSUPDATE");
					} else {
						Logging.log(connection.getName()
								+ " hat eine Lobby mit dem Namen "
								+ castToString(splitted[0], 0)
								+ " ohne Password auf der Map "
								+ getMap(input[1]) + " erstellt",
								"STATUSUPDATE");
					}
				}
			}
			break;
		case (4): // Spiel beitreten
			if (input.length > 1) {
				if (server.hasLobby(input[1])) {
					server.getLobby(input[1]).addPlayer(connection,
							castToString(input, 2));
					Logging.log(connection.getName() + " ist der Lobby "
							+ server.getLobby(input[1]).getName()
							+ " mit der ID "
							+ server.getLobby(input[1]).getID()
							+ " beigetreten", "STATUSUPDATE");
				}
			}
			break;
		case (5): // Einloggen
			if (!connection.isLoggedIn()) {
				splitted = getSplitString(input, 1);
				password = "";
				switch (splitted.length) {
				case (2):
					password = castToString(splitted[1], 0);
				case (1):
					if (server.loginClient(castToString(splitted[0], 0),
							password)) {
						connection.sendLogin(castToString(splitted[0], 0));
						connection.loggIn(castToString(splitted[0], 0));
						Logging.log(castToString(splitted[0], 0)
								+ " wurde eingeloggt", "STATUSUPDATE");
					} else {
						connection.sendFailed((byte) 0);
						Logging.log(
								castToString(splitted[0], 0)
										+ " konnte nicht eingeloggt werden(Fehler: Password falsch oder User nicht vorhanden)",
								"ERROR");
					}
					break;
				}
			}
			break;
		case (6): // Registrieren
			if (!connection.isLoggedIn()) {
				splitted = getSplitString(input, 1);
				if (splitted.length == 3) {
					server.registerClient(castToString(splitted[0], 0),
							castToString(splitted[1], 0),
							castToString(splitted[2], 0), "User", id);
				}
			}
			break;
		case (7): //Verbindung wird getrennt
			Logging.log(connection.getName() + "hat das Spiel verlassen(Exit)",
					"STATUSUPDATE");
			server.diconnect(id);
			break;
		default: //Fehlerhafte Nachricht
			Logging.log(
					"Nachricht konnte nicht analysiert werden(Menü-Fehler: "
							+ input[0] + " " + input.length + ")", "ERROR");
			break;
		}
	}
	
	/**
	 * Wandelt ein Byte[] in ein byte[] um
	 * 
	 * @param array
	 *            umzuwandelndes Byte[]
	 * @return Umgewandeltes byte[]
	 */
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
			int minPlayers = input[1] >> 3 & 247;
			int maxPlayers = input[1] & 7;
			if (input.length > 2) {
				map = getMap(input[2]);
				for (int i = 3; i < input.length; i++) {
					name += (char) input[i];
				}
			}
			return new Filter(noPassword, name, map, minPlayers, maxPlayers);
		}
		return null;
	}

	/**
	 * Entnimmt der Bytefolge die Map
	 * 
	 * @param MapID
	 * @return Map
	 */
	private Map getMap(byte b) {
		Map map = null;
		switch (b) {
		case (2):
			map = Map.Nightsun;
			break;
		}
		return map;
	}

	/**
	 * Schneidet von einem byte[] eine gewünschte Zahl an bytes vorne ab und Teilt den Rest nach 0x00 *2
	 * 
	 * @param input Zu teilendes Array
	 * @return Geteiltes Array
	 */
	private byte[][] getSplitString(byte[] input, int bytesToCut) {
		ArrayList<byte[]> outerArray = new ArrayList<byte[]>();
		ArrayList<Byte> array = new ArrayList<Byte>();
		boolean second = false;
		for (int i = bytesToCut; i < input.length; i++) {
			if (input[i] == 0) {
				if (!second) {
					second = true;
				} else {
					outerArray
							.add(toPrimal(array.toArray(new Byte[array.size()])));
					array.clear();
					second = false;
				}
			} else {
				if (second) {
					array.add((byte) 0);
				}
				array.add(input[i]);
				second = false;
			}
		}
		outerArray.add(toPrimal(array.toArray(new Byte[array.size()])));
		return outerArray.toArray(new byte[outerArray.size()][]);
	}

	/**
	 * Wandelt ein byte[] in einen String um und schneidet die ersten bytes nach Wunsch ab
	 * @param input Umzuwandelndes byte[]
	 * @param bytesToCut abzuschneidende Zahl an bytes
	 * @return übergebens byte[] gekürzt als String
	 */
	private String castToString(byte[] input, int bytesToCut) {
		String s = "";
		char[] buffer = new char[input.length - bytesToCut >> 1];
		for (int i = 0; i < buffer.length; i++) {
			int bpos = (i << 1) + bytesToCut;
			char c = (char) (((input[bpos] & 0x00FF) << 8) + (input[bpos + 1] & 0x00FF));
			s += c;
		}
		return s;
	}
}
