package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client_Connection implements Runnable {

	private BufferedReader input;
	private PrintStream output;

	public Client_Connection(Socket socket) {
		try {
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream(), true);
		} catch (IOException e) {

		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				while (input.ready()) {
					String s = input.readLine();
					if (s.equals("admin, pw")) { // Datenbank überprüfung hier
						output.println("Success");// Geht nicht
						System.out.println("bin drin");
					} else {
						System.out.println("da ist wohl was falsch");
					}
				}
			}
		} catch (IOException e) {

		}
	}

}
