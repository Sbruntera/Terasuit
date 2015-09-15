package lobby;

/**
 * Verwaltet die Spieler in einem offenen Spiel
 * @author Simeon Kublenz
 * 
 */
public class GameServer {
	
	private static final int MAXPLAYERS = 4;
	private Player[] playerList = new Player[MAXPLAYERS];
	private LobbyManager lobby;
	private String gameName;
	private String password;
	
	public void createGame(LobbyManager lobby, Player host, String gameName, String password) {
		playerList[0] = host;
		this.lobby = lobby;
		this.gameName = gameName;
		this.password = password;
	}
	
	public void addPlayer(Player player) {
		boolean playerJoined = false;
		int i = 0;
		while (!playerJoined) {
			if (playerList[i] == null) {
				playerList[i] = player;
				playerJoined = true;
			}
			else {
				if (i+1 < MAXPLAYERS) {
					i++;
				}
			}
		}
	}
	
	public void removePlayer(int position) {
		if (playerList[position] != null) {
			playerList[position].disconnect();
			playerList[position] = null;
		}
		if (getNumberOfPlayers() == 0) {
			closeGame();
		}
	}
	
	public void closeGame() {
		for (Player p : playerList) {
			p.disconnect();
		}
		lobby.removeServer(this);
	}

	public String getName() {
		return gameName;
	}

	public String getPassword() {
		return password;
	}
	
	public int getNumberOfPlayers() {
		int players = 0;
		for (int i = 0; i < MAXPLAYERS; i++) {
			if (playerList[i] != null) {
				players++;
			}
		}
		return players;
	}
}