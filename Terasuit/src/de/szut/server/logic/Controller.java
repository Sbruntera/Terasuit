package de.szut.server.logic;

public class Controller implements Runnable {

	private GameServer gameServer;

	public Controller(GameServer gameServer) {
		this.gameServer = gameServer;
	}

	@Override
	public void run() {
		while (!gameServer.ended()) {
			new Thread(gameServer).start();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}