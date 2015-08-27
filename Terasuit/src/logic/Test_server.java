package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Test_server {
	private final ServerSocket server;
	private BufferedReader input;
	private PrintStream output;

	public Test_server(int port) throws IOException {
		server = new ServerSocket(port);
	}

	private void connect() {
		try {
			Socket socket = null;
			socket = server.accept();
			input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream(), true);
			String s;
			String name = null;
			String pw = null;
			Boolean test = true;
			while (test) {	
				while (input.ready()) {
					s = input.readLine();
					if (name == null){
						name = s;
					}else{
						pw = s;
					}
					System.out.println(name);
					System.out.println(pw);	
					if((name != null && pw!= null) && name.contains("admin") && pw.contains("pw")){ //Datenbank überprüfung hier
						output.println("Success");//Geht nicht 
						System.out.println("bin drin");
						name = null;
						pw = null;
						socket.close();
						//test = false;
					}else{
						System.out.println("da ist wohl was falsch");
					}
				}		
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Test_server server = new Test_server(3141);
		server.connect();
	}
}
