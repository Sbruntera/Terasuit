package logic;

import grafig.Loader;

import java.util.ArrayList;

public class Analyser {

	private Loader loader;
	private State state;
	private GameLobby game;
	
	private boolean isHost;
	private byte position;

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
					list.add(new Lobby(b[0], s.substring(3), b[1],
							((b[2] & 8) >> 3) == 1, (byte) (b[2] & 7)));
				}
			}
			loader.updateLobbyList(list.toArray(new Lobby[list.size()]));
			break;
		case (2): // Join Game
			isHost = (bytes[2] & 4) > 0;
			position = (byte) (bytes[2] & 3);
			splittedMessage = message.substring(3).split(",");
			String[] names = new String[splittedMessage.length];
			for (int i = 0; i < splittedMessage.length; i++) {
				if (splittedMessage[i].length() > 1) {
					System.out.println((byte) splittedMessage[i].charAt(0) + "§");
					names[i] = splittedMessage[i];
				} else {
					names[i] = "";
				}
			}
			if (isHost) {
				state = State.LOBBY;
				loader.switchPanel(loader.Grouppage_owner);
			} else {
				state = State.LOBBY;
				loader.switchPanel(loader.Grouppage);
			}
			game = new GameLobby(names);
			loader.updatePlayerList(names, isHost);
			break;
		case (3): // Log in
			System.out.println("success");
			loader.connection.setLoggedIn(true);
			loader.connection.setName(message.substring(1));
			loader.loggIn(loader.connection.getName());
			break;
		}
	}

	private void analyseLobbyMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0]) {
		case (16): // Position Wechseln
			if (bytes.length == 4) {
				position = bytes[3];
			}
			game.switchPlayers(bytes[1], bytes[2]);
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			break;
		case (17): // Spieler tritt dem Spiel bei
			System.out.println("hehehe");
			game.addPlayer(bytes[1], message.substring(2));
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			break;
		case (18): // Spieler verlässt das Spiel
			System.out.println(bytes.length + "akljfla");
			if (bytes.length == 2) {
				System.out.println(bytes[1] + "aoifj");
				game.removePlayer(bytes[1]);
				loader.updatePlayerList(game.getPlayerNames(), isHost);
			} else {
				switchState(State.MENU);
				loader.switchPanel(loader.Lobbypage);
				loader.connection.refreshServerList(false, "", 0, 4, 0);
				game = null;
				// (Spieler wurde aus dem Spiel entfernt)
			}
			break;
		case (19): //Spieler wird Host
			loader.switchPanel(loader.Grouppage_owner);
			isHost = true;
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			break;
		case (20): // Spiel wird gestartet
			switchState(State.GAME);
			loader.switchPanel(loader.Gamepage);
			// TODO: An Feldmann: Hier Funktionsaufruf Spiel starten
			break;
		case (21):
			loader.setText(game.getPlayerName(bytes[1]) + ": " + message.substring(2));
			break;
		}
	}

	private void analyseGameMessage(String message) {
		System.out.println("Message gained");
		byte[] bytes = message.getBytes();
		switch (bytes[0]) {
		case (32): // Spieler erstellt oder verbessert ein gebäude ein Gebäude
			byte playerNumber = bytes[1];
			byte buildingPosition = bytes[2];
			byte id = bytes[3];
			String buildingName = null;
			System.out.println(id + "Nananananananananana");
			switch (id) {
			case (0):
				buildingName = "Outpost";
				break;
			case (1):
				buildingName = "Barracks";
				break;
			case (2):
				buildingName = "Arsenal";
				break;
			case (3):
				buildingName = "Forge";
				break;
			case (4):
				buildingName = "Manufactory";
				break;
			case (5):
				buildingName = "Mechanics Terminal";
				break;
			case (6):
				buildingName = "Hospital";
				break;
			case (7):
				buildingName = "War Sanctum";
				break;
			case (8):
				buildingName = "Bank";
				break;
			case (9):
				buildingName = "Treasury";
				break;
			case (11):
				buildingName = "Armory";
				break;
			case (12):
				buildingName = "Generator";
				break;
			case (14):
				buildingName = "Solar Grid";
				break;
			case (15):
				buildingName = "Special Operations";
				break;
			}
			if (buildingName != null) {
				if (playerNumber == position) {
					loader.game.createBuilding(buildingName, "Buildings/" + buildingName + ".png", (playerNumber << 2) + buildingPosition + 1, (playerNumber << 2) + buildingPosition + 19);
				} else {
					loader.game.createEnemyBuilding(buildingName, "Buildings/" + buildingName + ".png", (playerNumber << 2) + buildingPosition + 1, (playerNumber << 2) + buildingPosition + 19);
				}
			}
			break;
		case (33): // Ein eigenes Gebäude startet eine Produktion
			id = bytes[1];
			buildingPosition = bytes[2];
			//TODO: An Feldmann: Hier Einheitenproduktion starten
			break;
		case (34): // Spieler erstellt eine Einheit
			playerNumber = bytes[1];
			short unitPosition = (short) (bytes[2] << 8 + bytes[3]);
			id = bytes[4];
			short unitID = (short) (bytes[5] << 8 + bytes[6]);
			// TODO: An Feldmann: Hier Funktionsaufruf Einheit erstellen
			break;
		case (35): // Spieler bewegt eine Einheit
			playerNumber = bytes[1];
			byte direction = bytes[2];
			short[] unitIDs = new short[(bytes.length - 2) / 2];
			for (int i = 3; i < bytes.length; i += 2) {
				unitIDs[(i - 2) / 2] = (short) (bytes[i] << 8 + bytes[i + 1]);
			}
			// TODO: An Feldmann: Hier Funktionsaufruf Einheit bewegen
			break;
		case (36): // Einheit beginnt schießen
			break;
		case (37): // Einheit stirbt
			break;
		case (38): // Spieler verlässt das Spiel
			playerNumber = bytes[1];
			// TODO: An Feldmann: Hier Funktionsaufruf Spieler verlässt anzeigen
			break;
		case (39): // Spiel gewonnen/verloren
			boolean won = bytes[1] > 0;
			// TODO: An Feldmann: Hier Funktionsaufruf Sieg/Niederlage
			break;
		case (21):
			loader.setGameText(game.getPlayerName(bytes[1]) + ": " + message.substring(2));
			break;
		}
	}
}
