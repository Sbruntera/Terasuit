package logic;

import java.io.IOException;

import server.Server;
import grafig.Loader;

public class START {

	public static void main(String[] args) {
		try {
			new Thread(new Server(3142)).start();
			Loader ld = new Loader();
			ld.print();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
