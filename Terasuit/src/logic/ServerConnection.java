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
					// Nachricht interpretieren
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

	public void createGroup(byte mapID, String name, String password) {
		// Guppe erstellen
		// Sendet erschaffung zurück
		queue.clear();
		addMessage((char) 96 + mapID + name + "," + password);
		return;
	}

	public void connectGroup(byte id) {
		// Versucht der Gruppe zu joinen
		// Sendet erfolgreiche Verbindung zurück oder auch nicht
		queue.clear();
		addMessage(String.valueOf((char) 128 + id));
		return;
	}

	public void returnLobby() {
		// Verlässt die aktuelle Gruppe
		// Sendet erfolgreiche verlassen der Gruppe
		queue.clear();
		addMessage(String.valueOf((char) 64));
	}

	public void close() {
		// Schließst die Verbindung
		queue.clear();
		queue.add(String.valueOf((char) 224));
	}

	public void startGame() {
		queue.clear();
		addMessage(String.valueOf((char) 192));
		// Startet für alle in der Gruppe das Spiel
	}

	public void kickPlayer(byte playerNumber) {
		addMessage(String.valueOf((char) 128 + playerNumber)); //TODO: Playernumber maybe verschieben
	}
	
	public void leaveGame() {
		// Gibt zurück ob das verlassen vom Server akzeptiert worden ist
	}

	public void refreshServerList(boolean noPassword, byte minPlayers,
			byte maxPlayers, byte mapID) {
		addMessage(String.valueOf((char) (64 + Boolean.compare(noPassword,
				false) << 4) + (minPlayers << 2) + maxPlayers)
				+ mapID);
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