package inGame;

import grafig.Panel;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CreateUnit {

	Unit unit = new Unit();
	JLabel label = new JLabel("");
	SelectedUnits select = new SelectedUnits();
	ImageManipulator imgManipulator = new ImageManipulator();
	int default_spawn_left_X = 300;
	int default_spawn_left_Y = 300;

	public void createEntity(Panel panel, String Entitytype, ArrayList<Unit> entity, int color) {
		
		unit = new Unit();
		unit.setEntityNummer(entity.size() + 1);

		int randValue1 = random(50);
		int randValue2 = random(200);

		unit.setEntityPositionX(default_spawn_left_X + randValue1);
		unit.setEntityPositionY(default_spawn_left_Y + randValue2);

		// ImageIcon pic = new MirrorImageIcon(Entitytype);
		// label.setIcon(pic);

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(Entitytype));
		} catch (IOException e) {
		}

		img = imgManipulator.setnewColors(img, color);
		Image img2 = imgManipulator.setnewDimension(img, Entitytype);
		img = this.toBufferedImage(img2);
		img = imgManipulator.setSelection(img);
		
		img = imgManipulator.rotate(img);
		
		ImageIcon pic = new ImageIcon(img);
		label = new JLabel("");
		label.setIcon(pic);

		label.setBounds(default_spawn_left_X + (randValue1),
				default_spawn_left_Y + (randValue2), pic.getIconWidth(),
				pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent objUnit) {
				select.getUnit(entity, objUnit, panel);
			}
		});
		unit.setLabel(label);
		entity.add(unit);
		panel.add(label);
		panel.repaint();
		return;
	}

	public int random(int zahl) {
		int rand = (int) (Math.random() * zahl) + 1;
		return rand;
	}
	
	public  BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

}
