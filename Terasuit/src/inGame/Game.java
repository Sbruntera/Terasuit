package inGame;

import grafig.Loader;
import grafig.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class Game {
	
	BaseBuildings buildings = new BaseBuildings();
	BtnCreator btnCreator = new BtnCreator();
	
	public void init(Panel panel, Panel field, Panel console, Loader loader, Funktions func){
		
		// Erstellen der Basis
		buildings.buildBase(field, console, loader, func, buildings.red, buildings.blue, buildings.default_position_Leftside_x, buildings.default_position_Leftside_y);
		buildings.buildBase(field, console, loader, func, buildings.grun, buildings.gelb, buildings.default_position_Rightside_x, buildings.default_position_Rightside_y);
		
		
		
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

}
