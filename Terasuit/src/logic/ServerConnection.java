package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JOptionPane;

public class ServerConnection implements Runnable {

	public boolean ServerAccess = true;
	ConcurrentLinkedQueue<String> queue;
	private PrintStream writer;
	private BufferedReader reader;

	public ServerConnection() {
		Socket socket;
		try {
			socket = new Socket("localhost", 3142);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			this.reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			this.writer = new PrintStream(socket.getOutputStream(), true);
		} catch (ConnectException e) {
			// TODO Auto-generated catch block
			System.out.println("Server Not Reachable");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		queue = new ConcurrentLinkedQueue<String>();
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (reader.ready()) {
					System.out.println("Testerino");
					String in = reader.readLine();
					//Nachricht interpretieren
				}
				if (!queue.isEmpty()) {
					writer.println(queue.remove());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addMessage(String message) {
		queue.add(message);
	}

	// #################################################################
	public void sendInformation() {
		// Liste der Updates wird gesendet
	}

	public void login(String user, char[] password) {
		addMessage((char) 160 + user + password);
	}

	public boolean createGroup() {
		// Guppe erstellen
		// Sendet erschaffung zurück
		return true;
	}

	public boolean connectGroup() {
		// Versucht der Gruppe zu joinen
		// Sendet erfolgreiche Verbindung zurück oder auch nicht
		return true;
	}

	public boolean returnLobby() {
		// Verlässt die aktuelle Gruppe
		// Sendet erfolgreiche verlassen der Gruppe
		return true;
	}

	public boolean closeLobby() {
		// Verlässt die aktuelle Gruppe
		// Sendet erfolgreiche schließen der Gruppe
		return true;
	}

	public void close() {
		// Schließst die Verbindung
	}

	public boolean startGame() {
		// Startet für alle in der Gruppe das Spiel
		return true;
	}

	public boolean returnToLobbyFromGame() {
		// Gibt zurück ob das verlassen vom Server akzeptiert worden ist
		return true;
	}

	public void refreshServerList() {
		// Aktuellisiert die Listen
		// RETURN!!!
	}

	public boolean isServerAccess() {
		return ServerAccess;
	}

	public void setServerAccess(boolean serverAccess) {
		ServerAccess = serverAccess;
	}
}