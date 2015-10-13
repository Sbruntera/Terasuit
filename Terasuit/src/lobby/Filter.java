package lobby;

/**
 * Ein Filter um die lobbyList, dem Nutzer nach seien Ansprüchen gefiltert, zu übertragen
 * @author Simeon Kublenz
 * 
 */
public class Filter {
	
	private boolean noPassword;
	private String nameContains;
	private Map map;
	private int minPlayers;
	private int maxPlayers;
	
	
	public Filter(boolean noPassword, String nameContains, Map map, int minPlayers, int maxPlayers) {
		this.noPassword = noPassword;
		this.nameContains = nameContains;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}
	
	public boolean isNoPassword() {
		return noPassword;
	}
	
	public String getNameContains() {
		return nameContains;
	}
	
	public Map getMap() {
		return map;
	}
	
	public int getMinPlayers() {
		return minPlayers;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
}