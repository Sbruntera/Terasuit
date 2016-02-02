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
	 * @param name: Name des Nutzers
	 * @param pw: Passwort des Nutzers als Hash
	 * @param email: E-Mail des Nutzers
	 * @param mode: Privilegien
	 * @param id: ID des Nutzers
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
		// connections.get(id).close();
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
						&& (filter.getMap() == lobby.getMap())
						&& (filter.getMaxPlayers() >= lobby
								.getNumberOfPlayers() && lobby
								.getNumberOfPlayers() >= filter.getMinPlayers())
						&& !(filter.isNoPassword() && lobby.hasPassword())) {
					filteredList.add(lobby);
				}
			}
		} else {
			filteredList = (ArrayList<Lobby>) lobbys.clone();
		}
		Lobby[] filteredArray = new Lobby[filteredList.size()];

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
	public void createLobby(Connection connection, String name,
			String password, Map map) {
		lobbys.add(new Lobby(this, connection, name, password, map));
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

	/**
	 * Gibt eine Lobby zurück
	 * 
	 * @param id
	 *            : ID der Lobby
	 * @return
	 */
	public Lobby getLobby(byte id) {
		return lobbys.get(id);
	}
}
