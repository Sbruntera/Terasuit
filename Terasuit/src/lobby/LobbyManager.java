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

	public GameServer[] getServerList(Filter filter) {

		ArrayList<GameServer> filteredList = (ArrayList<GameServer>) serverList.clone();
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
		GameServer[] filteredArray = filteredList.toArray(new GameServer[filteredList.size()]);
		return filteredArray;
	}
}