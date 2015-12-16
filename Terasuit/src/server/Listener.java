package server;

import java.io.BufferedReader;
import java.io.IOException;

public class Listener implements Runnable {
	
	BufferedReader reader;
	Analyser analyser;
	
	public Listener(BufferedReader reader, Analyser analyser) {
		this.reader = reader;
		this.analyser = analyser;
	}
	
	public void setAnalyser(Analyser analyser) {
		this.analyser = analyser;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				while (reader.ready()) {
					System.out.println("Testerino");
					String in = reader.readLine();
					analyser.analyse(in);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void run() {
//		String in;
//		try {
//			System.out.println("nachricht");
//			while ((in = reader.readLine()) != null) {
//				analyser.analyse(in);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
