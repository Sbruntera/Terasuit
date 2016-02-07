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
	public void analyse(String input) {
		byte[] bytes = input.getBytes();
		switch (bytes[0]) {
		case (16): // Position wechseln
			lobby.movePlayer(id, bytes[1]);
			break;
		case (17): // Spiel verlassen
			lobby.removePlayer(id, id);
			break;
		case (18): // Spieler kicken
			lobby.removePlayer(id, (short) ((int) bytes[1] << 8 + bytes[2]));
			break;
		case (19): // Spiel starten
			lobby.startGame(id);
			break;
		case (20):
			lobby.broadcast(input.substring(1), id);
			break;
		}
	}

}
