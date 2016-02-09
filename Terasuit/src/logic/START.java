package logic;

import java.io.IOException;

import server.Server;
import grafig.Loader;

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
