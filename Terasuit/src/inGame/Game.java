package inGame;

import grafig.Loader;
import grafig.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JProgressBar;

public class Game {
	
	BaseBuildings buildings = new BaseBuildings();
	ProgressbarLogic progressbar;
//	ArrayList<Buildings> BuildingsEntity = new ArrayList<Buildings>();
	Buildings [] BuildingsArray = new Buildings[36];
	ArrayList<JProgressBar> JProgressBarArray = new ArrayList<JProgressBar>();
	JProgressBar[] listOfJProgressBar = new JProgressBar[36];
	BtnCreator btnCreator = new BtnCreator();
	ArrayList<Unit> entity = new ArrayList<Unit>();
	ArrayList<Unit> NEWentity;
	ActionButton btnAction = new ActionButton();
	Funktions func;
	Panel panel;
	Panel field;
	Panel console;
	Loader loader;
	int playerID;
	int counter = 0;
	
	
	
	public void init(Panel panel, Panel field, Panel console, Loader loader, Funktions func, int playerID){
		this.func = func;
		this.panel = panel;
		this.field = field;
		this.console = console;
		this.playerID = playerID;
		this.loader = loader;
		
		// Erstellen der Basis
		buildings.buildBase(field, this, BuildingsArray, loader, func, buildings.red, buildings.blue, buildings.default_position_Leftside_x, buildings.default_position_Leftside_y, true);
		buildings.buildBase(field, this, BuildingsArray, loader, func, buildings.grun, buildings.gelb, buildings.default_position_Rightside_x, buildings.default_position_Rightside_y, false);
		
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
	
	public void createUserOptions(int slotID, int primID, Buildings[] buildingsArray){
		btnAction.createUserOptions(console, this, buildingsArray, slotID, primID, loader, func);
	}

	public void entity(String unitString, int number, boolean b) {
		func.createEntity(field, unitString, number, b, this);
	}
	
	public void destroyBuilding(int i){
		buildings.destroyPrimaryBuilding(BuildingsArray, i, field, 20);
		func.destroyUserOptions(console, this);
	}

	public void createBuilding(String buildingName, String buildingLocation, Buildings[] buildingsArray, int index, int primID) {
		int X = buildingsArray[index].getX();
		int Y = buildingsArray[index].getY();
		int time = 20;
		func.destroyUserOptions(console, this);
		
//		System.out.println("=>>>>>  " + index);
//		System.out.println("=>>>>>  " + primID);
		if (primID == 0){
			progressbar = new ProgressbarLogic(index);
		}else{
			progressbar = new ProgressbarLogic(primID);
		}

		progressbar.init(20, this);
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(20, 700, 100, 5);
		listOfJProgressBar[index] = progressBar;
		field.add(listOfJProgressBar[index]);
		
		this.BuildingsArray = buildings.createPrimaryBuilding(buildingLocation, X, Y, buildingsArray, "Blubb", buildingName, 1, this, index, primID, field, time);
	}
	
	public void setProgressbar(int iD){
		System.out.println("ich setzte neu!");
	}
}
