package de.szut.server.connection;

import de.szut.server.logic.Lobby;
import de.szut.server.logic.Logging;
import de.szut.server.logic.Server;

/**
 * 
 * @author Simeon
 *
 */
public class LobbyAnalyser implements Analyser {

	private Lobby lobby;
	private short id;
	private Connection connection;
	private Server server;
	

	/**
	 * Initialisiert einen LobbyAnalyser zum analysieren von Lobby-Nachrichten
	 * 
	 * @param lobby Lobby in der die Verbindung ist
	 * @param id ID der Verbindung
	 */
	public LobbyAnalyser(Lobby lobby, short id, Connection connection, Server server) {
		this.lobby = lobby;
		this.id = id;
		this.connection = connection;
		this.server = server;
	}

	/**
	 * Analysiert die Nachricht als Lobby Nachricht
	 * 
	 * @param input
	 *            Zu Analysierende Nachricht
	 */
	@Override
	public void analyse(byte[] input) {
		switch (input[0]) {
		case (7): // Disconnect
			Logging.log(connection.getName() + " hat sich ausgeloggt",
					"STATUSUPDATE");
			connection.loggOut();
			lobby.removePlayer(id, lobby.getPosition(id), false);
			server.diconnect(id);
			break;
		case (16): // Position wechseln
			if (input.length == 2) {
				lobby.switchPlayers(id, (byte) (input[1] >> 2),
						(byte) (input[1] & 3));
			}
			break;
		case (17): // Spiel verlassen
			lobby.removePlayer(id, lobby.getPosition(id), true);
			Logging.log(
					lobby.getPosition(id) + " hat die Lobby " + lobby.getName()
							+ " verlassen", "STATUSUPDATE");
			break;
		case (18): // Spieler kicken
			lobby.removePlayer(id, input[1], true);
			Logging.log(input[1] + " wurde aus der Lobby " + lobby.getName()
					+ " gekickt", "STATUSUPDATE");
			break;
		case (19): // Spiel starten
			lobby.startGame(id);
			Logging.log("Das Spiel der Lobby " + lobby.getName()
					+ " mit der ID " + lobby.getID() + " wurde gestartet",
					"STATUSUPDATE");
			break;
		case (20): // Chat
			lobby.broadcast(split(input, 1), id);
			break;
		default: // Fehlerhafte Nachricht
			Logging.log(
					"Nachricht konnte nicht analysiert werden(Lobby-Fehler: "
							+ (char) input[0] + " " + input.length + ")",
					"ERROR");
			break;
		}
	}

	/**
	 * Trennt vorne eine gewünschte Zahl an Bytes von dem Array ab.
	 * 
	 * @param input
	 *            Zu kürzende Nachricht
	 * @param bytesToSplit
	 *            Zahl der Bytes die abgeschnitten werden sollen
	 * @return gekürzte Nachicht
	 */
	private byte[] split(byte[] input, int bytesToSplit) {
		byte[] output = new byte[input.length - 1];
		for (int i = 0; i < output.length; i++) {
			output[i] = input[i + 1];
		}
		return output;
	}
}
