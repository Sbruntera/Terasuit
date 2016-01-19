package logic;

public class Analyser {

	private State state;

	public Analyser() {
		state = State.Menu;
	}

	public void analyse(String message) {
		switch (state) {
		case Menu:
			analyseMenuMessage(message);
			break;
		case Lobby:
			analyseLobbyMessage(message);
			break;
		case Game:
			analyseGameMessage(message);
			break;
		}
	}

	public void switchState(State state) {
		this.state = state;
	}

	private void analyseMenuMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0] & 192) {
		case (0): // Stats
			break;
		case (64): // Get GameList
			break;
		case (128): // Join Game
			break;
		case (192): // Logg in
			break;
		}
	}

	private void analyseLobbyMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0] & 192) {
		case (0): // Position Wechseln
			break;
		case (64): // Spieler tritt dem Spiel bei
			break;
		case (128): // Spieler verlässt das Spiel
			break;
		case (192): // Spiel wird gestartet
			break;
		}
	}

	private void analyseGameMessage(String message) {
		byte[] bytes = message.getBytes();
		switch (bytes[0] & 224) {
		case (0): // Spieler erstellt ein Gebäude
			break;
		case(32): // Spieler verbessert ein Gebäude
			break;
		case (64): // Spieler erstellt eine Einheit
			break;
		case (92): // Spieler bewegt eine Einheit
			break;
		case (128): // Spieler verlässt das Spiel
			break;
		case (160): //Spiel gewonnen/verloren
			break;
		case (192): // Chat
			break;
		}
	}
}
