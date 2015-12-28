package inGame;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

/**
 * 
 * @author Sbrun
 * @version 0.1 !Achtung! Frisst eventuell Rescoursen
 */
public class ImageIconMirror {

	// Spiegel erhaltene ImageIcons

	@SuppressWarnings("serial")
	class MirrorImageIcon extends ImageIcon {

		public MirrorImageIcon(String filename) {
			super(filename);
		}

		@Override
		public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.translate(getIconWidth(), 0);
			g2.scale(-1, 1);
			super.paintIcon(c, g2, x, y);
		}

	}

}

// Heller/Dunkler
// http://stackoverflow.com/questions/12980780/how-to-change-the-brightness-of-an-image
// Graphics g = img.getGraphics();
// float percentage = .5f; // 50% bright - change this (or set dynamically) as
// you feel fit
// int brightness = (int)(256 - 256 * percentage);
// g.setColor(new Color(0,0,0,brightness));
// g.fillRect(0, 0, img.getWidth(), img.getHeight());
