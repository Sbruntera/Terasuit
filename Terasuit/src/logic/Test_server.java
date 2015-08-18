package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Test_server {
	private final ServerSocket server;

	public Test_server(int port) throws IOException {
		server = new ServerSocket(port);
	}

	private void connect() {

		while (true) {
			Socket socket = null;
			try {
				socket = server.accept();
				BufferedReader input = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				PrintStream output = new PrintStream(socket.getOutputStream());
				String s;

				while (input.ready()) {
					s = input.readLine();
					output.println(s);
				}
				socket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}

	public static void main(String[] args) throws IOException {
		Test_server server = new Test_server(3141);
		server.connect();
	}
}
