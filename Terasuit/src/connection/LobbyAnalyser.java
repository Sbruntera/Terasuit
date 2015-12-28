package connection;

public class LobbyAnalyser implements Analyser {

	@Override
	public void analyse(String input) {
		byte[] bytes = input.getBytes();
		switch (bytes[0] & 192) {
		case (0): // Position wechseln
			// TODO: Position wechseln
			break;
		case (64): // Spiel verlassen
			// TODO: Spiel verlassen
			break;
		case (128): // Spieler kicken
			// TODO: Spieler kicken
			break;
		case (192): // Spiel starten
			// TODO: Spiel starten
		}
	}

}
