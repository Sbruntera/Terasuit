package grafig;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel{

	private static final long serialVersionUID = 1L;
	private Image img = null;
	SetButtons buttons = new SetButtons();
	
	public Panel(String picName, int HEIGHT, int WIGTH, Loader loader) {
		super();
		setPreferredSize(new Dimension(HEIGHT, WIGTH));
		setFocusable(true);
		setLayout(null);
		try {
			ImageIcon u = new ImageIcon(picName);
			img = u.getImage();			
		} catch (Exception e) {
			System.out.println("<ERROR> Kein Bild für diese Aktion vorhanden!!!");
		}

		buttons.setbuttons(this, picName, loader);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D f2 = (Graphics2D)g;
		f2.drawImage(img, 0,0,null);
		paintChildren(g);
	}
}
