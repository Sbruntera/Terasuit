package logic;

import java.io.OutputStream;
import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Test {

	public static void main(String[] args) {
		KeyPairGenerator kpg = null;
		KeyGenerator aes;
		SecretKey aeskey;
		try {
			aes = KeyGenerator.getInstance("AES");
			aes.init(128);
			aeskey = aes.generateKey();
			Cipher ci = Cipher.getInstance("AES");
			ci.init(Cipher.ENCRYPT_MODE, aeskey);
			OutputStream out;
			out = new CipherOutputStream(null, ci);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
