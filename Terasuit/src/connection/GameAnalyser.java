package connection;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import server.GameServer;
import world.Building;

/**
 * 
 * @author Simeon
 *
 */
public class GameAnalyser implements Analyser{

	GameServer server;
	short id;

	public GameAnalyser(GameServer server, short id) {
		this.server = server;
		this.id = id;
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
			boolean freeSpace = false;
			int buildingPlace = bytes[1];
			freeSpace = server.hasBuildingAt(id, buildingPlace); // Gebäude an
																	// position
																	// vorhanden
			
			if (freeSpace) {
				server.build(bytes[2], buildingPlace);
			} else {
				if (bytes[2] == 0) {
					server.destroyBuilding(buildingPlace);
				} else {
					Building building = server.getBuildingAt(id, buildingPlace);
					if (building.hasUpgrade()) {
						building.upgrade(bytes[2]);
					}
				}
			}
			break;

		case (33): // Einheit erstellen/bewegen
			switch (bytes[0] & 32) {
			case (0):
				// TODO: Create Unit
				break;
			case (32):
				server.moveUnits(id, getUnits(bytes), ((bytes[0] & 4) >> 2)
						* Double.compare(bytes[0] & 2, 0.5));
				break;
			}
			break;

		case (34): // Spiel verlassen
			server.disconnect(id);
			break;

		case (35): // Chat
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