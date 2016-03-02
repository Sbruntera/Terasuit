package de.szut.client.ingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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
	private JLabel r1;
	private JLabel r2;
	private JLabel r3;
	private JLabel r4;
	JScrollPane scrollPane;

	public void init(Panel panel, Panel field, Panel console, Loader loader,
			Funktions func, int playerID, JScrollPane scrollPane) {
		this.func = func;
		this.panel = panel;
		this.field = field;
		this.console = console;
		this.playerID = playerID;
		this.loader = loader;
		this.scrollPane = scrollPane;
		btnAction = new ActionButton(loader);
		func.setGame(this);

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
		func.setMainBuildings((MainBuilding) BuildingsArray[9], (MainBuilding) BuildingsArray[18]);
		JButton btnBACK = new JButton("X");
		btnCreator.createOne(btnBACK, 673, 114, 60, 60, 87);
		btnBACK.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				loader.connection.leaveGame();
				loader.switchPanel(loader.Mainpage);
				loader.game.end();
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
        
        //Resources
        r1 = new JLabel("50");
        r1.setIcon(new ImageIcon("Game_Assets/money.png"));
        setAttributes(r1, 10, 140, 60, 30);
        r2 = new JLabel("50");
        r2.setIcon(new ImageIcon("Game_Assets/Strom.png"));
        setAttributes(r2, 85, 140, 60, 30);
        r3 = new JLabel("50");
        r3.setIcon(new ImageIcon("Game_Assets/human.png"));
        setAttributes(r3, 160, 140, 60, 30);
        r4 = new JLabel("0");
        r4.setIcon(new ImageIcon("Game_Assets/monk.png"));
        setAttributes(r4, 245, 140, 60, 30);
        
	}
	
	/**
	 * Setzt den Text Mittig, die Schrift größer und Weiß und setzt die position anhand der übergebenen Werte
	 * 
	 * @param c JLabel was bearbeitet werden soll
	 * @param x Position auf der X Achse
	 * @param y Position auf der Y Achse
	 * @param width Breite
	 * @param height Höhe
	 */
	public void setAttributes(JLabel c, int x, int y, int width, int height){
		c.setHorizontalAlignment(SwingConstants.CENTER);
        c.setFont(new Font("Arial", Font.BOLD, 16));
        c.setForeground(Color.WHITE);
        c.setBounds(x, y, width, height);
        console.add(c);
	}
	
	/**
	 * Setzt die anzeige der Resourcen
	 * 
	 * @param Resource Typ der Resourcen
	 * @param number Anzahl der Resourcen die hinzugefügt werden sollen
	 */
	public void setResources(double[] resources) {
		r1.setText(String.valueOf((int)resources[0]));
		r2.setText(String.valueOf((int)resources[1]));
		r3.setText(String.valueOf((int)resources[2]));
		r4.setText(String.valueOf((int)resources[3]));
	}

	public void end(boolean won) {
		JLabel victory;
		if(won){
			victory = new JLabel(new ImageIcon("Game_Assets/victory.png"));
		} else {
			victory = new JLabel(new ImageIcon("Game_Assets/defeat.png"));
		}
		victory.setBounds(100, 100, 552, 80);
		field.setEnabled(false);
		for (MouseListener n : field.getMouseListeners()){
			field.removeMouseListener(n);
		}
		for (MouseMotionListener n : field.getMouseMotionListeners()){
			field.removeMouseMotionListener(n);
		}
		console.setEnabled(false);
		for (MouseListener n : console.getMouseListeners()){
			console.removeMouseListener(n);
		}
		panel.add(victory);	
		panel.setComponentZOrder(victory, 0);
		panel.repaint();
		panel.revalidate();
		func.end();
	}

	public void end() {
		func.end();
	}

	public void searchForEntitysInRectangle(int minX, int minY, int w, int h) {
		func.deMarkEntittys();
		func.findAllEntitys(minX, minY, w, h, playerID);
		func.destroyUserOptions(console);
	}

	public void selectSingleEntity(MouseEvent objUnit) {
		func.findEntity(objUnit);
		func.destroyUserOptions(console);
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
		func.createEntity(field, unitString, playerNumber, air, unitID, position, playerNumber == this.playerID);
	}

	public void destroyBuilding(int i) {
		buildings.destroyPrimaryBuilding(BuildingsArray, i, field, 20);
		func.destroyUserOptions(console);
	}

	public void createBuilding(String buildingName, String buildingLocation,
			int index, int primID) {
		func.destroyUserOptions(console);

		if (primID != 0) {
			buildings.createPrimaryBuilding(buildingLocation, BuildingsArray[primID - 18].getX(), BuildingsArray[primID - 18].getY(),
					BuildingsArray, "This is a production building!", buildingName, this,
					primID - 18, primID, field);
			func.payPrice(BuildingsArray[primID].getPrice());
			JProgressBar progressBar = new JProgressBar();
			progressBar.setBounds(20, 60, 160, 10);
			func.addProgressBar(progressBar, index);
			console.add(progressBar);
		}

		createUserOptions(primID - 18, primID, BuildingsArray);
	}

	public void createEnemyBuilding(String buildingName, String buildingLocation, int slotID, int primID) {
		buildings.createPrimaryBuilding(buildingLocation, BuildingsArray[slotID].getX(), BuildingsArray[slotID].getY(),
				BuildingsArray, "This a enemy building!", buildingName, this,
				slotID, primID, field);
		field.remove(BuildingsArray[slotID].getLabel());
		field.add(BuildingsArray[primID].getLabel());
		field.add(BuildingsArray[slotID].getLabel());
		field.repaint();
	}
	
	public void finishBuilding(int primID) {
		field.remove(BuildingsArray[primID - 18].getLabel());
		field.add(BuildingsArray[primID].getLabel());
		field.add(BuildingsArray[primID - 18].getLabel());
		field.repaint();
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
		func.destroyUserOptions(console);
		func.getListOfJProgressBar()[index] = null;
		func.refundPrice(BuildingsArray[index + 18].getPrice());
		if (BuildingsArray[index + 18].getPrimerBuilding() != null) {
			BuildingsArray[index + 18] = BuildingsArray[index + 18].getPrimerBuilding();
			BuildingsArray[index].setPrimerBuilding(BuildingsArray[index + 18]);
			BuildingsArray[index + 18].setPrimerBuilding(null);
			field.remove(BuildingsArray[index].getLabel());
			field.add(BuildingsArray[index + 18].getLabel());
			field.add(BuildingsArray[index].getLabel());
		} else {
			BuildingsArray[index].setPrimerBuilding(null);
			BuildingsArray[index + 18] = null;
		}
		field.repaint();

	}

	public void replaceJProcessbar(int index) {
		func.getListOfJProgressBar()[index].setVisible(true);
	}

	/**
	 * Fügt die Nachricht den Chatfenster hinzu und bewegt den Scroller nach ganz unten
	 * 
	 * @param text Nachricht an den Chat
	 */
	public void setText(String text) {
		s = s + "<br>" + text;
		tl.setText("<html>" + s + "</html>");
		try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int s = ts.getModel().getMaximum() + ts.getModel().getExtent() ;
        ts.setValue(s); //Setzt den Scroller an das Ende der Scrollbar
	}
	
	public void setPlayerID(int id) {
		playerID = id;
		if (playerID == 3 || playerID == 4){
			scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMaximum());
		}
	}

	public void moveUnit(byte player, boolean moving, boolean running, boolean right, int[] units) {
		for (int i : units) {
			if (func.entity.containsKey(i)) {
				Unit u = func.getEntity(i);
				if (u.getEntitymembership() == player+1) {
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
	}

	public Integer[] getSelectedUnits() {
		return func.selectedEntitysID.toArray(new Integer[func.selectedEntitysID.size()]);
	}

	public void showBullet(JLabel bullet) {
		field.add(bullet);
	}

	public void repaint() {
		field.repaint();
		field.revalidate();
	}

	public void removeUnits(int[] units) {
		func.removeUnits(units);
	}
}
