package logic;

import server.Main;
import grafig.Loader;

public class START {

	public static void main(String[] args) {
		System.out.println((char) 128);
		new Thread(new Main()).start();
		Loader ld = new Loader();
		ld.print();
		
		
	}

}
