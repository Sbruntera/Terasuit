package logic;

import inGame.ImageManipulator;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UnitPics {
	
	String picLocation = "";
	String picName = "";
	ImageManipulator imgMani = new ImageManipulator();
	String [] entityGroundList = {"Marine", "Chronite Tank", "Gröditz", "A25-Roman", "Hover Tank", "Meditec", "Modified Sakata", "Sniper", "Sphinx", "Sakata-MK2", "Sakata", "Gladiator", "Sakata Spider"};
	String [] entityAirList = {"Scout", "Phantom", "Black Queen", "Saint", "Modified Phantom"};
	
	HashMap<String, BufferedImage> UnitPicHash = new HashMap<String, BufferedImage>();
	
	public void generateAllEntityPictures(){
		for (int i = 0; i != entityGroundList.length; i++){
			picLocation = "Unit/Ground/" + entityGroundList[i] + ".png";
			picName = entityGroundList[i];
			System.out.println(picLocation);
			this.generate(UnitPicHash);
		}
		for (int i = 0; i != entityAirList.length; i++){
			picLocation = "Unit/Air/" + entityAirList[i] + ".png";
			picName = entityAirList[i];
			System.out.println(picLocation);
			this.generate(UnitPicHash);
		}
	}
	
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
	