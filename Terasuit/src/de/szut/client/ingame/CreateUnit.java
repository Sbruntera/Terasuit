package de.szut.client.ingame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import de.szut.client.grafik.Panel;
import de.szut.client.logic.UnitData;
import de.szut.client.logic.UnitObject;
import de.szut.client.logic.UnitPics;

public class CreateUnit {

	Unit unit = new Unit();
	JLabel label = new JLabel("");
	SelectedUnits select = new SelectedUnits();
	ImageManipulator imgManipulator = new ImageManipulator();
	UnitData UnitTable = new UnitData();
	
	public CreateUnit(){
		UnitTable.createUnitData();
	}
	
	public Unit createEntity(Panel field, Game game, String Entitytype, int color, boolean airUnit, Funktions funktions, short unitID, Point position, UnitPics pics) {
		
		// Generiert eine neue Hülle und gibt ihre eine ID
		unit = new Unit();
		unit.setEntityNummer(unitID);
		
		// Position wird festgelegt
		unit.setEntityPositionX(position.x);
		unit.setEntityPositionY(position.y);
		
		// Setzen des Bildes
		ImageIcon pic = pics.getEntityPic(Entitytype, color, ((color-1)>>1)==1, false);
		label = new JLabel("");
		label.setIcon(pic);
		
		// Aktionlisener
		label.setBounds(position.x - pic.getIconWidth()/2, position.y - pic.getIconHeight()/2, pic.getIconWidth(), pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent objUnit) {
				game.selectSingleEntity(objUnit);
			}
		});
		
		// Datenbank wird geladen (Default Einheitenwerte)
		UnitObject unitData;
		unitData = UnitTable.returnUnitData(this.splitUp(Entitytype));
		
		// Hülle wird mit Attributen belegt
		unit.setFlyingEntity(airUnit);
		unit.setEntityRushLeft(((color-1)>>1)==1);	
		unit.setEntitymembership(color);
		unit.setLabel(label);
		unit.setEntityname(Entitytype);
		unit.setEntityFirepower(unitData.getDmg());
		unit.setEntityFirerange(unitData.getRange());
		unit.setEntityLive(unitData.getLive());
		unit.setEntitySplashDmg(unitData.getSplashDamage());
		unit.setEntityReloadTimer(unitData.getRpm());
		unit.setEntitySpeed(unitData.getSpeed());
		unit.setCanAttackGround(unitData.canAttackGround());
		unit.setCanAttackAir(unitData.canAttackAir());
		unit.setBulletSpeed(unitData.getBulletSpeed());
		unit.setPrice(unitData.getPrice());
		field.add(label);
		field.repaint();
		return unit;
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
	
	public String splitUp(String Unitname){
		String[] parts = Unitname.split("/");
		Unitname = parts[2];
		Unitname = Unitname.substring(0, Unitname.length()-4);	
		return Unitname;
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
