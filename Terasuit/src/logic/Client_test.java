package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

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
 
            PrintStream out = new PrintStream(socket.getOutputStream(), true); 
//            System.out.println("Login: ");
//            Scanner inL = new Scanner(System.in);
//            String login = inL.nextLine();
//            System.out.println("Password: ");
//            Scanner inP = new Scanner(System.in);
//            String password = inP.nextLine();
            out.println("admin"); 
            out.println("pw"); 

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            String s;
            while (in.ready()) { 
            	s = in.readLine();
                System.out.println(s); 
            } 
//            socket.close(); 
//            System.out.println("closed");
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    } 
}
