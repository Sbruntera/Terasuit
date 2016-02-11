package logic;

import grafig.Loader;

import java.util.ArrayList;

public class Analyser {

	private Loader loader;
	private State state;

	public Analyser(Loader loader) {
		state = State.MENU;
		this.loader = loader;
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
		switch (bytes[0]) {
		case (0): // Stats
			break;
		case (1): // Get GameList
			ArrayList<Lobby> list = new ArrayList<Lobby>();
			String[] splittedMessage = message.substring(1).split(",");
			for (String s : splittedMessage) {
				if (s.length() != 0) {
					byte[] b = s.getBytes();
					list.add(new Lobby(b[0], s.substring(1), b[1],
							((b[2] & 4) >> 2) == 1, (byte) (b[2] & 3)));
				}
			}
			Lobby[] lobbyList = list.toArray(new Lobby[list.size()]);
			// TODO: An Feldmann: Hier einen Funktionsaufruf einfügen der diese
			// Liste anzeigt
			break;
		case (2): // Join Game
			byte mapID = (byte) bytes[1];
			boolean host = bytes[2] > 0;
			splittedMessage = message.substring(2).split(",");
			short[] iDs = new short[splittedMessage.length];
			String[] names = new String[splittedMessage.length];
			for (int i = 0; i < splittedMessage.length; i++) {
				iDs[i] = (short) (splittedMessage[i].getBytes()[0] << 8 + splittedMessage[i]
						.getBytes()[1]);
				names[i] = splittedMessage[i].substring(2);
			}
			if (host) {
				state = State.LOBBY;
				loader.switchPanel(loader.Grouppage_owner);
			} else {
				state = State.LOBBY;
				loader.switchPanel(loader.Grouppage);
			}
			// TODO: An Feldmann: Hier Funktionsaufruf zum Beitreten einer Lobby
			break;
		case (3): // Log in
			// TODO: An Feldmann: Hier Funktionsaufruf zum Anzeige ändern (Login
			// disabeln Logout enabeln)
			break;
		}
	}

	private void analyseLobbyMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0]) {
		case (16): // Position Wechseln
			byte position = bytes[1];
			short playerID = (short) (bytes[2] << 8 + bytes[3]);
			// TODO: An Feldmann: Hier Funktionsaufruf GUI aktualisieren
			// (Spieler verschieben)
			break;
		case (17): // Spieler tritt dem Spiel bei
			position = (byte) (bytes[0] & 3);
			playerID = (short) (bytes[1] << 8 + bytes[2]);
			String name = message.substring(2);
			// TODO: An Feldmann: Hier Funktionsaufruf GUI aktualisieren
			// (Spieler hinzufügen)
			break;
		case (18): // Spieler verlässt das Spiel
			if (bytes.length != 1) {
				playerID = (short) (bytes[1] << 8 + bytes[2]);
				// TODO: An Feldmann: Hier funktionsaufruf GUI aktualisieren
				// (Spieler entfernen)
			} else {
				switchState(State.MENU);
				loader.switchPanel(loader.Lobbypage);
				// TODO: An Feldmann: Hier Funktionsaufruf ins Menü zurückkehren
				// (Spieler wurde aus dem Spiel entfernt)
			}
			break;
		case (19): // Spiel wird gestartet
			switchState(State.GAME);
			loader.switchPanel(loader.Gamepage);
			// TODO: An Feldmann: Hier Funktionsaufruf Spiel starten
			break;
		}
	}

	private void analyseGameMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0]) {
		case (32): // Spieler erstellt oder verbessert ein gebäude ein Gebäude
			short playerNumber = (short) (bytes[1] << 8 + bytes[2]);
			byte buildingPosition = bytes[3];
			byte id = bytes[4];
			// TODO: An Feldmann: Hier Funktionsaufruf Gebäude bauen
			break;
		case (33): // Spieler erstellt eine Einheit
			playerNumber = (short) (bytes[1] << 8 + bytes[2]);
			short unitPosition = (short) (bytes[3] << 8 + bytes[4]);
			id = bytes[5];
			short unitID = (short) (bytes[6] << 8 + bytes[7]);
			// TODO: An Feldmann: Hier Funktionsaufruf Einheit erstellen
			break;
		case (34): // Spieler bewegt eine Einheit
			playerNumber = (short) (bytes[1] << 8 + bytes[2]);
			byte direction = bytes[3];
			short[] unitIDs = new short[(bytes.length - 3) / 2];
			for (int i = 3; i < bytes.length; i += 2) {
				unitIDs[(i - 3) / 2] = (short) (bytes[i] << 8 + bytes[i + 1]);
			}
			// TODO: An Feldmann: Hier Funktionsaufruf Einheit bewegen
			break;
		case (35): // Einheit beginnt schießen
			break;
		case (36): // Einheit stirbt
			break;
		case (37): // Spieler verlässt das Spiel
			playerNumber = (short) (bytes[1] << 8 + bytes[2]);
			// TODO: An Feldmann: Hier Funktionsaufruf Spieler verlässt anzeigen
			break;
		case (38): // Spiel gewonnen/verloren
			boolean won = bytes[1] > 0;
			// TODO: An Feldmann: Hier Funktionsaufruf Sieg/Niederlage
			break;
		case (39): // Chat
			playerNumber = (short) (bytes[1] << 8 + bytes[2]);
			break;
		}
	}
}
