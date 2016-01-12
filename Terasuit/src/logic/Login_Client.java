package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Login_Client implements Runnable {

	private Socket socket;
	private BufferedReader in;
	private PrintStream out;
	private boolean login;
	private String user;
	private char[] password;
	private char[] password2;
	private String mail;

	public Login_Client() {
		try {
			socket = new Socket("localhost", 3142);
			this.in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			this.out = new PrintStream(socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void login(String user, char[] password) {
		this.user = user;
		this.password = password;
		login = true;
	}

	public void register(String user, char[] password, char[] password2,
			String mail) {
		this.user = user;
		this.password = password;
		this.password2 = password2;
		this.mail = mail;
		login = false;
	}

	@Override
	public void run() {
		if (login) {
			login();
		} else {
			register();
		}
	}

	private void login() { // String == Userdata
		/*
		 * Login Forumla -> Daten werden gesendet -> Server überprüft Daten ->
		 * Server sendet Antwort -> User ist eingeloggt
		 */
		try {
			String pw = "";
			for (int i = 0; i < password.length; i++) {
				pw = pw + password[i];
			}
			String send = "l," + user + "," + pw;
			// String send = "r," + user + "," + pw + ",test@test.de,Admin";
			System.out.println(send);
			out.println(send);
			String s;
			while (in.ready()) {
				s = in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void register() {
		// Register
		boolean samepw = true;
		try {
			for (int i = 0; i < password.length; i++) {
				if (password[i] != password2[i]) {
					// Interupt Fehler Meldung
					System.out.println("Passwort nicht gleich");
					samepw = false;
				}

			}
			if (samepw) {
				String pw = "";
				for (int x = 0; x < password.length; x++) {
					pw = pw + password[x];
				}
				String send = "r," + user + "," + pw + "," + mail + ",Admin";
				out.println(send);
				String s;
				while (in.ready()) {
					s = in.readLine();
					System.out.println(s);
				}
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
