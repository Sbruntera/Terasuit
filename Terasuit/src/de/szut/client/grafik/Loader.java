package de.szut.client.grafik;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import de.szut.client.ingame.Funktions;
import de.szut.client.ingame.Game;
import de.szut.client.logic.Debugger_Thread;
import de.szut.client.logic.Lobby;
import de.szut.client.logic.ServerConnection;

/**
 * 
 * @author Alexander, Jan-Philipp
 *
 */
public class Loader {

	Debugger_Thread debugger = new Debugger_Thread();
	public ServerConnection connection = new ServerConnection(this);
	Thread connectionThread = new Thread(connection);
	Funktions func = new Funktions();
	public Game game;

	public final String Mainpage = "Wallpaper/Start_Hintergrund.png";
	public final String Lobbypage = "Wallpaper/serverlist.png";
	public final String Gamepage = "Wallpaper/Maingame.png";
	public final String Grouppage = "Wallpaper/Lobby.png";
	public final String Grouppage_owner = "Wallpaper/Lobby_BESITZER.png";
	public final String Statspage = "Wallpaper/statspage.png";
	public final String Loginpage = "";
	public final String Registerpage = "";
	public final String Optionpage = "";

	public static int HEIGHT = 1024;
	public static int WIGTH = 768;

	JFrame window = new JFrame("Terasuit");
	private Panel panel;

	/**
	 * Standart Einstellungen für des JFrame
	 */
	public void print() {
		window.setContentPane(new Panel(Mainpage, func, HEIGHT, WIGTH, this));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		if (connection.isServerAccess()) {
			connectionThread.start();
		}
	}

	/**
	 * Wechselt das ContentPane auf dem Panel anhand des gegebenen
	 * Hintergrundbilds
	 * 
	 * @param newPage
	 *            Pfad des Hintergrundbildes
	 */
	public void switchPanel(String newPage) {
		if (newPage == Mainpage)
			window.setContentPane(new Panel(Mainpage, func, HEIGHT, WIGTH, this));
		else if (newPage == Lobbypage)
			window.setContentPane(new Panel(Lobbypage, func, HEIGHT, WIGTH,
					this));
		else if (newPage == Gamepage) {
			func.reset();
			window.setContentPane(new Panel(Gamepage, func, HEIGHT, WIGTH, this));
		} else if (newPage == Grouppage)
			window.setContentPane(new Panel(Grouppage, func, HEIGHT, WIGTH,
					this));
		else if (newPage == Grouppage_owner)
			window.setContentPane(new Panel(Grouppage_owner, func, HEIGHT,
					WIGTH, this));
		else if (newPage == Statspage)
			window.setContentPane(new Panel(Statspage, func, HEIGHT, WIGTH,
					this));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}

	/**
	 * Startet die GUI für das Spiel
	 * 
	 * @param panel
	 *            Background Panel
	 * @param field
	 *            Spielfeld Panel
	 * @param console
	 *            Consolen Panel
	 * @param func
	 *            Funktionen
	 * @param scrollPane 
	 */
	public void init(Panel panel, Panel field, Panel console, Funktions func, JScrollPane scrollPane) {
		game = new Game();
		game.init(panel, field, console, this, func, 1, scrollPane);
	}

	public void startDebugger() {
		debugger.startRound();
	}

	/**
	 * Beendet das Progamm
	 */
	public void exit() {
		System.exit(0);
	}

	/**
	 * MouseListener für das Auswahlfeld
	 * 
	 * @param minX
	 * @param minY
	 * @param w
	 * @param h
	 */
	public void mousListernerAction(int minX, int minY, int w, int h) {
		game.searchForEntitysInRectangle(minX, minY, w, h);
	}

	/**
	 * Setzen des Chattextes in der Lobby
	 * 
	 * @param msg
	 *            Nachricht des Chats
	 */
	public void setText(String msg) {
		panel.buttons.setText(msg);

	}

	/**
	 * Setzen des Aktuellen Panels
	 * 
	 * @param panel
	 *            Aktuell genutztes Panel
	 */
	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	/**
	 * Setzen der Chatnachricht in Game
	 * 
	 * @param msg
	 *            Nachricht an den Chat
	 */
	public void setGameText(String msg) {
		game.setText(msg);

	}

	/**
	 * Ruft die Update Methode für das Updaten der Lobby Liste in der Gui auf
	 * 
	 * @param lobbyList
	 *            Liste aller Lobbys auf dem Server
	 */
	public void updateLobbyList(Lobby[] lobbyList) {
		panel.buttons.updateLobbyList(lobbyList);
	}

	/**
	 * Ruft die Update Methoden für die Lobby auf um die Spieler anzuzeigen
	 * 
	 * @param players
	 *            Liste aller Spieler in einer Lobby
	 * @param host
	 *            boolean ob der Spieler Host der Lobby ist
	 */
	public void updatePlayerList(String[] players, boolean host) {
		if (host) {
			panel.buttons.updateCombo(players);
		} else {
			panel.buttons.updateLabels(players);
		}
	}

	/**
	 * Gibt der GUI bekannt das der Spieler eingeloggt wurde
	 * 
	 * @param name
	 *            Name des Eingeloggten
	 */
	public void loggIn(String name) {
		panel.buttons.loggedIn(name);
	}

	/**
	 * Ruft die Methode zum anzeigen der Stats auf der GUI auf
	 * 
	 * @param r
	 *            Liste aller Stats des Spieler vom Server
	 */
	public void showStats(String[][] r) {
		panel.buttons.showStats(r);
	}
	
	public void feedback(String string) {
		JOptionPane.showMessageDialog(panel, string, "A Problem occurred", JOptionPane.WARNING_MESSAGE);	
	}
}
