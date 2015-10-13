package inGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CreateMainGame extends JFrame{
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public static void main(String[] args) {
		run();
	}

	public static void run() {
		try {
			CreateMainGame frame = new CreateMainGame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void pain(Graphics g){
		g.drawRect(10, 10, 10, 10);
	}
	
	public CreateMainGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(new MyPanel());
	}
	
	class MyPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private int squareX = 0;
	    private int squareY = -1;
	    int minY;
	    int h;
	    int minX;
	    int w;

	    public MyPanel() {

	        addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {
	            	squareX = e.getX();
	            	squareY = e.getY();
	            }
	            
	        	public void mouseReleased(MouseEvent e) {
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
	    

	    public Dimension getPreferredSize() {
	        return new Dimension(250,200);
	    }
	    
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);       
	        g.setColor(Color.RED);
	        g.drawRect(minX,minY,w,h);
	    }  
	}

}
