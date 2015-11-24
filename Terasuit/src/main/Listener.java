package main;

import java.io.BufferedReader;
import java.io.IOException;

public class Listener extends Thread {
	
	BufferedReader reader;
	int id;
	Analyser analyser;
	
	public Listener(BufferedReader reader, int id, Analyser analyser) {
		this.reader = reader;
		this.id = id;
		this.analyser = analyser;
	}
	
	public void setAnalyser(Analyser analyser) {
		this.analyser = analyser;
	}
	
	public void run() {
		String in;
		try {
			while ((in = reader.readLine()) != null) {
				analyser.analyse(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
