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
				int buildingPlace = bytes[1];
				if (bytes[2] >= 0) {
					if (!server.hasBuildingAt(id, buildingPlace)) {
						server.build(position, buildingPlace, bytes[2]);
					}
				} else if(bytes[2] == -1) {
					if (server.hasBuildingAt(id, buildingPlace)) {
						Building building = server.getBuildingAt(position, buildingPlace);
						if (building.hasUpgrade()) {
							building.upgrade();
						}
					}
				} else {
					server.destroyBuilding(buildingPlace, position);
				}
			}
			break;
		case (33): // Einheit erstellen
			
			break;
		case (34): // Einheit bewegen
			server.moveUnits(id, getUnits(bytes), ((bytes[1] & 2) >> 1)
					* Double.compare(bytes[0] & 2, 0.5), (bytes[1] & 1) == 1);
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