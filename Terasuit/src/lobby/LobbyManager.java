package lobby;

import java.util.ArrayList;

/**
 * Verwaltet die Liste der offenen Spiele
 * 
 * @author Simeon Kublenz
 * 
 */
public class LobbyManager {
	
	private ArrayList<GameServer> serverList;
	
	public void addServer() {
		// TODO
	}
	
	protected void removeServer(GameServer server) {
		serverList.remove(server);
	}
	
	public GameLobby[] getServerList(Filter filter) {
		
		ArrayList<GameServer> filteredList = new ArrayList<GameServer>();
		if (filter != null) {
			for (GameServer server : serverList) {
				if ((server.getName().contains(filter.getNameContains()))
						&& (filter.getMap() == server.getMap())
						&& (filter.getMaxPlayers() >= server.getNumberOfPlayers() && server.getNumberOfPlayers() >= filter.getMinPlayers())
						&& !(filter.isNoPassword() && server.getPassword() == null)) {
					filteredList.add(server);
				}
			}
		}
		else {
			filteredList = (ArrayList<GameServer>) serverList.clone();
		}
		GameLobby[] filteredArray = new GameLobby[filteredList.size()];
		for (GameServer server : filteredList) {
			filteredArray[filteredList.indexOf(server)] = new GameLobby(server.getMap(), server.getName(), server.getHostname(), server.hasPassword(), server.getNumberOfPlayers());
		}
		return filteredArray;
	}
}