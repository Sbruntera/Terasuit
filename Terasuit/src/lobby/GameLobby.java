package lobby;

/**
 * Datenträger zur übertragung der Spieldaten einzelner Spiele an den Client
 * @author Simeon Kublenz
 *
 */
public class GameLobby {
	
	private Map map;
	private String name;
	private String host;
	private boolean password;
	private int players;
	
	public GameLobby(Map map, String name, String host, boolean password, int players) {
		this.map = map;
		this.name = name;
		this.host = host;
		this.password = password;
		this.players = players;
	}
	
	public Map getMap() {
		return map;
	}
	
	public String getName() {
		return name;
	}
	
	public String getHost() {
		return host;
	}
	
	public boolean getPassword() {
		return password;
	}
	
	public int getPlayers() {
		return players;
	}
}
