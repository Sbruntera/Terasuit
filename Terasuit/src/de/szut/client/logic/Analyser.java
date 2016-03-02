package de.szut.client.logic;

import java.awt.Point;
import java.util.ArrayList;

import de.szut.client.grafik.Loader;

public class Analyser {

	public Loader loader;
	private State state;
	private GameLobby game;

	private boolean isHost;
	byte position;

	public Analyser(Loader loader) {
		state = State.MENU;
		this.loader = loader;
	}

	public void analyse(byte[] bs) {
		switch (state) {
		case MENU:
			analyseMenuMessage(bs);
			break;
		case LOBBY:
			analyseLobbyMessage(bs);
			break;
		case GAME:
			analyseGameMessage(bs);
			break;
		}
	}

	public void switchState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	private void analyseMenuMessage(byte[] message) {
		switch (message[0]) {
		case (0): // Stats
			int i = 1;
			ArrayList<String[]> array = new ArrayList<String[]>();
			while (i < message.length) {
				int value = (((int) (Byte.toUnsignedInt(message[i])) << 8) + Byte
						.toUnsignedInt(message[i + 1]));
				i += 2;
				String type = "";
				boolean end = false;
				while (!end && i < message.length) {
					if (message[i] == 0 && message[i + 1] == 0) {
						end = true;
					} else {
						type += (char) (((message[i] & 0x00FF) << 8) + (message[i + 1] & 0x00FF));
					}
					i += 2;
				}
				array.add(new String[] { type, String.valueOf(value) });

			}
			loader.showStats(array.toArray(new String[array.size()][]));
			break;
		case (1): // Get GameList
			ArrayList<Lobby> list = new ArrayList<Lobby>();
			byte[][] splittedMessage = getSplitString(message, 1);
			for (byte[] s : splittedMessage) {
				if (s.length != 0) {
					list.add(new Lobby(s[0], castToString(s, 3), s[1],
							((s[2] & 8) >> 3) == 1, (byte) (s[2] & 7)));
				}
			}
			loader.updateLobbyList(list.toArray(new Lobby[list.size()]));
			break;
		case (2): // Join Game
			isHost = (message[2] & 4) > 0;
			position = (byte) (message[2] & 3);
			splittedMessage = getSplitString(message, 3);
			String[] names = new String[splittedMessage.length];
			for (i = 0; i < splittedMessage.length; i++) {
				if (splittedMessage[i].length > 1) {
					names[i] = castToString(splittedMessage[i], 0);
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
			loader.connection.setLoggedIn(true);
			loader.connection.setName(castToString(message, 1));
			loader.loggIn(loader.connection.getName());
			break;
		case (4): // Login Failed
			switch (message[1]) {
			case (0):
				loader.feedback("Failed to Login");
				break;
			case (1):
				loader.feedback("User already exsists");
				break;
			case (2):
				loader.feedback("Wrong Password");
				break;
			case (3):
				loader.feedback("Could not join Lobby.");
			}
		}
	}

	private void analyseLobbyMessage(byte[] bs) {
		switch (bs[0]) {
		case (16): // Position Wechseln
			if (bs.length == 4) {
				position = bs[3];
			}
			game.switchPlayers((byte) bs[1], (byte) bs[2]);
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			break;
		case (17): // Spieler tritt dem Spiel bei
			game.addPlayer(bs[1], castToString(bs, 2));
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			loader.setText(game.getPlayerName(bs[1]) + " joined");
			break;
		case (18): // Spieler verlässt das Spiel
			if (bs.length == 2) {
				loader.setText(game.getPlayerName(bs[1]) + " left");
				game.removePlayer(bs[1]);
				loader.updatePlayerList(game.getPlayerNames(), isHost);
			} else {
				switchState(State.MENU);
				loader.switchPanel(loader.Lobbypage);
				loader.connection.refreshServerList(false, "", 0, 4, 0);
				game = null;
			}
			break;
		case (19): // Spieler wird Host
			loader.switchPanel(loader.Grouppage_owner);
			isHost = true;
			loader.updatePlayerList(game.getPlayerNames(), isHost);
			break;
		case (20): // Spiel wird gestartet
			switchState(State.GAME);
			loader.switchPanel(loader.Gamepage);
			loader.game.setPlayerID(position + 1);
			break;
		case (21):
			loader.setText(game.getPlayerName(bs[1]) + ": "
					+ castToString(bs, 2));
			break;
		}
	}

	private void analyseGameMessage(byte[] bs) {
		switch (bs[0]) {
		case (32): // Spieler erstellt oder verbessert ein gebäude ein Gebäude
			byte playerNumber = bs[1];
			byte buildingPosition = (byte) (bs[2]);
			byte id;
			if (bs.length == 4) {
				boolean startBuild = false;
				if (bs[3] < 0) {
					id = (byte) (bs[3] + 128);
				} else {
					id = bs[3];
					startBuild = true;
				}
				String buildingName = null;
				switch (id) {
				case (1):
					buildingName = "Outpost";
					break;
				case (2):
					buildingName = "Barracks";
					break;
				case (3):
					buildingName = "Arsenal";
					break;
				case (4):
					buildingName = "Forge";
					break;
				case (5):
					buildingName = "Manufactory";
					break;
				case (6):
					buildingName = "Mechanics Terminal";
					break;
				case (7):
					buildingName = "Hospital";
					break;
				case (8):
					buildingName = "War Sanctum";
					break;
				case (9):
					buildingName = "Bank";
					break;
				case (10):
					buildingName = "Treasury";
					break;
				case (11):
					buildingName = "Armory";
					break;
				case (12):
					buildingName = "Generator";
					break;
				case (13):
					buildingName = "Solar Grid";
					break;
				case (14):
					buildingName = "Special Operations";
					break;
				}
				if (buildingName != null) {
					if (startBuild) {
						if (playerNumber == position) {
							loader.game.createBuilding(buildingName,
									"Buildings/" + buildingName + ".png",
									(playerNumber << 2) + (playerNumber >> 1)
											+ buildingPosition + 1,
									(playerNumber << 2) + (playerNumber >> 1)
											+ buildingPosition + 19);
						}
					} else {
						if (playerNumber != position) {
							loader.game.createEnemyBuilding(buildingName,
									"Buildings/" + buildingName + ".png",
									(playerNumber << 2) + (playerNumber >> 1)
											+ buildingPosition + 1,
									(playerNumber << 2) + (playerNumber >> 1)
											+ buildingPosition + 19);
						} else {
							loader.game.finishBuilding((playerNumber << 2)
									+ (playerNumber >> 1) + buildingPosition
									+ 19);
						}
					}
				}
			} else {
				loader.game.destroyBuilding(buildingPosition
						+ (playerNumber * 4) + (playerNumber >> 1) + 19);
			}
			break;
		case (33): // bricht eine Produktion ab
			if (bs[1] == position) {
				loader.game.cancel((bs[1] << 2) + (bs[1] >> 1) + bs[2] + 1);
			}
			break;
		case (34): // Ein eigenes Gebäude startet eine Produktion
			id = bs[1];
			buildingPosition = bs[2];
			break;
		case (35): // Spieler erstellt eine Einheit
			Point position = new Point(
					(((int) (Byte.toUnsignedInt(bs[2])) << 8) + Byte
							.toUnsignedInt(bs[3])),
					((int) (Byte.toUnsignedInt(bs[4])) << 8)
							+ Byte.toUnsignedInt(bs[5]));
			short unitID = (short) ((short) (Byte.toUnsignedInt(bs[7]) << 8) + Byte
					.toUnsignedInt(bs[8]));
			String name = "";
			boolean flying = false;
			switch (bs[6]) {
			case (1):
				name = "Marine";
				flying = false;
				break;
			case (2):
				name = "Chronite Tank";
				flying = false;
				break;
			case (3):
				name = "Sniper";
				flying = false;
				break;
			case (4):
				name = "Gröditz";
				flying = false;
				break;
			case (5):
				name = "Hover Tank";
				flying = false;
				break;
			case (6):
				name = "Black Queen";
				flying = true;
				break;
			case (7):
				name = "A25-Roman";
				flying = false;
				break;
			case (8):
				name = "Scout";
				flying = true;
				break;
			case (9):
				name = "Phantom";
				flying = true;
				break;
			case (10):
				name = "Sakata-MK2";
				flying = false;
				break;
			case (11):
				name = "Sakata Spider";
				flying = false;
				break;
			case (12):
				name = "Gladiator";
				flying = false;
				break;
			case (13):
				name = "Meditec";
				flying = false;
				break;
			case (14):
				name = "Saint";
				flying = true;
				break;
			case (15):
				name = "Sphinx";
				flying = false;
				break;
			case (16):
				name = "Modified Phantom";
				flying = true;
				break;
			case (17):
				name = "Modified Sakata";
				flying = false;
				break;
			}
			loader.game.entity("Unit/Ground/" + name + ".png", bs[1] + 1,
					flying, unitID, position);
			break;
		case (36): // Spieler bewegt eine Einheit
			loader.game.moveUnit(bs[1], bs[2] != 1, bs[2] == 3, bs[2] > 1,
					getUnits(bs, 3));
			break;
		case (37): // Einheit beginnt schießen
			break;
		case (38): // Einheit stirbt
			int[] units = getUnits(bs, 1);
			if (units != null) {
				loader.game.removeUnits(units);
			}
			break;
		case (39): // Spieler verlässt das Spiel
			playerNumber = bs[1];
			loader.setGameText(game.getPlayerName(playerNumber) + " hat das Spiel verlassen.");
			game.removePlayer(playerNumber);
			break;
		case (40): // Spiel gewonnen/verloren
			loader.game.end(bs[1] > 0);
			break;
		case (21): // Chat
			loader.setGameText(game.getPlayerName(bs[1]) + ": "
					+ castToString(bs, 2));
			break;
		}
	}

	private String castToString(byte[] message, int bytesToCut) {
		String s = "";
		char[] buffer = new char[message.length - bytesToCut >> 1];
		for (int i = 0; i < buffer.length; i++) {
			int bpos = (i << 1) + bytesToCut;
			char c = (char) (((message[bpos] & 0x00FF) << 8) + (message[bpos + 1] & 0x00FF));
			s += c;
		}
		return s;
	}

	private byte[][] getSplitString(byte[] input, int bytesToCut) {
		ArrayList<byte[]> outerArray = new ArrayList<byte[]>();
		ArrayList<Byte> array = new ArrayList<Byte>();
		boolean second = false;
		for (int i = bytesToCut; i < input.length; i++) {
			if (input[i] == 0) {
				if (!second) {
					second = true;
				} else {
					outerArray
							.add(toPrimal(array.toArray(new Byte[array.size()])));
					array.clear();
					second = false;
				}
			} else {
				if (second) {
					array.add((byte) 0);
				}
				array.add(input[i]);
				second = false;
			}
		}
		outerArray.add(toPrimal(array.toArray(new Byte[array.size()])));
		return outerArray.toArray(new byte[outerArray.size()][]);
	}

	private byte[] toPrimal(Byte[] splitted) {
		byte[] bytes = new byte[splitted.length];
		for (int i = 0; i < splitted.length; i++) {
			bytes[i] = splitted[i];
		}
		return bytes;
	}

	private int[] getUnits(byte[] input, int bytesToCut) {
		int[] array = null;
		if (input.length >= bytesToCut + 2 && input.length % 2 == 1) {
			array = new int[(input.length - bytesToCut) / 2];
			for (int i = 0; i < array.length; i++) {
				array[i] = (Byte.toUnsignedInt(input[i * 2 + bytesToCut]) << 8)
						+ Byte.toUnsignedInt(input[i * 2 + bytesToCut + 1]);
			}
		}
		return array;
	}
}
