package logic;

import server.Main;
import grafig.Loader;

public class START {

	public static void main(String[] args) {
		new Thread(new Main()).start();
		Loader ld = new Loader();
		ld.print();
		
		
	}

}
