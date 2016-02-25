package de.szut.client.logic;

import java.io.IOException;

import de.szut.client.grafik.Loader;
import de.szut.server.logic.Server;

public class START {

	public static void main(String[] args) {
		
		try {
			new Thread(new Server(3142)).start();
		} catch (IOException e) {
		}
		Loader ld = new Loader();
		ld.print();
		
		
	}

}
