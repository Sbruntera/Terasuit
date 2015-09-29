package lobby;

/**
 * Verwaltet ein offenes Spiel
 * @author Simeon Kublenz
 * 
 */
public class GameServer {
	
	private static final int MAXPLAYERS = 4;
	private Player[] playerList = new Player[MAXPLAYERS];
	private LobbyManager lobby;
	private String gameName;
	private String password;
	private Map map;
	private Player host;
	
	public void createGame(LobbyManager lobby, Player host, String gameName, String password, Map map) {
		playerList[0] = host;
		this.host = host;
		this.lobby = lobby;
		this.gameName = gameName;
		this.password = password;
		this.map = map;
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
		if (playerList[position] != null) { // An dieser Position existiert ein Spieler
			if (playerList[position] == host) { // Der Spieler ist Host
				boolean playerFound = false;
				int actualPlayer = 0;
				while (!playerFound && actualPlayer < 4) {
					if (playerList[actualPlayer] == null) {
						actualPlayer++;
					}
					else {
						playerFound = true;
						host = playerList[actualPlayer];
					}
				}
			}
			playerList[position].disconnect(null);
			playerList[position] = null;
		}
		if (getNumberOfPlayers() == 0) {
			closeGame();
		}
	}
	
	public void movePlayer(int oldPosition, int newPosition) {
		if (playerList[oldPosition] != null && playerList[newPosition] == null) {
			playerList[newPosition] = playerList[oldPosition];
		}
	}
	
	public void closeGame() {
		for (Player p : playerList) {
			p.disconnect("The Game was closed");
		}
		lobby.removeServer(this);
	}

	public String getName() {
		return gameName;
	}

	public String getPassword() {
		return password;
	}
	
	public boolean hasPassword() {
		if (password == null) {
			return false;
		}
		return true;
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
	
	public Map getMap() {
		return map;
	}
	
	public String getHostname() {
		return playerList[0].getName();
	}
}