package grafig;

import javax.swing.JFrame;

public class Loader {
	
	// ------------------------ Bereits Fertige Bilder
	public final String Mainpage = "Wallpaper/Start_Hintergrund.png";
	public final String Lobbypage = "Wallpaper/serverlist.png";
	public final String Gamepage = "Wallpaper/Maingame.png";
	public final String Grouppage = "Wallpaper/Lobby.png";
	public final String Grouppage_owner = "Wallpaper/Lobby_BESITZER.png";
	
	// ------------------------ In Bearbeitung

	
	// ------------------------ Fehlende Bilder
	
	public final String Loginpage = "";
	public final String Registerpage = "";
	public final String Optionpage = "";

	
	public static int HEIGHT = 1024;
	public static int WIGTH = 768;

	JFrame window = new JFrame("Terasuit");
	
	public void print() {
		
		window.setContentPane(new Panel(Gamepage, HEIGHT, WIGTH, this));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		
	}
	
	public void switchPanel(String newPage){
		if (newPage == Mainpage) window.setContentPane(new Panel(Mainpage, HEIGHT, WIGTH, this));
	    else if(newPage == Lobbypage) window.setContentPane(new Panel(Lobbypage, HEIGHT, WIGTH, this));
		else if(newPage == Gamepage) window.setContentPane(new Panel(Gamepage, HEIGHT, WIGTH, this));
		else if(newPage == Grouppage) window.setContentPane(new Panel(Grouppage, HEIGHT, WIGTH, this));
		else if(newPage == Grouppage_owner) window.setContentPane(new Panel(Grouppage_owner, HEIGHT, WIGTH, this));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
	
	public void exit(){
		System.exit(0);
	}
}
