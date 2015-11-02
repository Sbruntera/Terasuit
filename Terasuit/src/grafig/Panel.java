package grafig;

import inGame.Funktions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel{

	private static final long serialVersionUID = 1L;
	private Image img = null;
	SetButtons buttons = new SetButtons();
	Funktions func = new Funktions();
	
	private int squareX = 0;
	private int squareY = -1;
	int minY;
	int h;
	int minX;
	int w;
	
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

		buttons.setbuttons(this, picName, loader, func);
		if (picName.equals("Wallpaper/Maingame.png")){
			addListenersForMouse();
		}	
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D f2 = (Graphics2D)g;
		f2.drawImage(img, 0,0,null);
		paintChildren(g);
	    g.setColor(Color.RED);
	    g.drawRect(minX,minY,w,h);
	}

	private void addListenersForMouse() {
	    addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent e) {
	        	squareX = e.getX();
	        	squareY = e.getY();
	        }
	        
	    	public void mouseReleased(MouseEvent e) {
	    		func.deMarkEntittys();
	    		func.findAllEntitys(minX, minY, w, h);
	    		minY = 0;
	    		minX = -1;
	    		h = 0;
	    		w = 0;
	    		repaint();
	    	}
	    });
	
	    addMouseMotionListener(new MouseAdapter() {
	        public void mouseDragged(MouseEvent e) {
	        	drawSquare(e.getX(),e.getY());
	        }
	    });  
	}
	
	private void drawSquare(int x, int y) {
	
	    if (squareX > x) {     	
	    	this.w = (x - squareX) * -1;
	    	this.minX = x;
	    }
	    else {
	    	this.w = x-squareX;
	    	this.minX = squareX;
	    }
	    if (squareY > y) {     	
	    	this.h = (y - squareY) * -1;
	    	this.minY = y;
	    }
	    else {
	    	this.h = y-squareY;
	    	this.minY = squareY;
	    }
	    repaint();
	}

	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	}  
	
}
