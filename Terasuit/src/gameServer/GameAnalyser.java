package gameServer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import main.Analyser;

public class GameAnalyser implements Analyser {
	
	GameServer server;
	int id;
	
	public GameAnalyser(GameServer server, int id) {
		this.server = server;
		this.id = id;
	}
	
	public void analyse(String in) {
		byte[] bytes = in.getBytes();
		if (bytes[0] < 128) { //Erstes Bit ist 0, recieved Massage is a Gameplay-Message
			if (bytes[0] < 64) { //Zweites Bit ist 0, recieved Message is a Building-Order
				boolean freeSpace = false;
				freeSpace = server.hasBuildingAt(id, bytes[0] >> 3); //Gebäude an position vorhanden
				if (bytes[0] < 32) { //Drittes Bit ist 0, recieved Message is a new Building
					if (freeSpace) {
						//TODO: Ausgewähltes Gebäude auslesen, Kapital checken, Gebäude bauen 
					}
				} else { //Drittes Bit ist 1, recieved Message is a Upgrade-Order
					if (!freeSpace) {
						//TODO: Kapital checken, Gebäude bauen
					}
				}
			} else { //Zweites Bit ist 1, recieved Message is a Unit-Order
				if (bytes[0] < 32) { //Drittes Bit ist 0, Unit creation
					//TODO: 
				} else { //Unit movement
					server.moveUnits(id, getUnits(bytes), ((int) (bytes[0] >> 2) - 12));
				}
			}
		} else { //Erstes Bit ist 1, recieved Message is a Chat- or Surrender-Message
			if (bytes[0] < 64) { //Zweites Bit 0 Spiel verlassen
				
			} else { //Chat
				//TODO: Chat erwünscht? Dann check ob chat oder surrender und danach handeln
			}
		}
	}
	
	private int[] getUnits(byte[] bytes) {
		IntBuffer intBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
		int[] array = new int[intBuffer.remaining()];
		return intBuffer.get(array).array();
	}
}