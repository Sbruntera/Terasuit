package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Register {
public static void main(String[] args) {
	String hashed = BCrypt.hashpw("test", BCrypt.gensalt());
	if (BCrypt.checkpw("test1", hashed))
		System.out.println("It matches");
	else
		System.out.println("It does not match");
}
public Register(String user) { //String == Userdata
	/*
	 * Login Forumla -> Daten werden gesendet -> Server überprüft Daten ->
	 * Server sendet Antwort -> User ist eingeloggt
	 */
	Socket socket = null;
	try {
		socket = new Socket("localhost", 3142);

		PrintStream out = new PrintStream(socket.getOutputStream(), true);
		out.println(user);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		String s;
		while (in.ready()) {
			s = in.readLine();
			System.out.println(s);
		}
		// socket.close();
		// System.out.println("closed");
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}
