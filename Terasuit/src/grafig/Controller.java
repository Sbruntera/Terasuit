package grafig;

public class Controller implements Runnable {

	Thread thread;
	int time;

	public Controller(Thread thread, int time) {
		this.thread = thread;
		this.time = time;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (thread.isAlive()) {
			thread.interrupt();
			// TODO: Show Connection Error
		}
	}
}
