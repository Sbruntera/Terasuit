package connection;

import server.Lobby;

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
			break;
		case (18): // Spieler kicken
			lobby.removePlayer(id, input[1]);
			break;
		case (19): // Spiel starten
			lobby.startGame(id);
			break;
		case (20):
			lobby.broadcast(castToString(input).substring(1), id);
			break;
		}
	}

	private String castToString(byte[] input) {
		String s = "";
		for (byte i : input) {
			s += (char) i;
		}
		return s;
	}
}
