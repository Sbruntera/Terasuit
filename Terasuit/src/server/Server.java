package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import connection.Connection;
import connection.MenuAnalyser;
import logic.BCrypt;

/**
 * 
 * @author Simeon, Jan-Philipp
 *
 */
public class Server {
	private final ServerSocket server;
	private Socket socket;
	private DB db;
	ArrayList<Thread> connectionThreads = new ArrayList<Thread>();
	ArrayList<Connection> connections = new ArrayList<Connection>();
	short idIterator;
	ArrayList<GameServer> games = new ArrayList<GameServer>();
	ArrayList<Lobby> lobbys = new ArrayList<Lobby>();

	public Server(int port) throws IOException {
		lobbys.add(new Lobby(this, null, "na", null, Map.Nightsun, (byte) 1));
		server = new ServerSocket(port);
		db = new DB();
		acceptConnection();
		idIterator = 0;
	}

	/**
	 * Wartet auf einen neuen Nutzer und baut eine Verbindung mit dem Nutzer auf
	 */
	private void acceptConnection() {
		try {
			while (true) {
				socket = server.accept();
				createConnection(socket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Baut eine Verbindung mit dem Nutzer auf
	 * 
	 * @param socket
	 */
	private void createConnection(Socket socket) {
		Connection connection = new Connection(socket, this, idIterator);
		connection.setAnalyser(new MenuAnalyser(this, connection, idIterator));
		idIterator++;
		connections.add(connection);
		Thread connectionThread = new Thread(connection);
		connectionThreads.add(connectionThread);
		connectionThread.start();
	}

	/**
	 * registriert einen Nutzer in der Datenbank
	 * 
	 * @param name
	 *            : Name des Nutzers
	 * @param pw
	 *            : Passwort des Nutzers als Hash
	 * @param email
	 *            : E-Mail des Nutzers
	 * @param mode
	 *            : Privilegien
	 * @param id
	 *            : ID des Nutzers
	 */
	public void registerClient(String name, String pw, String email,
			String mode, int id) {
		String hashed = BCrypt.hashpw(pw, BCrypt.gensalt());
		if (!db.search(name)) {
			db.addUser(name, hashed, email, mode);
		} else {
			// TODO: Fehlermeldung
			connections.get(id).sendLogin();
		}
	}

	/**
	 * Loggt einen Nutzer ein
	 * 
	 * @param name
	 * @param pw
	 * @param id
	 * @return
	 */
	public boolean loginClient(String name, String pw, int id) {
		if (db.getUser(name) != null) {
			if (BCrypt.checkpw(pw, db.getUser(name))) { // hashed: hash steht in
														// der DB
				return (true);
			} else {
				return (false);
			}
		} else {
			return (false);
		}
	}

	public void diconnect(int id) {
		connections.get(id).close();
	}

	/**
	 * Gibt die Liste aller aktiven Lobby zurück
	 * 
	 * @param filter
	 *            : Filter nach dem die Lobbys gefiltert werden sollen
	 * @return
	 */
	public Lobby[] getLobbylist(Filter filter) {
		ArrayList<Lobby> filteredList = new ArrayList<Lobby>();
		if (filter != null) {
			for (Lobby lobby : lobbys) {
				if ((lobby.getName().contains(filter.getNameContains()))
						&& (filter.getMaxPlayers() >= lobby
								.getNumberOfPlayers() && lobby
								.getNumberOfPlayers() >= filter.getMinPlayers())
						&& !(filter.isNoPassword() && lobby.hasPassword())
						&& (filter.getMap() == null || filter.getMap() == lobby
								.getMap())) {
					filteredList.add(lobby);
				}
			}
		} else {
			filteredList = (ArrayList<Lobby>) lobbys.clone();
		}
		Lobby[] filteredArray = filteredList.toArray(new Lobby[filteredList.size()]);
		return filteredArray;
	}

	/**
	 * Erstellt eine Lobby
	 * 
	 * @param connection
	 *            : Verbindung des Host
	 * @param name
	 *            : Name der Lobby
	 * @param password
	 *            : Passwort der Lobby
	 * @param map
	 *            : Karte der Lobby
	 */
	public Lobby createLobby(Connection connection, String name,
			String password, Map map) {
		byte idGenerator = 0;
		Lobby lobby = new Lobby(this, connection, name, password, map,
				idGenerator);
		idGenerator++;
		lobbys.add(lobby);
		return lobby;
	}

	/**
	 * Löscht eine Lobby
	 * 
	 * @param lobby
	 *            : zu entfernende Lobby
	 */
	public void removeLobby(Lobby lobby) {
		lobbys.remove(lobby);
	}

	public boolean hasLobby(byte id) {
		return lobbys.contains(id);
	}

	/**
	 * Gibt eine Lobby zurück
	 * 
	 * @param id
	 *            : ID der Lobby
	 * @return
	 */
	public Lobby getLobby(byte id) {
		if (lobbys.contains(id)) {
			return lobbys.get(id);
		} else {
			return null;
		}
	}

	public void createGame(Lobby lobby) {
		lobbys.remove(lobby);
		GameServer game = new GameServer(lobby.getConnections(), this);
		games.add(game);
		for (Connection c : lobby.getConnections()) {
			if (c != null) {
				c.sendStarting(game);
			}
		}
	}
}
