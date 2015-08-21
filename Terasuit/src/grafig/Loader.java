package grafig;

import javax.swing.JFrame;

public class Loader {
	
	public void print() {
		System.out.println("ich bin ein schönes Bild");
		
		
		JFrame window = new JFrame("Terasuit");
		window.setContentPane(new StartPanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}
