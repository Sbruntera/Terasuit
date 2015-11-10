package TestClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
	
	public static void main(String[] args) {
		new Client();
	}
	
	public Client() {
		Socket socket;
		try {
			socket = new Socket("localhost", 1337);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String answer = input.readLine();
			JOptionPane.showMessageDialog(null, answer);
		}
		catch (ConnectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
}