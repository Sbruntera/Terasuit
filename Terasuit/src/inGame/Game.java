package inGame;

import grafig.Loader;
import grafig.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class Game {
	
	BaseBuildings buildings = new BaseBuildings();
	BtnCreator btnCreator = new BtnCreator();

	Funktions func;
	
	
	
	public void init(Panel panel, Panel field, Panel console, Loader loader, Funktions func){
		this.func = func;
		
		
		// Erstellen der Basis
		buildings.buildBase(field, console, loader, func, buildings.red, buildings.blue, buildings.default_position_Leftside_x, buildings.default_position_Leftside_y);
		buildings.buildBase(field, console, loader, func, buildings.grun, buildings.gelb, buildings.default_position_Rightside_x, buildings.default_position_Rightside_y);
		
//		pics.generateAllEntityPictures();
		
//		BufferedImage img = null;
//		try {
//			img = ImageIO.read(new File("Unit/Ground/Marine.png"));
//		} catch (IOException e) {
//		}
		
//		// TEST
//		JLabel label = new JLabel("");
//		ImageIcon pic = pics.getEntityPic("Chronite Tank", 1, true, true);
////		ImageIcon pic = new ImageIcon(img);
//		label.setIcon(pic);
//		label.setBounds(10, 10, 50, 50);
//		console.add(label);
//		console.repaint();
		
		// Back-Button
		JButton btnBACK = new JButton("X");
		btnCreator.createOne(btnBACK, 680, 5, 60, 60, 87);
		btnBACK.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				loader.connection.leaveGame();
				loader.switchPanel(loader.Mainpage);
			}
		});
		console.add(btnBACK);


		panel.repaint();
	}

	public void addMousListerner(int minX, int minY, int w, int h) {
		func.deMarkEntittys();
		func.findAllEntitys(minX, minY, w, h);	
	}

}
