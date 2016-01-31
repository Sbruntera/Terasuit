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
		switch (bytes[0] & 192) {
		case (0): // Position wechseln
			lobby.movePlayer(id, bytes[0]);
			break;
		case (64): // Spiel verlassen
			lobby.removePlayer(id, id);
			break;
		case (128): // Spieler kicken
			lobby.removePlayer(id, (short) ((short) bytes[1] << 8 + bytes[2]));
			break;
		case (192): // Spiel starten
			switch (bytes[0] & 32) {
			case (0):
				// Spiel starten
				break;
			case (32):
				lobby.broadcast(input, id);
				break;
			}
		}
	}

}
