package inGame;

import grafig.Loader;
import grafig.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import logic.UnitPics;

public class Game {
	
	BaseBuildings buildings = new BaseBuildings();
	BtnCreator btnCreator = new BtnCreator();
	UnitPics pics = new UnitPics();
	ActionButton btnAction = new ActionButton();
	Funktions func = new Funktions();
	
	Panel panel;
	Panel field;
	Panel console;
	Loader loader;
	
	public void init(Panel panel, Panel field, Panel console, Loader loader, Funktions func){
		
		this.panel = panel;
		this.field = field;
		this.console = console;
		this.loader = loader;
		this.func = func;
		
		// Erstellen der Basis
		buildings.buildBase(field, console, loader, func, this, buildings.red, buildings.blue, buildings.default_position_Leftside_x, buildings.default_position_Leftside_y);
		buildings.buildBase(field, console, loader, func, this, buildings.grun, buildings.gelb, buildings.default_position_Rightside_x, buildings.default_position_Rightside_y);
		
		pics.generateAllEntityPictures();
		
		
		
		// TEST
		JLabel label = new JLabel("");
		ImageIcon pic = pics.getEntityPic("Scout", 4, true, true);
		label.setIcon(pic);
		label.setBounds(20, 20, 150, 150);
		console.add(label);
		console.repaint();
		console.revalidate();
		
		// Back-Button
		JButton btnBACK = new JButton("X");
		btnCreator.createOne(btnBACK, 680, 5, 60, 60, 87);
		btnBACK.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
//				if (loader.connection.returnToLobbyFromGame()){
//					loader.switchPanel(loader.Mainpage);
//				}else{
//					System.out.println("Kann das Spiel nicht verlassen!!");
//				}
				
			}
		});
		console.add(btnBACK);


		panel.repaint();
	}
	
	public void createAktion(ArrayList<Buildings> buildingsEntity, int i){
		btnAction.createUserOptions(console, field, buildingsEntity, i, loader, func);
	}

	public void addMousListerner(int minX, int minY, int w, int h) {
		
		func.deMarkEntittys();
		func.findAllEntitys(minX, minY, w, h);
		
	}

}
