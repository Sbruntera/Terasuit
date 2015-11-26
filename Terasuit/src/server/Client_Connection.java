package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import logic.BCrypt;

public class Client_Connection implements Runnable {

	private BufferedReader input;
	private PrintStream output;
	private String[] sl;
	private DB db;

	public Client_Connection(Socket socket, DB db) {
		try {

			this.db = db;
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream(), true);
		} catch (IOException e) {

		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				while (input.ready()) {
					String s = input.readLine();
					System.out.println(s);
					sl = s.split(",");
					String status = sl[0];
					if (status.equals("r")) {
						register();
					} else {
						login();
					}
				}
			}
		} catch (IOException e) {

		}
	}

	public void register() {
		String name = sl[1];
		String pw = sl[2];
		String email = sl[3];
		String mode = sl[4];
		String hashed = BCrypt.hashpw(pw, BCrypt.gensalt());
		if (!db.search(name)) {
			db.addUser(name, hashed, email, mode);
		} else {
			// Fehlermeldung
			output.println("Miep");
		}
		System.out.println("Regist");
	}

	public void login() {
		String name = sl[1];
		String pw = sl[2];
		if (BCrypt.checkpw(pw, db.getUser(name))) { // hashed: hash steht in der
													// DB
			output.println("Success");
			System.out.println("It matches");
		} else {
			System.out.println("It does not match");
		}
	}
}
