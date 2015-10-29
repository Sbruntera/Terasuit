package inGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DrawRect extends JFrame{
	

	private static final long serialVersionUID = 1L;
//	private JPanel contentPane;
	SelectedUnits select = new SelectedUnits();
	Funktions func = new Funktions();

	
//	public static void main(String[] args) {
//		System.out.println("hola2");
//		run();
//	}
//
//	public static void run() {
//		try {
//			CreateMainGame frame = new CreateMainGame();
//			System.out.println("Hola");
//			frame.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void pain(Graphics g){
//		g.drawRect(10, 10, 10, 10);
//	}
//	
//	public CreateMainGame() {
//		System.out.println("hollla3");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//		contentPane.add(new MyPanel());
//	}
	
//	class MyPanel extends JPanel {
//
//		private static final long serialVersionUID = 1L;
//		private int squareX = 0;
//	    private int squareY = -1;
//	    int minY;
//	    int h;
//	    int minX;
//	    int w;
//
//	    public MyPanel() {
//
//	        addMouseListener(new MouseAdapter() {
//	            public void mousePressed(MouseEvent e) {
//	            	squareX = e.getX();
//	            	squareY = e.getY();
//	            }
//	            
//	        	public void mouseReleased(MouseEvent e) {
//	        		func.no();
//	        		select.getGroupOfUnits(entity, x, y, DO_NOTHING_ON_CLOSE, NW_RESIZE_CURSOR);
//	        		minY = 0;
//	        		minX = -1;
//	        		h = 0;
//	        		w = 0;
//	        		repaint();
//	        	}
//	        });
//
//	        addMouseMotionListener(new MouseAdapter() {
//	            public void mouseDragged(MouseEvent e) {
//	            	drawSquare(e.getX(),e.getY());
//	            }
//	        });
//	        
//	    }
//
//	    private void drawSquare(int x, int y) {
//
//	        if (squareX > x) {     	
//	        	this.w = (x - squareX) * -1;
//	        	this.minX = x;
//	        }
//	        else {
//	        	this.w = x-squareX;
//	        	this.minX = squareX;
//	        }
//	        if (squareY > y) {     	
//	        	this.h = (y - squareY) * -1;
//	        	this.minY = y;
//	        }
//	        else {
//	        	this.h = y-squareY;
//	        	this.minY = squareY;
//	        }
//	        repaint();
//	    }
//
//	    public Dimension getPreferredSize() {
//	        return new Dimension(250,200);
//	    }
//	    
//	    protected void paintComponent(Graphics g) {
//	        super.paintComponent(g);       
//	        g.setColor(Color.RED);
//	        g.drawRect(minX,minY,w,h);
//	    }  
//	}

}
