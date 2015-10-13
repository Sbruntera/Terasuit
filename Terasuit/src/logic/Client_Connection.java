package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Client_Connection implements Runnable {

	private BufferedReader input;
	private PrintStream output;
	private String[] sl;
	private DB db;

	public Client_Connection(Socket socket, DB db) {
		try {
			KeyPair kp = createRSA();
			String pub = kp.getPublic().toString();
			System.out.println(pub);
			PrivateKey pri = kp.getPrivate();
			this.db = db;
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream(), true);
			output.print(pub);
			System.out.println("HEYHO");
			String aes = input.readLine();
		} catch (IOException e) {

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public KeyPair createRSA() throws NoSuchAlgorithmException{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		return kpg.genKeyPair();
	}
	public void decryptAES(String enaes){
		
	}

}
