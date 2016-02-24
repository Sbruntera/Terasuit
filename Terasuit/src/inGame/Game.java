package inGame;

import grafig.Loader;
import grafig.Panel;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Game {

	BaseBuildings buildings = new BaseBuildings();
	ProgressbarLogic progressbar;
	// ArrayList<Buildings> BuildingsEntity = new ArrayList<Buildings>();
	Buildings[] BuildingsArray = new Buildings[36];
	ArrayList<JProgressBar> JProgressBarArray = new ArrayList<JProgressBar>();
	JProgressBar[] listOfJProgressBar = new JProgressBar[36];
	BtnCreator btnCreator = new BtnCreator();
	ArrayList<Unit> entity = new ArrayList<Unit>();
	ArrayList<Unit> NEWentity;
	ActionButton btnAction;
	Funktions func;
	Panel panel;
	Panel field;
	Panel console;
	Loader loader;
	JLabel tl;
	int playerID;
	int counter = 0;
	private String s = "Willkommen im Chat";
	private JScrollBar ts;

	public void init(Panel panel, Panel field, Panel console, Loader loader,
			Funktions func, int playerID) {
		this.func = func;
		this.panel = panel;
		this.field = field;
		this.console = console;
		this.playerID = playerID;
		this.loader = loader;
		btnAction = new ActionButton(loader);

		// Erstellen der Basis
		buildings.buildBase(field, this, BuildingsArray, loader, func,
				buildings.blue, buildings.red,
				buildings.default_position_Leftside_x,
				buildings.default_position_Leftside_y, true);
		buildings.buildBase(field, this, BuildingsArray, loader, func,
				buildings.gelb, buildings.grun,
				buildings.default_position_Rightside_x,
				buildings.default_position_Rightside_y, false);

		// Back-Button
		JButton btnBACK = new JButton("X");
		btnCreator.createOne(btnBACK, 673, 114, 60, 60, 87);
		btnBACK.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				loader.connection.leaveGame();
				loader.switchPanel(loader.Mainpage);
			}
		});
		// Chat
		tl = new JLabel(s);
        tl.setBounds(756, 11, 255, 134);
        tl.setForeground(Color.WHITE);
        tl.setVerticalAlignment(SwingConstants.TOP);
        JScrollPane scroller = new JScrollPane(tl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setBounds(756, 11, 255, 134);
        scroller.setOpaque(false);
        scroller.getViewport().setOpaque(false);
        ts = scroller.getVerticalScrollBar();
        // 666, 350, 320, 364
        JTextField tf = new JTextField();
        tf.setBounds(756, 145, 255, 30);
        tf.addActionListener(e -> {
            loader.connection.sendChatMessage(tf.getText());
            tf.setText("");
        });
        console.add(scroller);
        console.add(tf);
        console.add(btnBACK);
        console.repaint();
        console.revalidate();
        panel.repaint();
	}

	public void searchForEntitysInRectangle(int minX, int minY, int w, int h) {
		func.deMarkEntittys();
		func.findAllEntitys(minX, minY, w, h, playerID);
		func.destroyUserOptions(console, this);
	}

	public void selectSingleEntity(MouseEvent objUnit) {
		func.findEntity(objUnit, this);
		func.destroyUserOptions(console, this);
	}

	public void createUserOptions(int slotID, int primID,
			Buildings[] buildingsArray) {
		if ((slotID > (playerID-1 >> 1) + (playerID-1)*4 && slotID < (playerID-1 >> 1) + (playerID)*4 + 1) || slotID == (((playerID-1)>>1)+1)*9){
			func.deMarkEntittys();
			btnAction.createUserOptions(console, this, buildingsArray,
					listOfJProgressBar, slotID, primID, func);
		}
	}

	public void entity(String unitString, int playerNumber, boolean air, short unitID, Point position) {
		func.createEntity(field, unitString, playerNumber, air, this, unitID, position);
	}

	public void destroyBuilding(int i) {
		buildings.destroyPrimaryBuilding(BuildingsArray, i, field, 20);
		func.destroyUserOptions(console, this);
	}

	public void createBuilding(String buildingName, String buildingLocation,
			int index, int primID) {
		int X = BuildingsArray[index].getX();
		int Y = BuildingsArray[index].getY();
		int time = 20;
		func.destroyUserOptions(console, this);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(20, 60, 160, 10);
		if (primID != 0) {
			progressbar = new ProgressbarLogic(index, X, Y, this, index,
					primID, time, field, "blubb", buildingName,
					buildingLocation);
			this.listOfJProgressBar[index] = progressBar;
			progressbar.init(time);
			console.add(listOfJProgressBar[index]);
		} else {
			progressbar = new ProgressbarLogic(primID, X, Y, this, index,
					primID, time, field, "blubb", buildingName,
					buildingLocation);
			this.listOfJProgressBar[primID] = progressBar;
			progressbar.init(time);
			console.add(listOfJProgressBar[primID]);
		}
	}

	public void createEnemyBuilding(String buildingName, String buildingLocation, int slotID, int primID) {
		buildings.createPrimaryBuilding(buildingLocation, BuildingsArray[slotID].getX(), BuildingsArray[slotID].getY(),
				BuildingsArray, "blubb", buildingName, this,
				slotID, primID, field);
	}
	
	public void changeInformation(String type, String description){
		btnAction.changeDescription(description);
		btnAction.changeBuildingName(type);
	}

	/**
	 * Sucht mit den gegeben Daten nach der aktuellen JProcessbar und
	 * aktualisiert sie, wobei bei 100% eine neue Aktion ausgeführt wird
	 */
	public void setProgressbar(int ID, int X, int Y, String buildingLocation,
			String description, String buildingName, int i, int slotID,
			int primID, int time) {
		if (listOfJProgressBar[ID] != null) {
			int percent = listOfJProgressBar[ID].getValue();
			if (percent != 100) {
				listOfJProgressBar[ID].setValue(percent + 10);
			} else {
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
	public void setAllJProgressBarVisible(boolean bool) {
		for (int i = 0; i != listOfJProgressBar.length; i++) {
			if (listOfJProgressBar[i] != null) {
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

	public void setText(String text) {
		s = s + "<br>" + text;
		tl.setText("<html>" + s + "</html>");
		try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int s = ts.getModel().getMaximum() + ts.getModel().getExtent() ;
        ts.setValue(s);
	}
	
	public void setPlayerID(int id) {
		playerID = id;
	}

	public void moveUnit(boolean moving, boolean running, boolean right, int[] units) {
		for (int i : units) {
			Unit u = func.getEntity(i);
			System.out.println(u.getEntitymembership());
			u.setEntityRun(running);
			u.setEntityMove(moving);
			if (!running) {
				u.getLabel().setIcon(func.pics.getEntityPic(u.getEntityname(), u.getEntitymembership(), !right, func.selectedEntitysID.contains(i)));
				u.setEntityRushLeft(!right);
			} else {
				u.getLabel().setIcon(func.pics.getEntityPic(u.getEntityname(), u.getEntitymembership(), (((u.getEntitymembership()-1)&2) == 2), func.selectedEntitysID.contains(i)));
				u.setEntityRushLeft(((u.getEntitymembership()-1)&2) == 2);
			}
		}
	}

	public Integer[] getSelectedUnits() {
		return func.selectedEntitysID.toArray(new Integer[func.selectedEntitysID.size()]);
	}
}
