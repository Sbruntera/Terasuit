package logic;

import grafig.Loader;
import grafig.Panel;

import java.util.ArrayList;

public class Analyser {

	private Loader loader;
	private State state;

	public Analyser(Loader loader) {
		state = State.MENU;
	}

	public void analyse(String message) {
		switch (state) {
		case MENU:
			analyseMenuMessage(message);
			break;
		case LOBBY:
			analyseLobbyMessage(message);
			break;
		case GAME:
			analyseGameMessage(message);
			break;
		}
	}

	public void switchState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	private void analyseMenuMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0] & 192) {
		case (0): // Stats
			break;
		case (64): // Get GameList
			ArrayList<Lobby> list = new ArrayList<Lobby>();
			String[] splittedMessage = message.substring(1, message.length())
					.split(",");
			for (String s : splittedMessage) {
				byte[] b = s.getBytes();
				list.add(new Lobby(b[0], s.substring(1), b[1],
						((b[2] & 4) >> 2) == 1, (byte) (b[2] & 3)));
			}
			Lobby[] lobbyList = list.toArray(new Lobby[list.size()]);
			//TODO: An Feldmann: Hier einen Funktionsaufruf einfügen der diese Liste anzeigt
			break;
		case (128): // Join Game
			byte mapID = (byte) Character.getNumericValue(message.charAt(2));
			splittedMessage = message.substring(1).split(",");
			byte[] iDs = new byte[splittedMessage.length];
			String[] names = new String[splittedMessage.length];
			for (int i = 0; i < splittedMessage.length; i++) {
				iDs[i] = splittedMessage[i].getBytes()[0];
				names[i] = splittedMessage[i].substring(1);
			}
			loader.switchPanel(loader.Grouppage);
			//TODO: An Feldmann: Hier Funktionsaufruf zum Beitreten einer Lobby
			break;
		case (192): // Log in
			//TODO: An Feldmann: Hier Funktionsaufruf zum Anzeige ändern (Login disabeln Logout enabeln)
			break;
		}
	}

	private void analyseLobbyMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0] & 192) {
		case (0): // Position Wechseln
			byte position = bytes[0];
			byte playerID = bytes[2];
			//TODO: An Feldmann: Hier Funktionsaufruf GUI aktualisieren (Spieler verschieben)
			break;
		case (64): // Spieler tritt dem Spiel bei
			position = (byte) (bytes[0] & 3);
			playerID = bytes[1];
			String name = message.substring(2);
			//TODO: An Feldmann: Hier Funktionsaufruf GUI aktualisieren (Spieler hinzufügen)
			break;
		case (128): // Spieler verlässt das Spiel
			if (bytes.length != 1) {
				playerID = bytes[1];
				//TODO: An Feldmann: Hier funktionsaufruf GUI aktualisieren (Spieler entfernen) 
			} else {
				switchState(State.MENU);
				//TODO: An Feldmann: Hier Funktionsaufruf ins Menü zurückkehren (Spieler wurde aus dem Spiel entfernt) 
			}
			break;
		case (192): // Spiel wird gestartet
			switchState(State.GAME);
			loader.switchPanel(loader.Gamepage);
			//TODO: An Feldmann: Hier Funktionsaufruf Spiel starten
			break;
		}
	}

	private void analyseGameMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0] & 224) {
		case (0): // Spieler erstellt ein Gebäude
			break;
		case (32): // Spieler verbessert ein Gebäude
			break;
		case (64): // Spieler erstellt eine Einheit
			break;
		case (92): // Spieler bewegt eine Einheit
			break;
		case (128): // Spieler verlässt das Spiel
			break;
		case (160): // Spiel gewonnen/verloren
			break;
		case (192): // Chat
			break;
		}
	}
}
