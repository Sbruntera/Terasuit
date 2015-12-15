package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import logic.BCrypt;
import main.Analyser;

public class Client_Connection implements Analyser {

	private String[] sl;
	private DB db;
	
	public Client_Connection(DB db) {
		this.db = db;
	}

	@Override
	public void analyse(String in) {
		sl = in.split(",");
		String status = sl[0];
		if (status.equals("r")) {
			register();
		} else {
			login();
		}
	}
	
	private void register() {
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
	
	private void login() {
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
