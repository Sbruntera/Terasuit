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
					String[] sl = s.split(",");
					String name = sl[0];
					String pw = sl[1];
					String hashed = BCrypt.hashpw(pw, BCrypt.gensalt()); //test
					if (name.equals("admin") && BCrypt.checkpw(pw, hashed)){ //hashed: hash steht in der DB
						output.println("Success");
						System.out.println("It matches");
					}else
						System.out.println("It does not match");
//					if () { Datenbank überprüfung hier
//						output.println("Success");
//					} else {
//						System.out.println("da ist wohl was falsch");
//					}
				}
			}
		} catch (IOException e) {

		}
	}

}
