package de.szut.server.connection;

import de.szut.server.logic.Lobby;

/**
 * 
 * @author Simeon
 *
 */
public class LobbyAnalyser implements Analyser {

	private Lobby lobby;
	private short id;

	public LobbyAnalyser(Lobby lobby, short id) {
		this.lobby = lobby;
		this.id = id;
	}

	/**
	 * Analysiert die Nachricht als Lobby Nachricht
	 * 
	 * @param input
	 */
	@Override
	public void analyse(byte[] input) {
		switch (input[0]) {
		case (16): // Position wechseln
			if (input.length == 2) {
				lobby.switchPlayers(id, (byte) (input[1] >> 2), (byte) (input[1] & 3));
			}
			break;
		case (17): // Spiel verlassen
			lobby.removePlayer(id, lobby.getPosition(id));
			Logging.log(lobby.getPosition(id) + " hat die Lobby " + lobby.getName() + " verlassen", "STATUSUPDATE");
			break;
		case (18): // Spieler kicken
			lobby.removePlayer(id, input[1]);
			Logging.log(input[1] + " wurde aus der Lobby " + lobby.getName() + " gekickt", "STATUSUPDATE");
			break;
		case (19): // Spiel starten
			lobby.startGame(id);
			Logging.log("Das Spiel der Lobby " + lobby.getName() + " mit der ID " + lobby.getID() + " wurde gestartet", "STATUSUPDATE");
			break;
		case (20):
			lobby.broadcast(split(input, 1), id);
			break;
		default:
			Logging.log("Nachricht konnte nicht analysiert werden(Fehler: " + (char) input[0] + ")", "ERROR");
			break;
		}
	}

	private byte[] split(byte[] input, int bytesToSplit) {
		byte[] output = new byte[input.length-1];
		for (int i = 0; i < output.length; i++) {
			output[i] = input[i+1];
		}
		return output;
	}
}
