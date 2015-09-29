package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

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
		// TODO Auto-generated method stub
		try {
			while (true) {
				while (input.ready()) {
					String s = input.readLine();
					sl = s.split(",");
					String status = sl[0];
					if(status.equals("register")){
						register();
					}else{
						login();
					}
				}
			}
		} catch (IOException e) {

		}
	}
	public void register(){
		String name = sl[1];
		String pw = sl[2];
		String email = sl[3];
		String mode = sl[4];
		String hashed = BCrypt.hashpw(pw, BCrypt.gensalt());
		db.addUser(name, hashed, email, mode);
		System.out.println("Regist");
	}
	public void login(){
		String name = sl[1];
		String pw = sl[2];
		if (name.equals("admin") && BCrypt.checkpw(pw, db.getUser(name))){ //hashed: hash steht in der DB
			output.println("Success");
			System.out.println("It matches");
		}else{
			System.out.println("It does not match");
		}
	}

}
