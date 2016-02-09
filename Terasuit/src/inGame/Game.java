package inGame;

import grafig.Loader;
import grafig.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;

public class Game {
	
	BaseBuildings buildings = new BaseBuildings();
	BtnCreator btnCreator = new BtnCreator();
	ArrayList<Unit> entity = new ArrayList<Unit>();
	ArrayList<Unit> NEWentity;
	ActionButton btnAction = new ActionButton();
	Funktions func;
	Panel panel;
	Panel field;
	Panel console;
	
	
	
	public void init(Panel panel, Panel field, Panel console, Loader loader, Funktions func){
		this.func = func;
		this.panel = panel;
		this.field = field;
		this.console = console;
		
		
		// Erstellen der Basis
		buildings.buildBase(field, console, this, loader, func, buildings.red, buildings.blue, buildings.default_position_Leftside_x, buildings.default_position_Leftside_y);
		buildings.buildBase(field, console, this, loader, func, buildings.grun, buildings.gelb, buildings.default_position_Rightside_x, buildings.default_position_Rightside_y);
		
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
	
	public void updateBuilding(){
		
	}
	
	public void updateUnit(){
		
	}
	
	public void run(){
		
	}

	public void searchForEntitysInRectangle(int minX, int minY, int w, int h) {	
		func.deMarkEntittys();
		func.findAllEntitys(minX, minY, w, h);
		func.destroyUserOptions(console, this);
	}
	
	public void selectSingleEntity(MouseEvent objUnit){
		func.findEntity(objUnit);
		func.destroyUserOptions(console, this);
	}
	
	public void createUserOptions(Panel field, Panel console, int i, ArrayList<Buildings> buildingsEntity, Loader loader){
		btnAction.createUserOptions(console, field, this, buildingsEntity, i, loader, func);
	}

	public void entity(String unitString, int number, boolean b) {
		func.createEntity(field, unitString, number, b, this);
	}
	

}
