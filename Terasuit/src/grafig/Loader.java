package grafig;

import javax.swing.JFrame;

public class Loader {
	
	private static final String Mainpage2 = "Wallpaper/Start_Hintergrund.png";
	public static int HEIGHT = 500;
	public static int WIGTH = 450;
	
	public void print() {

		JFrame window = new JFrame("Terasuit");
		window.setContentPane(new Panel(Mainpage2, HEIGHT, WIGTH));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setVisible(true);
	}
}
