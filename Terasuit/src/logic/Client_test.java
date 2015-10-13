package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Client_test {

	public Client_test(String user) { // String == Userdata
		/*
		 * Login Forumla -> Daten werden gesendet -> Server überprüft Daten ->
		 * Server sendet Antwort -> User ist eingeloggt
		 */
		Socket socket = null;
		try {
			socket = new Socket("localhost", 3142);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));	
			PrintStream out = new PrintStream(socket.getOutputStream(), true);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String p = in.readLine();
			p = p + in.readLine();
			p = p + in.readLine();
//			while (in.ready()){
//				System.out.println("heyho");
//				p = p + in.readLine();
//				System.out.println(p);
//			}
			out.println(encryptAES(p));
			out.println(user);
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
			// socket.close();
			// System.out.println("closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SecretKey createAES() throws NoSuchAlgorithmException {
		KeyGenerator aes = KeyGenerator.getInstance("AES");
		aes.init(256);
		return aes.generateKey();
	}

	public byte[] encryptAES(String pub) {
		Cipher cipher;
		try {
			System.out.println(pub);
			cipher = Cipher.getInstance("AES");
			String[] pkStringSplitted = pub.split("\n");
			BigInteger m = new BigInteger(pkStringSplitted[1].split(" ")[3]);
			BigInteger e = new BigInteger(pkStringSplitted[2].split(" ")[4]);
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey pubKey = fact.generatePublic(keySpec);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			return cipher.doFinal(createAES().toString().getBytes());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidKeySpecException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;

	}
}
