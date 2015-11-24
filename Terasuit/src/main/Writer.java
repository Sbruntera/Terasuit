package main;

import java.io.PrintWriter;

public class Writer extends Thread {
	
	private PrintWriter writer;
	private String message;
	
	public Writer(PrintWriter writer) {
		this.writer = writer;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void run() {
		writer.print(message);
	}
}
