package connection;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import server.GameServer;

/**
 * 
 * @author Simeon
 *
 */
public class GameAnalyser implements Analyser {

	private GameServer server;
	private short id;
	private byte position;

	public GameAnalyser(GameServer server, short id, byte position) {
		this.server = server;
		this.id = id;
		this.position = position;
	}

	/**
	 * Analysiert die Nachricht als Ingame Nachricht
	 * 
	 * @param input
	 */
	public void analyse(byte[] input) {
		switch (input[0]) {
		case (32): // Gebäude (aus)bauen/zerstören
			if (input.length == 3) {
				if (input[2] < 127) {
					server.build(position, input[1], input[2]);
				} else {
					server.destroyBuilding((byte) (input[1]), position);
				}
			}
			break;
		case (33): // Abbrechen
			if (input.length == 2) {
				server.cancelBuilding(position, (byte) (input[1]));
			}
			break;
		case (34): // Einheit erstellen
			if (input.length == 3) {
				byte unitID = input[1];
				byte buildingPlace = (byte) (input[2]);
				server.createUnit(position, unitID, buildingPlace);
			}
			break;
		case (35): // Einheit bewegen
			if (input.length > 2) {
				server.moveUnits(position, getUnits(input), input[1]);
			}
			break;
		case (36): // Spiel verlassen
			server.disconnect(id);
			break;
		case (20): // Chat
			server.broadcast(castToString(input).substring(1), id);
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
				array[i] = (short) ((Byte.toUnsignedInt(input[i*2 + 2]) << 8) + Byte
						.toUnsignedInt(input[i*2 + 3]));
			}
		}
		return array;
	}

	private String castToString(byte[] input) {
		String s = "";
		for (byte i : input) {
			s += (char) i;
		}
		return s;
	}
}