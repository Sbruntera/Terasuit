package grafig;

import inGame.BaseBuildings;
import inGame.BtnCreator;
import inGame.Funktions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import logic.Lobby;

/**
 * 
 * @author Alexander, Jan-Philipp, Simeon
 *
 */

public class SetButtons {
	
	LoginRegisterPanel loginRegisterPanel;
	boolean registerOpen = false;
	boolean loginOpen = false;
	boolean serverCreateOpen = false;
	boolean haspassword;
	BaseBuildings buildings = new BaseBuildings();
	BtnCreator btnCreator = new BtnCreator();
	JLabel tl;
	JPanel jp;
	JScrollBar ts;
	Panel panel;
	Loader loader;
	int markedLobby;
	ArrayList<JComboBox<String>> combolist = new ArrayList<JComboBox<String>>();
	ArrayList<JLabel> labellist = new ArrayList<JLabel>();
	ArrayList<JLabel> player_count_list = new ArrayList<JLabel>();
	ImageIcon open = new ImageIcon("Menu_Asstes/Schloss_offen.png");
	ImageIcon closed = new ImageIcon("Menu_Asstes/Schloss.png");
	ImageIcon map1 = new ImageIcon("Menu_Asstes/Thumbnail.png");
	String[] standartselect;
	private JPanel gametemp;
	
	private String s = "Willkommen im Chat";
	private JScrollPane scroller;
	
	public void setbuttons(Panel panel, String picName, Loader loader, Funktions func){
		this.panel = panel;
		this.loader = loader;
		
		//#########################################################################
		//
		//								MAINPANEL
		//
		//#########################################################################
		
		// Buttons für das Startpanel werden gesetzt und mit Aktionlisener versetzt
		if (picName.equals("Wallpaper/Start_Hintergrund.png")){
			
			// Start-Button
			JButton btnStart = new JButton("Start");
			btnStart.setBounds(168, 290, 245, 65);
			btnStart.setBackground(new Color(255,50,0));
			btnStart.setBorder(BorderFactory.createMatteBorder(1, 3, 7, 1, Color.red));
			btnStart.setFont(new Font("ArialB", Font.BOLD, 24));
			btnStart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Beim klick auf dem "Start"-Buttons gelangt man in die Lobby
					if (loader.connection.isServerAccess()){
						loader.switchPanel(loader.Lobbypage);
						loader.connection.refreshServerList(false, "", 0, 4, 0);
					} else {
						System.out.println("Server konnte nicht gefunden werden. ");
					}
				}
			});
			panel.add(btnStart);
			
			// Option-Button
			JButton btnOption = new JButton("Option");
			btnOption.setBounds(138, 383, 245, 65);
			btnOption.setBackground(new Color(255,70,0));
			btnOption.setBorder(BorderFactory.createMatteBorder(1, 3, 7, 1, Color.red));
			btnOption.setFont(new Font("Arial", Font.BOLD, 24));
			btnOption.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Beim klick auf dem "Options"-Buttons gelangt man in die Optionen
					loader.switchPanel(loader.Lobbypage);
				}
			});
			panel.add(btnOption);
			
			// Exit-Button
			JButton btnExit = new JButton("Exit");
			btnExit.setBounds(183, 480, 245, 65);
			btnExit.setBackground(new Color(255,90,0));
			btnExit.setBorder(BorderFactory.createMatteBorder(1, 3, 7, 1, Color.red));
			btnExit.setFont(new Font("Arial", Font.BOLD, 24));
			btnExit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Schließst das Programm
					loader.connection.close();
					loader.exit();
				}
			});
			panel.add(btnExit);
			loginRegisterPanel = new LoginRegisterPanel(loader.connection);
			JButton btnLogin = new JButton("LOGIN");
			btnLogin.setBounds(800, 732, 90, 25);
			btnLogin.setBackground(new Color(255,90,0));
			btnLogin.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					
					if (loginOpen == false){
						if (registerOpen == true){
							loginRegisterPanel.popupdestroy(panel);
						}
						loginRegisterPanel.popupLogin(panel);
						loginOpen = true;
						registerOpen = false;
					} else {
						loginRegisterPanel.popupdestroy(panel);
						loginOpen = false;
					}
				}
			});
			panel.add(btnLogin);
			
			JButton btnRegister = new JButton("REGISTER");
			btnRegister.setBounds(920, 732, 90, 25);
			btnRegister.setBackground(new Color(255,90,0));
			btnRegister.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					if (registerOpen == false){
						if (loginOpen == true){
							loginRegisterPanel.popupdestroy(panel);
						}
						loginRegisterPanel.popupRegister(panel);
						registerOpen = true;
						loginOpen = false;
					} else {
						loginRegisterPanel.popupdestroy(panel);
						registerOpen = false;
					}
				}
			});
			panel.add(btnRegister);
		
			
		//#########################################################################
		//
		//								SERVERLIST
		//
		//#########################################################################
			
		} else if (picName.equals("Wallpaper/serverlist.png")){
			//LobbyList
			jp = new JPanel();
			jp.setBounds(142, 92, 789, 800);
			jp.setOpaque(false);
			jp.setLayout(null);
			jp.setPreferredSize(new Dimension(789,0));
			//TestGen
			//TestGen Ende
			scroller = new JScrollPane(jp, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroller.setBounds(142, 92, 789, 580);
			scroller.setOpaque(false);
			scroller.getViewport().setOpaque(false);
			panel.add(scroller);
			
			
			// JOIN-Button
			JButton btnJoin = new JButton("JOIN");
			btnJoin.setBounds(303, 689, 170, 60);//links / runter / breite / höhe
			btnJoin.setBackground(new Color(255,90,0));
			btnJoin.setFont(new Font("Arial", Font.BOLD, 24));
			btnJoin.setIcon(new ImageIcon("Wallpaper/Join_Pfeil.png"));
			btnJoin.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Beim klick auf dem "Join"-Buttons gelangt man in eine Spielgruppe
					if(haspassword){
						JPanel test = needPW();
						panel.add(test);
						panel.setComponentZOrder(test, 0);
						panel.repaint();
						panel.revalidate();
					} else{
						loader.connection.connectGroup(markedLobby, "");
					}
					//TODO	loader.switchPanel(loader.Grouppage);
					//TODO	System.out.println("Gruppe voll oder ein fehler ist aufgetretten");

				}
			});
			panel.add(btnJoin);
			
			// Create-Button
			JButton btnCreateGroup = new JButton("CREATE");
			btnCreateGroup.setBounds(626, 689, 170, 60);//links / runter / breite / höhe
			btnCreateGroup.setBackground(new Color(255,90,0));
			btnCreateGroup.setFont(new Font("Arial", Font.BOLD, 24));
			btnCreateGroup.setIcon(new ImageIcon("Wallpaper/Create_Pfeil.png"));
			btnCreateGroup.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					System.out.println(markedLobby);
					JPanel test = createInfos();
					panel.add(test);
					panel.setComponentZOrder(test, 0);
					panel.repaint();
					panel.revalidate();
						// Beim klick auf dem "Create"-Buttons gelangt man in eine Spielgruppe, als Besitzer
					//TODO	loader.switchPanel(loader.Grouppage_owner);
					//TODO	System.out.println("Gruppe konnte nicht erstellt werden!");

				}
			});
			panel.add(btnCreateGroup);
			
			// Refresh-Button
			JButton btnRefreshGroup = new JButton("Refresh");
			btnRefreshGroup.setBounds(510, 680, 85, 30);//links / runter / breite / höhe
			btnRefreshGroup.setBackground(new Color(255,90,0));
			btnRefreshGroup.setFont(new Font("Arial", Font.BOLD, 12));
			btnRefreshGroup.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					loader.connection.refreshServerList(false, "", 0, 4, 0);
				}
			});
			panel.add(btnRefreshGroup);
			
			
			// Back-Button
			JButton btnBACK = new JButton("BACK");
			btnBACK.setBounds(510, 715, 85, 30);//links / runter / breite / höhe
			btnBACK.setBackground(new Color(255,90,0));
			btnBACK.setFont(new Font("Arial", Font.BOLD, 12));
			btnBACK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Der Weg zurück
					loader.switchPanel(loader.Mainpage);
				}
			});
			panel.add(btnBACK);	
		
		//#########################################################################
		//
		//								LOBBY
		//
		//#########################################################################	
			
		} else if (picName.equals("Wallpaper/Lobby.png")){
			
			// Back-Button
			JButton btnBACK = new JButton("RETURN");
			btnBACK.setBounds(430, 695, 170, 60);//links / runter / breite / höhe
			btnBACK.setBackground(new Color(255,90,0));
			btnBACK.setFont(new Font("Arial", Font.BOLD, 24));
			btnBACK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					loader.connection.returnFromLobby();
					//TODO	loader.switchPanel(loader.Lobbypage);
					//TODO	System.out.println("Verbindung zur Gruppe konnte nicht geschlossen werden!");
				}
			});
			
			//Team bezeichungen
			for(int i = 0; i <4;i++){
				JLabel color = new JLabel();
				if(i==0){
					color.setText("RED");
					color.setForeground(Color.RED);
					color.setBounds(75, 150, 200, 50);
				} else if(i ==1){
					color.setText("BLUE");
					color.setForeground(Color.BLUE);
					color.setBounds(75, 400, 200, 50);
				} else if(i ==2){
					color.setText("GREEN");
					color.setForeground(Color.GREEN);
					color.setBounds(425, 150, 200, 50);
				} else{
					color.setText("YELLOW");
					color.setForeground(Color.YELLOW);
					color.setBounds(425, 400, 200, 50);
				}
				color.setHorizontalAlignment(SwingConstants.CENTER);
				color.setFont(new Font("Arial", Font.BOLD, 24));
				panel.add(color);
			}
			
			//Lobbby Player Showcase
			for(int i = 0; i <4;i++){
				JLabel players = new JLabel();
				if (i==0 || i==1){
					players.setBounds(75, 230+(250*i), 200, 50);
				}else{
					players.setBounds(425, 230+(250*(i-2)), 200, 50);
				}
				players.setHorizontalAlignment(SwingConstants.CENTER);
				players.setBackground(new Color(255,90,0));
				players.setFont(new Font("Arial", Font.BOLD, 24));
				players.setOpaque(true);
				labellist.add(players);
				panel.add(players);
			}
			String[] player = { "hey", "test", "bird", ""}; //PLS DELETE !!!! LATER
			updateLabels(player); //ME TOOOOO
			
			//Chat
			tl = new JLabel(s);
			tl.setBounds(668, 350, 318, 334);
			tl.setForeground(Color.WHITE);
			tl.setVerticalAlignment(SwingConstants.TOP);
			JScrollPane scroller = new JScrollPane(tl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroller.setBounds(666, 350, 320, 334);
			scroller.setOpaque(false);
			scroller.getViewport().setOpaque(false);
			//666, 350, 320, 364
			JTextField tf = new JTextField();
			tf.setBounds(666, 684, 320, 30);
			tf.addActionListener(e -> {
				loader.connection.sendLobbyChatMessage(tf.getText());
				tf.setText("");
			});
			panel.add(scroller);
			panel.add(tf);
			panel.add(btnBACK);
			
		//#########################################################################
		//
		//								LOBBY_Besitzer
		//
		//#########################################################################	
			
		} else if (picName.equals("Wallpaper/Lobby_BESITZER.png")){
			// Back-Button
			JButton btnBACK = new JButton("RETURN");
			btnBACK.setBounds(430, 695, 170, 60);//links / runter / breite / höhe
			btnBACK.setBackground(new Color(255,90,0));
			btnBACK.setFont(new Font("Arial", Font.BOLD, 24));
			btnBACK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					loader.connection.returnFromLobby();
					//TODO	loader.switchPanel(loader.Lobbypage);
					//TODO	System.out.println("Gruppenlobby konnte nicht geschlossen werden!!!");
				}
			});
			panel.add(btnBACK);	
			
			//Chat
			tl = new JLabel(s);
			tl.setBounds(668, 350, 318, 334);
			tl.setForeground(Color.WHITE);
			tl.setVerticalAlignment(SwingConstants.TOP);
			JScrollPane scroller = new JScrollPane(tl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroller.setBounds(666, 350, 320, 334);
			scroller.setOpaque(false);
			scroller.getViewport().setOpaque(false);
			ts = scroller.getVerticalScrollBar();
			//666, 350, 320, 364
			JTextField tf = new JTextField();
			tf.setBounds(666, 684, 320, 30);
			tf.addActionListener(e -> {
				loader.connection.sendLobbyChatMessage(tf.getText());
				tf.setText("");
			});
			panel.add(scroller);
			panel.add(tf);
			panel.add(btnBACK);	
			
			//Team bezeichungen
			for(int i = 0; i <4;i++){
				JLabel color = new JLabel();
				if(i==0){
					color.setText("RED");
					color.setForeground(Color.RED);
					color.setBounds(75, 150, 200, 50);
				} else if(i ==1){
					color.setText("BLUE");
					color.setForeground(Color.BLUE);
					color.setBounds(75, 400, 200, 50);
				} else if(i ==2){
					color.setText("GREEN");
					color.setForeground(Color.GREEN);
					color.setBounds(425, 150, 200, 50);
				} else{
					color.setText("YELLOW");
					color.setForeground(Color.YELLOW);
					color.setBounds(425, 400, 200, 50);
				}
				color.setHorizontalAlignment(SwingConstants.CENTER);
				color.setFont(new Font("Arial", Font.BOLD, 24));
				panel.add(color);
			}
			//Lobby Host Player movement
			for(int i = 0; i <4;i++){
				JComboBox<String> players = new JComboBox<String>();
				JButton jb = new JButton("KICK");
				if (i==0 || i==1){
					players.setBounds(75, 230+(250*i), 200, 50);
					jb.setBounds(75, 300+(250*i), 200, 60);//links / runter / breite / höhe
				}else{
					players.setBounds(425, 230+(250*(i-2)), 200, 50);
					jb.setBounds(425, 300+(250*(i-2)), 200, 60);//links / runter / breite / höhe
				}
				players.setFont(new Font("Arial", Font.BOLD, 24));
				jb.setBackground(new Color(255,90,0));
				jb.setFont(new Font("Arial", Font.BOLD, 24));
				players.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent arg0) {
						int [] i = newplayers();
						loader.connection.switchPlayers((byte) i[0], (byte) i[1]);
					}
				});
				int x = i;
				jb.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent arg0) {
						loader.connection.kickPlayer(x);
					}
				});
				combolist.add(players);
				panel.add(players);
				panel.add(jb);
			}
			String[] player = { "hey", "test", "bird", "cat"}; //TODO:PLS DELETE !!!! LATER
			updateCombo(player); //ME TOOOOO
			
			
			// START-BATTLE-Button
			JButton btnBattleStart = new JButton("START");
			btnBattleStart.setBounds(135, 695, 170, 60);//links / runter / breite / höhe
			btnBattleStart.setBackground(new Color(255,90,0));
			btnBattleStart.setFont(new Font("Arial", Font.BOLD, 24));
			btnBattleStart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					loader.connection.startGame();
				}
			});
			panel.add(btnBattleStart);
		}
		
	}
	
	public void setText(String text){
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
	
	public void updateCombo(String[] player){
		standartselect = player;
		for(int i = 0; i <4;i++){
			combolist.get(i).setModel(new DefaultComboBoxModel<String>(player));
			combolist.get(i).setSelectedIndex(i);
		}
		panel.repaint();
	}
	
	public void updateLabels(String[] player){
		for(int i = 0; i <4;i++){
			labellist.get(i).setText(player[i]);
		}
		panel.repaint();
	}
	
	public int[] newplayers(){
		int[] numbers = new int[2];
		for (int i = 0; i < standartselect.length; i++) {
			if (!standartselect[i].equals(combolist.get(i).getSelectedItem().toString())) {
				numbers[0] = i;
			}
		}
		for (int i = 0; i < standartselect.length; i++) {
			if (standartselect[i].equals(combolist.get(numbers[0]).getSelectedItem().toString())) {
				numbers[1] = i;
			}
		}
		return numbers;
	}
	
	//Nicht wirklich flexibel keine möglichkeit zum Nachrutschen usw.
	public JPanel genNEWLobby(int lobbyNr, Lobby lobby) {
		JPanel game1 = new JPanel();
		game1.setBounds(10, 10+(160*(lobbyNr)), 755, 150);
		game1.setLayout(null);
		game1.setOpaque(true);
		game1.setBackground(new Color(255,90,0));
		game1.setFocusable(true);
		game1.setRequestFocusEnabled(true);
		game1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				markedLobby = lobby.getID();
				haspassword = lobby.hasPassword();
				if(gametemp != null){
                    gametemp.setBorder(null);    
                }
                gametemp = game1;
                game1.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
			}
		});
		JLabel map_pic = new JLabel();
		JLabel pw_en = new JLabel();
		JLabel lobby_name = new JLabel(lobby.getName());
		JLabel player_count = new JLabel("<html>Players:<br>" + lobby.getNumberOfPlayers() + "/4</html>");
		switch(lobby.getMapID()){
			case(1):
				map_pic.setIcon(map1);
				break;
		}
		pw_en.setIcon(open);
		if(lobby.hasPassword()){
			pw_en.setIcon(closed);
		}
		map_pic.setBounds(10,10,200,130);
		pw_en.setBounds(220, 20, 70, 45);
		lobby_name.setBounds(300, 20, 445, 110);
		player_count.setBounds(220, 85, 70, 45);
		lobby_name.setHorizontalAlignment(SwingConstants.CENTER);
		pw_en.setHorizontalAlignment(SwingConstants.CENTER);
		player_count.setHorizontalAlignment(SwingConstants.CENTER);
		lobby_name.setFont(new Font("Arial", Font.BOLD, 24));
		player_count.setFont(new Font("Arial", Font.BOLD, 24));
//		DEMO CODE
//		map_pic.setOpaque(true);
//		map_pic.setBackground(Color.BLUE);
//		lobby_name.setBackground(Color.BLUE);
//		player_count.setBackground(Color.BLUE);
//		pw_en.setBackground(Color.BLUE);
//		pw_en.setOpaque(true);
//		player_count.setOpaque(true);
//		lobby_name.setOpaque(true);
		//DEMO CODE ENDE
		player_count_list.add(player_count);
		game1.add(map_pic);
		game1.add(pw_en);
		game1.add(lobby_name);
		game1.add(player_count);
		
		return game1;
	}
	public void updateLobbyList(Lobby[] lobbyList) {
		System.out.println(lobbyList.length);
		JPanel lobbyPanel = new JPanel();
		lobbyPanel.setBounds(0, 0, 789, 800);
		lobbyPanel.setOpaque(false);
		lobbyPanel.setLayout(null);
		lobbyPanel.setPreferredSize(new Dimension(789,0));
		for (int i = 0; i < lobbyList.length; i++) {
			lobbyPanel.add(genNEWLobby(i, lobbyList[i]));
		}
		lobbyPanel.setPreferredSize(new Dimension(789,10+(160*lobbyList.length)));
		scroller.remove(jp);
		scroller.setViewportView(lobbyPanel);
		jp = lobbyPanel;
		scroller.repaint();
		scroller.revalidate();
	}
	public JPanel createInfos(){
		JPanel en = new JPanel();
		en.setLayout(null);
		en.setOpaque(true);
		en.setBounds(300, 251, 473, 264);
		en.setBackground(Color.RED);
		JLabel nl = new JLabel("Lobbyname: ");
		JLabel pl = new JLabel("Password: ");
		JButton c = new JButton("Create");
		JTextField n = new JTextField();
		JTextField p = new JTextField();
		nl.setFont(new Font("Arial", Font.BOLD, 12));
		pl.setFont(new Font("Arial", Font.BOLD, 12));
		c.setFont(new Font("Arial", Font.BOLD, 12));
		n.setFont(new Font("Arial", Font.BOLD, 12));
		p.setFont(new Font("Arial", Font.BOLD, 12));
		c.setBackground(new Color(255,90,0));
		nl.setBounds(20, 40, 130, 50);
		pl.setBounds(20, 130, 130, 50);
		n.setBounds(150, 40, 230, 50);
		p.setBounds(150, 130, 230, 50);
		c.setBounds(186, 200, 100, 60);
		c.addActionListener(e ->{
			loader.connection.createGroup(1, n.getText(), p.getText());
		});
		en.add(p);
		en.add(n);
		en.add(nl);
		en.add(pl);
		en.add(c);
		return en;
	}
	public JPanel needPW(){
		JPanel np = new JPanel();
		JLabel ep = new JLabel("This Lobby has a Password. Please it");
		JTextField tp = new JTextField();
		tp.addActionListener(e ->{
			loader.connection.connectGroup(markedLobby, ep.getText());
		});
		tp.setBounds(30, 80, 305, 50);
		ep.setBounds(30, 20, 305, 50);
		ep.setFont(new Font("Arial", Font.BOLD, 12));
		np.setLayout(null);
		np.setOpaque(true);
		np.setBounds(350, 300, 370, 165);
		np.setBackground(Color.RED);
		np.add(tp);
		np.add(ep);
		return np;
	}
}
