package connection;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import server.GameServer;
import world.Building;

public class GameAnalyser {

	GameServer server;
	int id;

	public GameAnalyser(GameServer server, int id) {
		this.server = server;
		this.id = id;
	}

	public void analyse(String input) {
		byte[] bytes = input.getBytes();
		switch (bytes[0] & 192) {
		case (0): // Gebäude (aus)bauen
			boolean freeSpace = false;
			int buildingPlace = bytes[0] >> 3;
			freeSpace = server.hasBuildingAt(id, buildingPlace); // Gebäude an
																	// position
																	// vorhanden
			switch (bytes[0] & 32) {
			case (0): // Gebäude bauen
				if (freeSpace) {
					// TODO: Ausgewähltes Gebäude auslesen, Kapital checken,
					// Gebäude bauen
				}
			case (32): // Gebäude ausbauen
				if (!freeSpace) {
					Building building = server.getBuildingAt(id, buildingPlace);
					if (building.hasUpgrade()) {
						// TODO: Kapital checken, Gebäude bauen
					}
				}
				break;
			}
			break;

		case (64): // Einheit erstellen/bewegen
			switch (bytes[0] & 32) {
			case (0):
				// TODO: Create Unit
				break;
			case (32):
				server.moveUnits(id, getUnits(bytes), (bytes[0] & 6));
				break;
			}
			break;

		case (128): // Spiel verlassen
			server.disconnect(id);
			break;

		case (192): // Chat
			// TODO: Chat erwünscht?
			break;
		}
	}

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