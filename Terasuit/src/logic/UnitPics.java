package logic;

import inGame.ImageManipulator;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UnitPics {
	
	String picLocation = "";
	String picName = "";
	ImageManipulator imgMani = new ImageManipulator();
	String [] entityGroundList = {"Marine", "Chronite Tank"};
	String [] entityAirList = {"Scout"};
	
	HashMap<String, BufferedImage> UnitPicHash = new HashMap<String, BufferedImage>();
	
	public void generateAllEntityPictures(){
		for (int i = 0; i != entityGroundList.length; i++){
			picLocation = "Unit/Ground/" + entityGroundList[i] + ".png";
			picName = entityGroundList[i];
			this.generate(UnitPicHash, i);
		}
		for (int i = 0; i != entityAirList.length; i++){
			picLocation = "Unit/Air/" + entityAirList[i] + ".png";
			picName = entityAirList[i];
			this.generate(UnitPicHash, i);
		}
	}
	
	public ImageIcon getEntityPic(String EnityName, int color, boolean right, boolean mark){
		
	    String searchString = EnityName + "_" + color;
		if (right && !mark){
			searchString = searchString + "_false_false";
		}else if (!right && !mark){
			searchString = searchString + "_true_false";
		}else if (right && mark){
			searchString = searchString + "_false_true";
		}else{
			searchString = searchString + "_true_true";
		}
		
		System.out.println(searchString + "  >=====");
		
		for(Entry<String, BufferedImage> entry : UnitPicHash.entrySet()) {
		    String key = entry.getKey();
		    BufferedImage unitObject = entry.getValue();
		    System.out.println(key);
		    if (key.equals(searchString)){
		    	System.out.println("habs");
		    	ImageIcon pic = new ImageIcon(unitObject);
		    	return pic;
		    }
		}
		return null;
	}
	
	private HashMap<String, BufferedImage> generate(HashMap<String, BufferedImage> unitPicHash, int i){

		// Bild der Einheite wird geladen
		BufferedImage img = null;
		BufferedImage imgBackup = null;
		try {
			img = ImageIO.read(new File(picLocation));
		} catch (IOException e) {
		}
		
		imgBackup = img;
		
		// Vier verschiedene Farben werden generiert (Rechts)
		for (int n = 1; n != 5; n++){
			img = imgMani.setnewColors(img, n);
			Image img2 = imgMani.setnewDimension(img, picLocation);
			img = this.toBufferedImage(img2);
			UnitPicHash.put((picName + "_" + n + "_false_false"), img);
			img = imgBackup;
		}
		// Vier verschiedene Farben werden generiert (Links)
		for (int n = 1; n != 5; n++){
			img = imgMani.setnewColors(img, n);
			Image img2 = imgMani.setnewDimension(img, picLocation);
			img = this.toBufferedImage(img2);
			img = imgMani.rotate(img);
			UnitPicHash.put((picName + "_" + n + "_true_false"), img);
			img = imgBackup;
		}
		// Vier verschiedene Farben werden generiert (Rechts und makiert)
		for (int n = 1; n != 5; n++){
			img = imgMani.setnewColors(img, n);
			Image img2 = imgMani.setnewDimension(img, picLocation);
			img = this.toBufferedImage(img2);
			img = imgMani.setSelection(img);
			UnitPicHash.put((picName + "_" + n + "_false_true"), img);
			img = imgBackup;
		}
		// Vier verschiedene Farben werden generiert (Links und makiert)
		for (int n = 1; n != 5; n++){
			img = imgMani.setnewColors(img, n);
			Image img2 = imgMani.setnewDimension(img, picLocation);
			img = this.toBufferedImage(img2);
			img = imgMani.setSelection(img);
			img = imgMani.rotate(img);
			UnitPicHash.put((picName + "_" + n + "_true_true"), img);
			img = imgBackup;
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
	