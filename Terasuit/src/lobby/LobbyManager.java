package lobby;

import java.util.ArrayList;

/**
 * Verwaltet die Liste  der offenen Spiele
 * @author Simeon Kublenz
 * 
 */
public class LobbyManager {
	
	private ArrayList<GameServer> serverList;
	
	public void addServer() {
		//TODO
	}
	
	public void removeServer(GameServer server) {
		if (!server.isRunning()) {
			serverList.remove(server);
		}
	}
	
	public ArrayList<GameServer> getServerList(Filter filter) {
		if (filter != null) {
			ArrayList<GameServer> filteredList = new ArrayList<GameServer>();
			for (GameServer server : serverList) {
				//TODO Filter
			}
			return filteredList;
		}
		return serverList;
	}
}