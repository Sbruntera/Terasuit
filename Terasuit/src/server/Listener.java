package server;

import java.io.BufferedReader;
import java.io.IOException;

public class Listener extends Thread {
	
	GameServer server;
	BufferedReader reader;
	int id;
	
	public Listener(GameServer server, BufferedReader reader, int id) {
		this.server = server;
		this.reader = reader;
		this.id = id;
	}
	
	public void run() {
		String in;
		try {
			while ((in = reader.readLine()) != null) {
				analyse(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void analyse(String in) {
		byte firstByte = in.getBytes()[0];
		if (firstByte < 128) { //Erstes Bit ist 0, recieved Massage is a Gameplay-Message
			if (firstByte < 64) { //Zweites Bit ist 0, recieved Message is a Building-Order
				boolean freeSpace = false;
				freeSpace = server.hasBuildingAt(id, firstByte >> 2); //Gebäude an position vorhanden
				if (freeSpace) {
					//TODO: Ausgewähltes Gebäude auslesen, Kapital checken, Gebäude bauen 
				}
			} else { //Zweites Bit ist 1, recieved Message is a Unit-Order
				//TODO: Zielposition auslesen. Wie viele Bits benötigt? Dahinter EinheitenIDs auslesen.
			}
		} else { //Erstes Bit ist 1, recieved Message is a Chat- or Surrender-Message
			//TODO: Chat erwünscht? Dann check ob chat oder surrender und danach handeln
		}
	}
}
