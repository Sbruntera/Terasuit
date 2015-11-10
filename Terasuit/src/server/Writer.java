package server;

import java.io.PrintWriter;

public class Writer extends Thread {
	
	private PrintWriter writer;
	private int id;
	
	public Writer(PrintWriter writer, int id) {
		this.writer = writer;
		this.id = id;
	}
	
	public void run() {
		try {
			
		}
		catch(IOException e) {
			
		}
	}
}
