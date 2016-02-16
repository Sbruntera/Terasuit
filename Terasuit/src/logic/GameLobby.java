package logic;

public class GameLobby {
	
	private String[] players;

	public GameLobby(String[] players) {
		this.players = players;
	}
	
	public String getPlayerName(byte id) {
		return players[id];
	}

	public String[] getPlayerNames() {
		return players;
	}
	
	public void addPlayer(byte id, String name) {
		players[id] = name;
	}
	
	public void removePlayer(byte id) {
		players[id] = null;
	}
	
	public void switchPlayers(byte id1, byte id2) {
		String playerTemp = players[id1];
		players[id1] = players[id2];
		players[id2] = playerTemp;
	}
}
