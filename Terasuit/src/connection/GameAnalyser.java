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
	public void analyse(String input) {
		byte[] bytes = input.getBytes();
		switch (bytes[0]) {
		case (32): // Gebäude (aus)bauen
			if (bytes.length == 3) {
				byte buildingPlace = bytes[1];
				if (bytes[2] < 126) {
					System.out.println("af");
					if (!server.hasBuildingAt(position, buildingPlace)) {
						server.build(position, buildingPlace, bytes[2]);
					}
				} else if (bytes[2] == 127) {
					server.upgradeBuilding(position, buildingPlace);

				} else {
					server.destroyBuilding(buildingPlace, position);
				}
			}
			break;
		case (33): // Einheit erstellen
			if (bytes.length == 3) {
				byte unitID = bytes[1];
				byte buildingPlace = bytes[2];
				server.createUnit(position, unitID, buildingPlace);
			}
			break;
		case (34): // Einheit bewegen
			if (bytes.length > 2) {
				server.moveUnits(id, getUnits(bytes), ((bytes[2] & 2) >> 1)
						* Double.compare(bytes[1] & 2, 0.5),
						(bytes[2] & 1) == 1);
			}
			break;
		case (35): // Spiel verlassen
			System.out.println("leave");
			server.disconnect(id);
			break;

		case (36): // Chat
			server.broadcast(input, id);
			break;
		}
	}

	/**
	 * Entnimmt einer bytefolge die enthaltenen Einheiten
	 * 
	 * @param bytes
	 * @return
	 */
	private int[] getUnits(byte[] bytes) {
		byte[] bytes1 = new byte[bytes.length - 1];
		for (int i = 2; i <= bytes.length; i++) {
			bytes1[i - 2] = bytes[i];
		}
		IntBuffer intBuffer = ByteBuffer.wrap(bytes1)
				.order(ByteOrder.BIG_ENDIAN).asIntBuffer();
		int[] array = new int[intBuffer.remaining()];
		return intBuffer.get(array).array();
	}
}