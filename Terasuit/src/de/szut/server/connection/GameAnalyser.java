package de.szut.server.connection;

import de.szut.server.logic.GameServer;
import de.szut.server.logic.Logging;
import de.szut.server.logic.Server;

/**
 * 
 * @author Simeon
 *
 */
public class GameAnalyser implements Analyser {

	private GameServer gameServer;
	private short id;
	private byte position;
	private Connection connection;
	private Server server;

	/**
	 * Initialisiert einen GameAnalyser zum analysieren von Ingame-Nachrichten
	 * 
	 * @param server
	 *            Spiel zu dem die Nachrichten gerichtet sind
	 * @param id
	 *            ID der Verbindung
	 * @param position
	 *            Position des Spielers
	 */
	public GameAnalyser(GameServer gameServer, short id, byte position, Connection connection, Server server) {
		this.gameServer = gameServer;
		this.id = id;
		this.position = position;
		this.connection = connection;
		this.server = server;
	}

	/**
	 * Analysier die Nachricht als Ingame Nachricht und Unterichtet den Server
	 * 
	 * @param input
	 *            Zu analysierende Nachricht
	 */
	public void analyse(byte[] input) {
		switch (input[0]) {
		case (7): // Disconnect
			Logging.log(connection.getName() + " hat sich ausgeloggt",
					"STATUSUPDATE");
			connection.loggOut();
			gameServer.disconnect(id);
			server.diconnect(id);
			break;
		case (32): // Gebäude (aus)bauen/zerstören
			if (input.length == 3) {
				if (input[2] < 127) {
					gameServer.build(position, input[1], input[2]);
				} else {
					gameServer.destroyBuilding((byte) (input[1]), position);
				}
			}
			break;
		case (33): // Abbrechen
			if (input.length == 2) {
				gameServer.cancelBuilding(position, (byte) (input[1]));
			}
			break;
		case (34): // Einheit erstellen
			if (input.length == 3) {
				System.out.println("neue Einheit");
				byte unitID = input[1];
				byte buildingPlace = input[2];
				gameServer.createUnit(position, unitID, buildingPlace);
			}
			break;
		case (35): // Einheit bewegen
			if (input.length > 2) {
				gameServer.moveUnits(position, getUnits(input), input[1]);
			}
			break;
		case (36): // Spiel verlassen
			Logging.log(id + " hat das Spiel verlassen(Ingame)", "STATUSUPDATE");
		gameServer.disconnect(id);
			break;
		case (20): // Chat
			gameServer.broadcast(split(input, 1), id);
			break;
		default: // Fehlerhafte Nachricht
			Logging.log(
					"Nachricht konnte nicht analysiert werden(Spiel-Fehler: "
							+ (char) input[0] + " " + input.length + ")",
					"ERROR");
			break;
		}
	}

	/**
	 * Entnimmt einer bytefolge die enthaltenen Einheiten
	 * 
	 * @param input
	 * @return
	 */
	private short[] getUnits(byte[] input) {
		short[] array = null;
		if (input.length >= 4 && input.length % 2 == 0) {
			array = new short[(input.length - 2) / 2];
			for (int i = 0; i < array.length; i++) {
				array[i] = (short) ((Byte.toUnsignedInt(input[i * 2 + 2]) << 8) + Byte
						.toUnsignedInt(input[i * 2 + 3]));
			}
		}
		return array;
	}

	/**
	 * Trennt vorne eine gewünschte Zahl an Bytes von dem Array ab.
	 * 
	 * @param input
	 *            Zu kürzende Nachricht
	 * @param bytesToSplit
	 *            Zahl der Bytes die abgeschnitten werden sollen
	 * @return Gekürzte Nachricht
	 */
	private byte[] split(byte[] input, int bytesToSplit) {
		byte[] output = new byte[input.length - 1];
		for (int i = 0; i < output.length; i++) {
			output[i] = input[i + 1];
		}
		return output;
	}
}