package de.szut.client.grafik;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import de.szut.client.ingame.Funktions;

/**
 * 
 * @author Alexander
 *
 */
public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image img = null;
	SetButtons buttons = new SetButtons();

	private int squareX = 0;
	private int squareY = -1;
	int minY;
	int h;
	int minX;
	int w;

	public Panel(String picName, Funktions func, int HEIGHT, int WIGTH,
			Loader loader) {
		super();
		setFocusable(true);
		if (picName.equals("Wallpaper/Field.png")) {
			// Ausnamhe für das Feld
		} else if (picName.equals("Wallpaper/Console.png")) {
			// Ausnahme für die Konsole
		} else {
			setPreferredSize(new Dimension(HEIGHT - 2, WIGTH - 2));
		}

		// Das Spiel wird gestartet, es werden das Feld und die Konsole
		// inizalisiert und auf das Feld der Listener aktiviert
		if (picName.equals("Wallpaper/Maingame.png")) {

			setLayout(new BorderLayout(0, 0));

			Panel field = new Panel("Wallpaper/Field.png", func, HEIGHT, WIGTH,
					loader);
			field.setPreferredSize(new Dimension(1600, 590));
			field.addListenersForMouse(loader);
			JScrollPane scrollPane = new JScrollPane(field);
			scrollPane
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane
					.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			add(scrollPane, BorderLayout.CENTER);

			Panel console = new Panel("Wallpaper/Console.png", func, HEIGHT,
					WIGTH, loader);
			console.setLayout(null);
			console.setPreferredSize(new Dimension(WIGTH, 185));
			add(console, BorderLayout.SOUTH);

			loader.init(this, field, console, func, scrollPane);

		} else {
			setLayout(null);
			try {
				ImageIcon u = new ImageIcon(picName);
				img = u.getImage();
			} catch (Exception e) {
				System.out
						.println("<ERROR> Kein Bild für diese Aktion vorhanden!!!");
			}
			loader.setPanel(this);
			buttons.setbuttons(this, picName, loader, func);
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D f2 = (Graphics2D) g;
		f2.drawImage(img, 0, 0, null);
		paintChildren(g);
		g.setColor(Color.RED);
		g.drawRect(minX, minY, w, h);
	}

	private void addListenersForMouse(Loader loader) {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				squareX = e.getX();
				squareY = e.getY();
			}

			public void mouseReleased(MouseEvent e) {
				loader.mousListernerAction(minX, minY, w, h);
				minY = 0;
				minX = -1;
				h = 0;
				w = 0;
				repaint();
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				drawSquare(e.getX(), e.getY());
			}
		});
	}

	private void drawSquare(int x, int y) {

		if (squareX > x) {
			this.w = (x - squareX) * -1;
			this.minX = x;
		} else {
			this.w = x - squareX;
			this.minX = squareX;
		}
		if (squareY > y) {
			this.h = (y - squareY) * -1;
			this.minY = y;
		} else {
			this.h = y - squareY;
			this.minY = squareY;
		}
		repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
