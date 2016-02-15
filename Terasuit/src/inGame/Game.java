package inGame;

import grafig.Loader;
import grafig.Panel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	JLabel tl;
	int playerID;
	int counter = 0;
	private String s = "Willkommen im Chat";
	
	
	
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
		//Chat
		tl = new JLabel(s);
		tl.setBounds(756, 11, 255, 134);
		tl.setForeground(Color.WHITE);
		tl.setVerticalAlignment(SwingConstants.TOP);
		//666, 350, 320, 364
		JTextField tf = new JTextField();
		tf.setBounds(756, 145, 255, 30);
		tf.addActionListener(e -> {
			loader.connection.sendChatMessage(tf.getText());
			tf.setText("");
		});
		console.add(tl);
		console.add(tf);
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
		func.findAllEntitys(minX, minY, w, h, playerID);
		func.destroyUserOptions(console, this);
	}
	
	public void selectSingleEntity(MouseEvent objUnit){
		func.findEntity(objUnit);
		func.destroyUserOptions(console, this);
	}
	
	public void createUserOptions(int slotID, int primID, Buildings[] buildingsArray){
		btnAction.createUserOptions(console, this, buildingsArray, listOfJProgressBar , slotID, primID, loader, func);
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
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(20, 60, 160, 10);
		if (primID != 0){
			progressbar = new ProgressbarLogic(index, X, Y, this, index, primID, time, field, "blubb", buildingName, buildingLocation);
			System.out.println("===> " + index);
			this.listOfJProgressBar[index] = progressBar;
			progressbar.init(time);
			console.add(listOfJProgressBar[index]);
		}else{
			progressbar = new ProgressbarLogic(primID, X, Y, this, index, primID, time, field, "blubb", buildingName, buildingLocation);
			this.listOfJProgressBar[primID] = progressBar;
			System.out.println("===> " + primID);
			progressbar.init(time);
			console.add(listOfJProgressBar[primID]);
		}
	}
	
	/**
	 * Sucht mit den gegeben Daten nach der aktuellen JProcessbar und aktualisiert sie, wobei bei 100% eine neue Aktion ausgeführt wird
	 */
	public void setProgressbar(int ID, int X,  int Y, String buildingLocation, String description, String buildingName, int i, int slotID, int primID, int time){
		if (listOfJProgressBar[ID] != null){
			int percent =  listOfJProgressBar[ID].getValue();
			if (percent != 100){
				listOfJProgressBar[ID].setValue(percent+10);
				System.out.println(ID + " kkkdkd");
			}else{
				buildings.createPrimaryBuilding(buildingLocation, X, Y, BuildingsArray, description, buildingName, this, slotID, primID, field, time);
				listOfJProgressBar[ID].setVisible(false);
				listOfJProgressBar[ID] = null;
				func.destroyUserOptions(console, this);
				setAllJProgressBarVisible(false);
				field.repaint();
			}
		}
	}
	
	/**
	 * Setzt alle verfügbaren JProcessbars auf sichtbar/nicht sichtbar
	 */
	public void setAllJProgressBarVisible(boolean bool){
		for (int i = 0; i != listOfJProgressBar.length; i++){
			if (listOfJProgressBar[i] != null){
				listOfJProgressBar[i].setVisible(bool);
			}
		}
	}

	public void cancel(int index) {
		listOfJProgressBar[index].setVisible(false);
		func.destroyUserOptions(console, this);
		listOfJProgressBar[index] = null;
		
	}

	public void replaceJProcessbar(int index) {
		listOfJProgressBar[index].setVisible(true);
		
	}
	public void setText(String text){
		s = s + "<br>" + "User: " + text;
		System.out.println(text);
		tl.setText("<html>" + s + "</html>");
	}
}
