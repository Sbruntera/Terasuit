package grafig;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class StartPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	private Image img = null;
	
	public StartPanel(String picName, int HEIGHT, int WIGTH) {
		super();
		setPreferredSize(new Dimension(HEIGHT, WIGTH));
		setFocusable(true);
		requestFocus();

		ImageIcon u = new ImageIcon(picName);
		img = u.getImage();
	}
	
	
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D f2 = (Graphics2D)g;
		f2.drawImage(img, 0,0,null);

	}

}
