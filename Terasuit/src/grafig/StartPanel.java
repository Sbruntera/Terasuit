package grafig;

import java.awt.Dimension;

import javax.swing.JPanel;

public class StartPanel extends JPanel{
	
	public static int HEIGHT = 300;
	public static int WIGTH = 450;
	
	public StartPanel() {
		super();
		setPreferredSize(new Dimension(HEIGHT, WIGTH));
		setFocusable(true);
		requestFocus();
	}

}
