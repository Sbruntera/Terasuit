package server;

import java.io.IOException;

public class Main implements Runnable {
	public static void main(String[] args) {
		try {
			new Server(3142);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			new Server(3142);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
