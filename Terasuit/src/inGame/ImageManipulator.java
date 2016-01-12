package inGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ImageManipulator {
	
	int xSkalierung = 0;
	int ySkalierung = 0;
	static int Colorbrightness = 20;

	public int getxSkalierung() {
		return xSkalierung;
	}

	public void setxSkalierung(int xSkalierung) {
		this.xSkalierung = xSkalierung;
	}

	public int getySkalierung() {
		return ySkalierung;
	}

	public void setySkalierung(int ySkalierung) {
		this.ySkalierung = ySkalierung;
	}

	public BufferedImage setnewColors(BufferedImage img, int color) {

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color imageColor = new Color(img.getRGB(x, y));
				if (imageColor.getBlue() == 255 && imageColor.getGreen() == 0 && imageColor.getBlue() == 255) {
					// Blau
					if (color == 1){
						imageColor = new Color(0, 0, 255);
					// Rot
					}else if (color == 2){
						imageColor = new Color(255, 0, 0);
					// gelb
					}else if (color == 3){
						imageColor = new Color(255, 255, 0);
					// Gr�n
					}else if (color == 4){
						imageColor = new Color(0, 255, 0);
					}
					img.setRGB(x, y, imageColor.getRGB());
				}
			}
		}
		return img;
	}


	public Image setnewDimension(BufferedImage img, String Entitytype) {
		this.getUnitDeminsionSize(Entitytype);
		return img.getScaledInstance(getxSkalierung(), getySkalierung(), Image.SCALE_AREA_AVERAGING);
	}


	public BufferedImage setSelection(BufferedImage img) {
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color imageColor = new Color(img.getRGB(x, y));
				if ( (img.getRGB(x, y)>>24) == 0x00 ) {
				}else{
					imageColor = new Color(newSelectionColor(imageColor.getRed()), newSelectionColor(imageColor.getGreen()), newSelectionColor(imageColor.getBlue()));
					img.setRGB(x, y, imageColor.getRGB());
				}
			}
		}
		return img;
	}
	
	
	public int newSelectionColor(int Color){
		Color = Color + Colorbrightness;
		if (Color >= 256){
			Color = 255;
		}
		return Color;
	}
	
	public BufferedImage setDeSelection(BufferedImage img) {
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				Color imageColor = new Color(img.getRGB(x, y));
				if ( (img.getRGB(x, y)>>24) == 0x00 ) {
				}else{
					imageColor = new Color(newDeSelectionColor(imageColor.getRed()), newDeSelectionColor(imageColor.getGreen()), newDeSelectionColor(imageColor.getBlue()));
					img.setRGB(x, y, imageColor.getRGB());
				}
			}
		}
		return img;
	}
	
	
	public int newDeSelectionColor(int Color){
		Color = Color - Colorbrightness;
		if (Color >= 0){
			Color = 0;
		}
		return Color;
	}
	
	
	/**
	 * Giebt die Skalierungsgr��e zur�ck, diese wird an Hand des Namens ermittelt.
	 * @return int
	 */
	public void getUnitDeminsionSize(String Entitytype){
	     switch (Entitytype) {
         case "Unit/test2.png":
        	 setxSkalierung(40);
        	 setySkalierung(40);
         case "Unit/test.png":
        	 setxSkalierung(20);
        	 setySkalierung(44);
	     }
	}

	// Spiegel erhaltene ImageIcons
	
	@SuppressWarnings("serial")
	class MirrorImageIcon extends ImageIcon {

	    public MirrorImageIcon(String filename) {
	    	super(filename);
	    }

	    @Override
	    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
	    	Graphics2D g2 = (Graphics2D)g.create();
	    	g2.translate(getIconWidth(), 0);
	    	g2.scale(-1, 1);
	    	super.paintIcon(c, g2, x, y);
	    }
	}

	public BufferedImage rotate(BufferedImage img) {
	    AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
	    tx.translate(-img.getWidth(null), 0);
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return img = op.filter(img, null);
	}
}
