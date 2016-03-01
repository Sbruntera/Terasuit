//package de.szut.client.ingame;
//
//public class Controller implements Runnable {
//
//	private Funktions func;
//
//	public Controller(Funktions func) {
//		this.func = func;
//	}
//
//	@Override
//	public void run() {
//		while (!func.ended()) {
//			new Thread(func).start();
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//}