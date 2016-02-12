package grafig;

import inGame.Funktions;
import inGame.Game;

import javax.swing.JFrame;

import logic.Debugger_Thread;
import logic.ServerConnection;

public class Loader {
	
	Debugger_Thread debugger = new Debugger_Thread();
	public ServerConnection connection = new ServerConnection(this);
	Thread connectionThread = new Thread(connection);
	Funktions func = new Funktions();
	Game game;

	public final String Mainpage = "Wallpaper/Start_Hintergrund.png";
	public final String Lobbypage = "Wallpaper/serverlist.png";
	public final String Gamepage = "Wallpaper/Maingame.png";
	public final String Grouppage = "Wallpaper/Lobby.png";
	public final String Grouppage_owner = "Wallpaper/Lobby_BESITZER.png";
	public final String Loginpage = "";
	public final String Registerpage = "";
	public final String Optionpage = "";
	
	public static int HEIGHT = 1024;
	public static int WIGTH = 768;

	JFrame window = new JFrame("Terasuit");
	private Panel panel;
	
	public void print() {

		window.setContentPane(new Panel(Mainpage, func, HEIGHT, WIGTH, this));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		connectionThread.start();
	}
	
	public void switchPanel(String newPage){
		if (newPage == Mainpage) window.setContentPane(new Panel(Mainpage, func, HEIGHT, WIGTH, this));
	    else if(newPage == Lobbypage) window.setContentPane(new Panel(Lobbypage, func, HEIGHT, WIGTH, this));
		else if(newPage == Gamepage) window.setContentPane(new Panel(Gamepage, func, HEIGHT, WIGTH, this));
		else if(newPage == Grouppage) window.setContentPane(new Panel(Grouppage, func, HEIGHT, WIGTH, this));
		else if(newPage == Grouppage_owner) window.setContentPane(new Panel(Grouppage_owner, func, HEIGHT, WIGTH, this));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
	
	public void init(Panel panel, Panel field, Panel console, Funktions func){
		game = new Game();
		game.init(panel, field, console, this, func, 1);
	}
	
	public void startDebugger(){
		debugger.startRound();
	}
	
	public void changeTo(String page){
		switchPanel(page);
	}
	
	public void exit(){
		System.exit(0);
	}
	
	public void mousListernerAction(int minX, int minY, int w, int h){
		game.searchForEntitysInRectangle(minX, minY, w, h);
	}

	public void setText(String msg) {
		panel.buttons.setText(msg);
		// TODO Auto-generated method stub
		
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}
	
}
