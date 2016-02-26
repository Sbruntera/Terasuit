package de.szut.client.ingame;

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

import de.szut.client.grafik.Loader;
import de.szut.client.grafik.Panel;

public class Game {

	BaseBuildings buildings = new BaseBuildings();
	// ArrayList<Buildings> BuildingsEntity = new ArrayList<Buildings>();
	Buildings[] BuildingsArray = new Buildings[36];
	ArrayList<JProgressBar> JProgressBarArray = new ArrayList<JProgressBar>();
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
					func.getListOfJProgressBar(), slotID, primID, func);
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

		if (primID != 0) {
			JProgressBar progressBar = new JProgressBar();
			progressBar.setBounds(20, 60, 160, 10);
			func.addProgressBar(progressBar, index);
			console.add(progressBar);
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
	 * Setzt alle verfügbaren JProcessbars auf sichtbar/nicht sichtbar
	 */
	public void setAllJProgressBarVisible(boolean bool) {
		for (int i = 0; i != func.getListOfJProgressBar().length; i++) {
			if (func.getListOfJProgressBar()[i] != null) {
				func.getListOfJProgressBar()[i].setVisible(bool);
			}
		}
	}

	public void cancel(int index) {
		func.getListOfJProgressBar()[index].setVisible(false);
		func.destroyUserOptions(console, this);
		func.getListOfJProgressBar()[index] = null;

	}

	public void replaceJProcessbar(int index) {
		func.getListOfJProgressBar()[index].setVisible(true);
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
			if (func.entity.containsKey(i)) {
				Unit u = func.getEntity(i);
				u.setEntityRun(running);
				u.setEntityMove(moving);
				if (running || !moving) {
					u.getLabel().setIcon(func.pics.getEntityPic(u.getEntityname(), u.getEntitymembership(), (((u.getEntitymembership()-1)&2) == 2), func.selectedEntitysID.contains(i)));
					u.setEntityRushLeft(((u.getEntitymembership()-1)&2) == 2);
				} else {
					u.getLabel().setIcon(func.pics.getEntityPic(u.getEntityname(), u.getEntitymembership(), !right, func.selectedEntitysID.contains(i)));
					u.setEntityRushLeft(!right);
				}
			}
		}
	}

	public Integer[] getSelectedUnits() {
		return func.selectedEntitysID.toArray(new Integer[func.selectedEntitysID.size()]);
	}
}
