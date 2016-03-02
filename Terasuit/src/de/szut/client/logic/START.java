package de.szut.client.logic;

import java.io.IOException;

import de.szut.client.grafik.Loader;
import de.szut.server.logic.Logging;
import de.szut.server.logic.Server;

public class START {

	public static void main(String[] args) {
		// Server wird gestartet
		try {
			new Thread(new Server(3142)).start();
			Logging.loggingOn = true;
		} catch (IOException e) {
		}
		// Client Lader wird gestartet
		Loader ld = new Loader();
		// Client wird gestartet
		ld.print();
	}
}
