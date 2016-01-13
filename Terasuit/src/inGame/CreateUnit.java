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
	int default_spawn_left = 300;
	int default_spawn_leftside_Ground = 350;
	int default_spawn_leftside_Air = 150;

	public ArrayList<Unit> createEntity(Panel panel, String Entitytype, ArrayList<Unit> entity, int color, boolean airUnit) {
		
		unit = new Unit();
		unit.setEntityNummer(entity.size() + 1);

		int unit_X_Position = 0;
		int unit_Y_Position = 0;
		
		if (airUnit) {
			unit_X_Position = default_spawn_left + random(70);
			unit_Y_Position = default_spawn_leftside_Air + random(150);
		}else{
			unit_X_Position = default_spawn_left + random(70);
			unit_Y_Position = default_spawn_leftside_Ground + random(150);
		}
		unit.setEntityPositionX(unit_X_Position);
		unit.setEntityPositionY(unit_Y_Position);
		
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

		label.setBounds(unit_X_Position, unit_Y_Position, pic.getIconWidth(), pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent objUnit) {
				select.getUnit(entity, objUnit, panel);
			}
		});
		
		
		unit.setEntityRushLeft(true);
		unit.setEntitymembership(color);
		unit.setLabel(label);
		unit.setEntityname(Entitytype);
		entity.add(unit);
		panel.add(label);
		panel.repaint();
		return entity;
	}
	
	public BufferedImage mark(String Entitytype, boolean directionLeft, int color, boolean deMark){

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(Entitytype));
		} catch (IOException e) {
		}
		
		img = imgManipulator.setnewColors(img, color);
		if (deMark == true){

		}else{
			img = imgManipulator.setSelection(img);
		}
		
		Image img2 = imgManipulator.setnewDimension(img, Entitytype);
		img = this.toBufferedImage(img2);
	
		
		if (directionLeft){
			img = imgManipulator.rotate(img);
		}
		
		return img;
	}
	
	
	// Zufällige Platzierung in der Welt
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
