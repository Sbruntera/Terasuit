package de.szut.client.logic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import de.szut.client.ingame.ImageManipulator;
/**
 * Hier werden alle Einheitenbilder aus Defaultbildern generiert
 * Aus 1 Bild werden 16 Bilder erzeugt:
 * -> 4 verschiedene Farben
 * -> 2 richtungen
 * -> 2 Helligkeitsstufen
 * @author Sbrun
 */
public class UnitPics {
	
	String picLocation = "";
	String picName = "";
	ImageManipulator imgMani = new ImageManipulator();
	String [] entityGroundList = {"Marine", "Chronite Tank", "Gröditz", "A25-Roman", "Hover Tank", "Meditec", "Modified Sakata", "Sniper", "Sphinx", "Sakata-MK2", "Sakata", "Gladiator", "Sakata Spider"};
	String [] entityAirList = {"Scout", "Phantom", "Black Queen", "Saint", "Modified Phantom"};
	HashMap<String, BufferedImage> UnitPicHash = new HashMap<String, BufferedImage>();
	
	/**
	 * Läuft über die Array mit den Einheitennamen, sucht sich die Bilder auf und 
	 * führt Funktionen aus, die die Bilder manipulieren. Diese werden dann in einer
	 * Hashmap gespeichert <String(Name der Einheit als Key), Image>
	 */
	public void generateAllEntityPictures(){
		for (int i = 0; i != entityGroundList.length; i++){
			picLocation = "Unit/Ground/" + entityGroundList[i] + ".png";
			picName = entityGroundList[i];
			this.generate(UnitPicHash);
		}
		for (int i = 0; i != entityAirList.length; i++){
			picLocation = "Unit/Air/" + entityAirList[i] + ".png";
			picName = entityAirList[i];
			this.generate(UnitPicHash);
		}
	}
	
	/**
	 * Sucht aus der Hashmap das passende Bild
	 * @param EnityName
	 * @param color
	 * @param left
	 * @param mark
	 * @return ImageIcon
	 */
	public ImageIcon getEntityPic(String EnityName, int color, boolean left, boolean mark){
		
		String []splitEntityName = EnityName.split("/");
		String EnityName2 = splitEntityName[2];
		splitEntityName = EnityName2.split("\\.");

	    String searchString = splitEntityName[0] + "_" + color;
		if (left && !mark){
			searchString = searchString + "_turn";	
		}else if (!left && !mark){
		}else if (left && mark){
			searchString = searchString + "_mark_turn";
		}else{
			searchString = searchString + "_mark";
		}
		return new ImageIcon(UnitPicHash.get(searchString));
	}
	
	/**
	 * Hier werden alle 16 Varianten der Einheit erzeugt
	 * @param unitPicHash
	 * @return
	 */
	private HashMap<String, BufferedImage> generate(HashMap<String, BufferedImage> unitPicHash){

		// Bild der Einheite wird geladen
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(picLocation));
		} catch (IOException e) {}
		
		// Vier verschiedene Farben werden generiert (Rechts)
		for (int n = 1; n != 5; n++){
			img = imgMani.setnewColors(img, n);
			Image img2 = imgMani.setnewDimension(img, picLocation);
			img = this.toBufferedImage(img2);
			UnitPicHash.put((picName + "_" + n), img);
			try {img = ImageIO.read(new File(picLocation));
			} catch (IOException e) {}
		}
		// Vier verschiedene Farben werden generiert (Links)
		for (int n = 1; n != 5; n++){
			img = imgMani.setnewColors(img, n);
			Image img2 = imgMani.setnewDimension(img, picLocation);
			img = this.toBufferedImage(img2);
			img = imgMani.rotate(img);
			UnitPicHash.put((picName + "_" + n + "_turn"), img);
			try {img = ImageIO.read(new File(picLocation));
			} catch (IOException e) {}
		}
		// Vier verschiedene Farben werden generiert (Rechts und makiert)
		for (int n = 1; n != 5; n++){
			img = imgMani.setnewColors(img, n);
			Image img2 = imgMani.setnewDimension(img, picLocation);
			img = this.toBufferedImage(img2);
			img = imgMani.setSelection(img);
			UnitPicHash.put((picName + "_" + n + "_mark"), img);
			try {img = ImageIO.read(new File(picLocation));
			} catch (IOException e) {}
		}
		// Vier verschiedene Farben werden generiert (Links und makiert)
		for (int n = 1; n != 5; n++){
			img = imgMani.setnewColors(img, n);
			Image img2 = imgMani.setnewDimension(img, picLocation);
			img = this.toBufferedImage(img2);
			img = imgMani.setSelection(img);
			img = imgMani.rotate(img);
			UnitPicHash.put((picName + "_" + n + "_mark_turn"), img);
			try {img = ImageIO.read(new File(picLocation));
			} catch (IOException e) {}
		}
		
		return unitPicHash;
	}
	
	
	/**
	 * Macht aus einem Image ein sicheres BufferesImage
	 * @param img
	 * @return
	 */
	public  BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
	    return bimage;
	}

}
	