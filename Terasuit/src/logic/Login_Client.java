package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Login_Client {
	
	private BufferedReader in;
	private PrintStream out;
	
	public Login_Client() {
		try {
			Socket socket = new Socket("localhost", 3142);
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
	
	public void login(String user, char[] password) { // String == Userdata
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
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String s;
			while (in.ready()) {
				s = in.readLine();
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void register(String user, char[] password, char[] password2,
			String mail) {
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
			if(samepw){
				String pw = "";
				for (int x = 0; x < password.length; x++) {
					pw = pw + password[x];
				}
				String send = "r," + user + "," + pw + "," + mail
						+ ",Admin";
				out.println(send);
				String s;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
}
