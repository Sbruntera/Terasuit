package server;

import java.io.PrintStream;

public class Writer implements Runnable {
	
	private PrintStream writer;
	private String message;
	
	public Writer(PrintStream writer) {
		this.writer = writer;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void run() {
		writer.print(message);
	}
}
