package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client_test {

	public Client_test(){
		System.out.println("Dat wetter ist so schlimm");
		System.out.println("Drecks kopfschmerzen");
		/*
		 * Login Forumla -> Daten werden gesendet -> Server überprüft Daten -> Server sendet Antwort -> User ist eingeloggt
		 * 
		 */
	}
	public static void main(String[] args) { 
        Socket socket = null; 
        try { 
            socket = new Socket("localhost", 3141); 

            OutputStream raus = socket.getOutputStream(); 
            PrintStream ps = new PrintStream(raus, true); 
            ps.println("name"); 
            ps.println("passwort"); 

            InputStream rein = socket.getInputStream(); 
            //System.out.println("verf\u00FCgbare Bytes: " + rein.available()); 
            BufferedReader buff = new BufferedReader(new InputStreamReader(rein)); 
             
            while (buff.ready()) { 
                System.out.println(buff.readLine()); 
            } 
            socket.close(); 
            System.out.println("closed");
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    } 
}
