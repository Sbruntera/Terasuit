package de.szut.client.grafik;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.szut.client.ingame.BaseBuildings;
import de.szut.client.ingame.BtnCreator;
import de.szut.client.ingame.Funktions;
import de.szut.client.logic.Lobby;

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
	Panel panel;
	Loader loader;
	private int markedLobby;
	private ArrayList<JComboBox<String>> combolist = new ArrayList<JComboBox<String>>();
	private ArrayList<JLabel> labellist = new ArrayList<JLabel>();
	private ImageIcon open = new ImageIcon("Menu_Assets/Schloss_offen.png");
	private ImageIcon closed = new ImageIcon("Menu_Assets/Schloss.png");
	private ImageIcon map1 = new ImageIcon("Menu_Assets/Thumbnail.png");
	private String s = "Willkommen im Chat";
	private String[] standartselect;
	private JLabel tl;
	private JPanel jp;
	private JScrollBar ts;
	private JPanel gametemp;
	private JPanel create;
	private JScrollPane scroller;
	private JButton btnLogin;
	private JButton btnRegister;
	private boolean panelopen = false;
	private JCheckBox checknoPW;
	private JTextField nameContains;
	private JComboBox<Integer> minPlayers;
	private JComboBox<Integer> maxPlayers;
	private JCheckBox map_ns;

	/**
	 * Plaziert alle wichtigen Schaltflächen passend zum Hintergrund
	 * 
	 * @param panel
	 *            ContentPane des Frames
	 * @param picName
	 *            Name des Hintergrundbilds des ContentPanes
	 * @param loader
	 *            Loader der GUI
	 * @param func
	 *            Funktionen der GUI
	 */
	public void setbuttons(Panel panel, String picName, Loader loader,
			Funktions func) {
		this.panel = panel;
		this.loader = loader;

		// #########################################################################
		//
		// MAINPANEL
		//
		// #########################################################################

		// Buttons für das Startpanel werden gesetzt und mit Aktionlisener
		// versetzt
		if (picName.equals("Wallpaper/Start_Hintergrund.png")) {

			// Start-Button
			JButton btnStart = new JButton("Start");
			btnStart.setBounds(168, 290, 245, 65);
			btnStart.setBackground(new Color(255, 50, 0));
			btnStart.setBorder(BorderFactory.createMatteBorder(1, 3, 7, 1,
					Color.red));
			btnStart.setFont(new Font("ArialB", Font.BOLD, 24));
			btnStart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Beim klick auf dem "Start"-Buttons gelangt man in die
					// Lobby
					if (loader.connection.isServerAccess()) {
						loader.switchPanel(loader.Lobbypage);
						loader.connection.refreshServerList(false, "", 0, 4, 0);
					} else {
						loader.feedback("Server Not Reachable");
					}
				}
			});
			panel.add(btnStart);

			// Option-Button
			JButton btnOption = new JButton("Stats");
			btnOption.setBounds(138, 383, 245, 65);
			btnOption.setBackground(new Color(255, 70, 0));
			btnOption.setBorder(BorderFactory.createMatteBorder(1, 3, 7, 1,
					Color.red));
			btnOption.setFont(new Font("Arial", Font.BOLD, 24));
			btnOption.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Beim klick auf dem "Options"-Buttons gelangt man in die
					// Optionen
					if(loader.connection.getName() != null){
						loader.switchPanel(loader.Statspage);
						loader.connection.stats();
					} else {
						loader.feedback("You are not logged in!");
					}
				}
			});
			panel.add(btnOption);

			// Exit-Button
			JButton btnExit = new JButton("Exit");
			btnExit.setBounds(183, 480, 245, 65);
			btnExit.setBackground(new Color(255, 90, 0));
			btnExit.setBorder(BorderFactory.createMatteBorder(1, 3, 7, 1,
					Color.red));
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

			// Login-Button
			loginRegisterPanel = new LoginRegisterPanel(loader.connection);
			btnLogin = new JButton("LOGIN");
			btnLogin.setBounds(800, 732, 90, 25);
			btnLogin.setBackground(new Color(255, 90, 0));
			btnLogin.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {

					if (loginOpen == false) {
						if (registerOpen == true) {
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

			// Register-Button
			btnRegister = new JButton("REGISTER");
			btnRegister.setBounds(920, 732, 90, 25);
			btnRegister.setBackground(new Color(255, 90, 0));
			btnRegister.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					if (registerOpen == false) {
						if (loginOpen == true) {
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

			if (!loader.connection.isLoggedIn()) {
				panel.add(btnLogin);
				panel.add(btnRegister);
			} else {
				loggedIn(loader.connection.getName());
			}

			// #########################################################################
			//
			// SERVERLIST
			//
			// #########################################################################

		} else if (picName.equals("Wallpaper/serverlist.png")) {
			// LobbyList
			jp = new JPanel();
			scroller = new JScrollPane(jp,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroller.setBounds(142, 92, 789, 580);
			scroller.setOpaque(false);
			scroller.getViewport().setOpaque(false);
			panel.add(scroller);

			// JOIN-Button
			JButton btnJoin = new JButton("JOIN");
			btnJoin.setBounds(303, 689, 170, 60);
			btnJoin.setBackground(new Color(255, 90, 0));
			btnJoin.setFont(new Font("Arial", Font.BOLD, 24));
			btnJoin.setIcon(new ImageIcon("Wallpaper/Join_Pfeil.png"));
			btnJoin.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Beim klick auf dem "Join"-Buttons gelangt man in eine
					// Spielgruppe
					if (haspassword) {
						if (panelopen) {
							panel.remove(create);
							reload(panel);
							panelopen = false;
						}
						create = needPW();
						panel.add(create);
						panel.setComponentZOrder(create, 0);
						reload(panel);
					} else {
						loader.connection.connectGroup(markedLobby, "");
					}

				}
			});
			panel.add(btnJoin);

			// Create-Button
			JButton btnCreateGroup = new JButton("CREATE");
			btnCreateGroup.setBounds(626, 689, 170, 60);
			btnCreateGroup.setBackground(new Color(255, 90, 0));
			btnCreateGroup.setFont(new Font("Arial", Font.BOLD, 24));
			btnCreateGroup.setIcon(new ImageIcon("Wallpaper/Create_Pfeil.png"));
			btnCreateGroup.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Beim klick auf dem "Create"-Buttons gelangt man in eine
					// Spielgruppe, als Besitzer
					if (panelopen) {
						panel.remove(create);
						reload(panel);
						panelopen = false;
					}
					create = createInfos();
					panel.add(create);
					panel.setComponentZOrder(create, 0);
					reload(panel);
				}
			});
			panel.add(btnCreateGroup);

			// Refresh-Button
			JButton btnRefreshGroup = new JButton("Refresh");
			btnRefreshGroup.setBounds(510, 680, 85, 30);
			btnRefreshGroup.setBackground(new Color(255, 90, 0));
			btnRefreshGroup.setFont(new Font("Arial", Font.BOLD, 12));
			btnRefreshGroup.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					int map;
					if(map_ns.isSelected()){
						map = 1;
					} else {
						map = 0;
					}
					loader.connection.refreshServerList(checknoPW.isSelected(), nameContains.getText(), Integer.parseInt(minPlayers.getSelectedItem().toString()), Integer.parseInt(maxPlayers.getSelectedItem().toString()) , map);
				}
			});
			panel.add(btnRefreshGroup);

			// Back-Button
			JButton btnBACK = new JButton("BACK");
			btnBACK.setBounds(510, 715, 85, 30);
			btnBACK.setBackground(new Color(255, 90, 0));
			btnBACK.setFont(new Font("Arial", Font.BOLD, 12));
			btnBACK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// Der Weg zurück
					loader.switchPanel(loader.Mainpage);
				}
			});
			panel.add(btnBACK);
			
			//Filter-Options
			JLabel filter = new JLabel("Filter");
			filter.setBounds(10, 170, 120, 30);
			filter.setHorizontalAlignment(SwingConstants.CENTER);
			filter.setFont(new Font("Arial", Font.BOLD, 24));
			checknoPW = new JCheckBox("No Password");
			checknoPW.setBounds(10, 210, 130, 30);
			checknoPW.setOpaque(false);
			JLabel name = new JLabel("Name Filter");
			name.setBounds(10, 250, 130, 30);
			nameContains = new JTextField("");
			nameContains.setBounds(10, 290, 120, 30);
			JLabel min = new JLabel("Min Players");
			min.setBounds(10, 330, 130, 30);
			Integer[] playernumbers = { 0, 1, 2, 3, 4 };
			minPlayers = new JComboBox<Integer>(playernumbers);
			minPlayers.setBounds(10, 370, 60, 30);
			JLabel max = new JLabel("Max Players");
			max.setBounds(10, 410, 130, 30);
			maxPlayers = new JComboBox<Integer>(playernumbers);
			maxPlayers.setBounds(10, 450, 60, 30);
			maxPlayers.setSelectedIndex(4);
			JLabel map = new JLabel("Map Filter");
			map.setBounds(10, 490, 130, 30);
			map_ns = new JCheckBox("Nightsun");
			map_ns.setBounds(10, 520, 130, 30);
			map_ns.setOpaque(false);
			
			panel.add(filter);
			panel.add(map);
			panel.add(name);
			panel.add(min);
			panel.add(max);
			panel.add(checknoPW);
			panel.add(map_ns);
			panel.add(nameContains);
			panel.add(minPlayers);
			panel.add(maxPlayers);
			
			ImageIcon image = new ImageIcon("Wallpaper/SlotHolder.png");
			JLabel slotHolder = new JLabel(image);
			slotHolder.setBounds(-4, 100, 140, 600);
			panel.add(slotHolder);

			// #########################################################################
			//
			// LOBBY
			//
			// #########################################################################

		} else if (picName.equals("Wallpaper/Lobby.png")) {

			// Back-Button
			JButton btnBACK = new JButton("RETURN");
			btnBACK.setBounds(430, 695, 170, 60);
			btnBACK.setBackground(new Color(255, 90, 0));
			btnBACK.setFont(new Font("Arial", Font.BOLD, 24));
			btnBACK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					loader.connection.returnFromLobby();
				}
			});

			// Team bezeichungen
			for (int i = 0; i < 4; i++) {
				JLabel color = new JLabel();
				if (i == 0) {
					color.setText("BLUE");
					color.setForeground(Color.BLUE);
					color.setBounds(75, 150, 200, 50);
				} else if (i == 1) {
					color.setText("RED");
					color.setForeground(Color.RED);
					color.setBounds(75, 400, 200, 50);
				} else if (i == 2) {
					color.setText("YELLOW");
					color.setForeground(Color.YELLOW);
					color.setBounds(425, 150, 200, 50);
				} else {
					color.setText("GREEN");
					color.setForeground(Color.GREEN);
					color.setBounds(425, 400, 200, 50);
				}
				color.setHorizontalAlignment(SwingConstants.CENTER);
				color.setFont(new Font("Arial", Font.BOLD, 24));
				panel.add(color);
			}

			// Lobbby Player Showcase
			for (int i = 0; i < 4; i++) {
				JLabel players = new JLabel();
				if (i == 0 || i == 1) {
					players.setBounds(75, 230 + (250 * i), 200, 50);
				} else {
					players.setBounds(425, 230 + (250 * (i - 2)), 200, 50);
				}
				players.setHorizontalAlignment(SwingConstants.CENTER);
				players.setBackground(new Color(255, 90, 0));
				players.setFont(new Font("Arial", Font.BOLD, 24));
				players.setOpaque(true);
				labellist.add(players);
				panel.add(players);
			}

			// Chat
			tl = new JLabel(s);
			tl.setBounds(668, 350, 318, 334);
			tl.setForeground(Color.WHITE);
			tl.setVerticalAlignment(SwingConstants.TOP);
			JScrollPane scroller = new JScrollPane(tl,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroller.setBounds(666, 350, 320, 334);
			scroller.setOpaque(false);
			scroller.getViewport().setOpaque(false);
			ts = scroller.getVerticalScrollBar();
			JTextField tf = new JTextField();
			tf.setBounds(666, 684, 320, 30);
			tf.addActionListener(e -> {
				loader.connection.sendLobbyChatMessage(tf.getText());
				tf.setText("");
			});
			panel.add(scroller);
			panel.add(tf);
			panel.add(btnBACK);

			// #########################################################################
			//
			// LOBBY_Besitzer
			//
			// #########################################################################

		} else if (picName.equals("Wallpaper/Lobby_BESITZER.png")) {
			// Back-Button
			JButton btnBACK = new JButton("RETURN");
			btnBACK.setBounds(430, 695, 170, 60);
			btnBACK.setBackground(new Color(255, 90, 0));
			btnBACK.setFont(new Font("Arial", Font.BOLD, 24));
			btnBACK.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					loader.connection.returnFromLobby();
				}
			});
			panel.add(btnBACK);

			// Chat
			tl = new JLabel(s);
			tl.setBounds(668, 350, 318, 334);
			tl.setForeground(Color.WHITE);
			tl.setVerticalAlignment(SwingConstants.TOP);
			JScrollPane scroller = new JScrollPane(tl,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroller.setBounds(666, 350, 320, 334);
			scroller.setOpaque(false);
			scroller.getViewport().setOpaque(false);
			ts = scroller.getVerticalScrollBar();
			JTextField tf = new JTextField();
			tf.setBounds(666, 684, 320, 30);
			tf.addActionListener(e -> {
				loader.connection.sendLobbyChatMessage(tf.getText());
				tf.setText("");
			});
			panel.add(scroller);
			panel.add(tf);
			panel.add(btnBACK);

			// Team bezeichungen
			for (int i = 0; i < 4; i++) {
				JLabel color = new JLabel();
				if (i == 0) {
					color.setText("BLUE");
					color.setForeground(Color.BLUE);
					color.setBounds(75, 150, 200, 50);
				} else if (i == 1) {
					color.setText("RED");
					color.setForeground(Color.RED);
					color.setBounds(75, 400, 200, 50);
				} else if (i == 2) {
					color.setText("YELLOW");
					color.setForeground(Color.YELLOW);
					color.setBounds(425, 150, 200, 50);
				} else {
					color.setText("GREEN");
					color.setForeground(Color.GREEN);
					color.setBounds(425, 400, 200, 50);
				}
				color.setHorizontalAlignment(SwingConstants.CENTER);
				color.setFont(new Font("Arial", Font.BOLD, 24));
				panel.add(color);
			}
			// Lobby Host Player movement
			for (int i = 0; i < 4; i++) {
				JComboBox<String> players = new JComboBox<String>();
				JButton jb = new JButton("KICK");
				if (i == 0 || i == 1) {
					players.setBounds(75, 230 + (250 * i), 200, 50);
					jb.setBounds(100, 300 + (250 * i), 150, 40);
				} else {
					players.setBounds(425, 230 + (250 * (i - 2)), 200, 50);
					jb.setBounds(450, 300 + (250 * (i - 2)), 150, 40);
				}
				players.setFont(new Font("Arial", Font.BOLD, 24));
				jb.setBackground(new Color(255, 90, 0));
				jb.setFont(new Font("Arial", Font.BOLD, 24));
				players.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent arg0) {
						int[] i = newplayers();
						loader.connection.switchPlayers((byte) i[0],
								(byte) i[1]);
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

			// START-BATTLE-Button
			JButton btnBattleStart = new JButton("START");
			btnBattleStart.setBounds(135, 695, 170, 60);
			btnBattleStart.setBackground(new Color(255, 90, 0));
			btnBattleStart.setFont(new Font("Arial", Font.BOLD, 24));
			btnBattleStart.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					loader.connection.startGame();
				}
			});
			panel.add(btnBattleStart);
		}
		// #########################################################################
		//
		// Statspage
		//
		// #########################################################################
		else if (picName.equals("Wallpaper/statspage.png")) {
			// Back-Button
			JButton btnBack = new JButton("Back");
			btnBack.setBounds(471, 689, 170, 60);
			btnBack.setBackground(new Color(255, 90, 0));
			btnBack.setFont(new Font("Arial", Font.BOLD, 24));
			btnBack.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent arg0) {
					loader.switchPanel(loader.Mainpage);
				}
			});
			panel.add(btnBack);

			JLabel welcome = new JLabel("Stats for "
					+ loader.connection.getName());
			welcome.setBounds(150, 95, 500, 40);
			welcome.setFont(new Font("Arial", Font.BOLD, 24));
			welcome.setForeground(Color.RED);
			panel.add(welcome);
		}
	}
		
	/**
	 * Erstellt die Lables zur darstellung der Stats
	 * 
	 * @param r String Array mit Stats
	 */
	public void showStats(String[][] r) {
		for (int i = 0; i < r.length; i++) {
			JLabel Stat = new JLabel(r[i][0]);
			JLabel Statnumber = new JLabel(r[i][1]);
			Stat.setBounds(200 + i % 4 * 170, 150 + 125 * (i >> 2), 150, 40);
			Statnumber.setBounds(200 + i % 4 * 170, 200 + 125 * (i >> 2),
					150, 40);
			Stat.setForeground(Color.RED);
			Statnumber.setForeground(Color.RED);
			Stat.setHorizontalAlignment(SwingConstants.CENTER);
			Statnumber.setHorizontalAlignment(SwingConstants.CENTER);
			Stat.setFont(new Font("Arial", Font.BOLD, 24));
			Statnumber.setFont(new Font("Arial", Font.BOLD, 24));
			panel.add(Stat);
			panel.add(Statnumber);
			reload(panel);
		}
	}

	/**
	 * Fügt die eingehende Nachricht dem Chatfeld hinzu
	 * 
	 * @param text
	 *            Eingehende Nachricht
	 */
	public void setText(String text) {
		s = s + "<br>" + text;
		tl.setText("<html>" + s + "</html>");
		// Wird benötigt damit der Chat wirklich zum ende Scrollt
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int s = ts.getModel().getMaximum() + ts.getModel().getExtent();
		ts.setValue(s);
	}

	/**
	 * Updatet die JComboBoxen des Lobby Hosts mit den namen der neuen Spieler
	 * 
	 * @param player
	 *            Spieler Array mit den Namen aller Spieler die sich in der
	 *            Lobby befinden
	 */
	public void updateCombo(String[] player) {
		standartselect = player;
		for (int i = 0; i < 4; i++) {
			combolist.get(i).setModel(new DefaultComboBoxModel<String>(player));
			combolist.get(i).setSelectedIndex(i);
		}
		reload(panel);
	}

	/**
	 * Updatet die JLabels der anderen mitspieler der Lobby
	 * 
	 * @param player
	 *            Spieler Array mit den Namen aller Spieler die sich in der
	 *            Lobby befinden
	 */
	public void updateLabels(String[] player) {
		for (int i = 0; i < 4; i++) {
			labellist.get(i).setText(player[i]);
		}
		reload(panel);
	}

	/**
	 * Liest die zu tauschenden Spielernummern aus und speichert diese in einem
	 * Array
	 * 
	 * @return Int Array mit Plätzen der zutauschenden Spieler
	 */
	public int[] newplayers() {
		int[] numbers = new int[2];
		for (int i = 0; i < standartselect.length; i++) {
			if (!standartselect[i].equals(combolist.get(i).getSelectedItem()
					.toString())) {
				numbers[0] = i;
			}
		}
		for (int i = 0; i < standartselect.length; i++) {
			if (standartselect[i].equals(combolist.get(numbers[0])
					.getSelectedItem().toString())) {
				numbers[1] = i;
			}
		}
		return numbers;
	}

	/**
	 * Erstellt ein JPanel anhand der Informationen über die Lobby mit einem
	 * ActionListener der die Lobby auswählen lässt
	 * 
	 * @param lobbyNr
	 *            Nummer der Lobby
	 * @param lobby
	 *            Das Lobby Objekt mit den nötigen Informationen
	 * @return JPanel mit Informationen über die Lobby
	 */
	public JPanel genNEWLobby(int lobbyNr, Lobby lobby) {
		JPanel game1 = new JPanel();
		game1.setBounds(10, 10 + (160 * (lobbyNr)), 755, 150);
		game1.setLayout(null);
		game1.setOpaque(true);
		game1.setBackground(new Color(255, 90, 0));
		game1.setFocusable(true);
		game1.setRequestFocusEnabled(true);
		game1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				markedLobby = lobby.getID();
				haspassword = lobby.hasPassword();
				if (panelopen) {
					panel.remove(create);
					reload(panel);
					panelopen = false;
				}
				if (gametemp != null) {
					gametemp.setBorder(null);
				}
				gametemp = game1;
				game1.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5,
						Color.BLACK));
			}
		});
		JLabel map_pic = new JLabel();
		JLabel pw_en = new JLabel();
		JLabel lobby_name = new JLabel(lobby.getName());
		JLabel player_count = new JLabel("<html>Players:<br>"
				+ lobby.getNumberOfPlayers() + "/4</html>");
		switch (lobby.getMapID()) {
		case (2):
			map_pic.setIcon(map1);
			break;
		}
		pw_en.setIcon(open);
		if (lobby.hasPassword()) {
			pw_en.setIcon(closed);
		}
		map_pic.setBounds(10, 10, 200, 130);
		pw_en.setBounds(220, 20, 70, 45);
		lobby_name.setBounds(300, 20, 445, 110);
		player_count.setBounds(220, 85, 70, 45);
		lobby_name.setHorizontalAlignment(SwingConstants.CENTER);
		pw_en.setHorizontalAlignment(SwingConstants.CENTER);
		player_count.setHorizontalAlignment(SwingConstants.CENTER);
		lobby_name.setFont(new Font("Arial", Font.BOLD, 24));
		player_count.setFont(new Font("Arial", Font.BOLD, 24));
		game1.add(map_pic);
		game1.add(pw_en);
		game1.add(lobby_name);
		game1.add(player_count);

		return game1;
	}

	/**
	 * Erstellt die Lobby Panels und fügt diese einem JPanel hinzu was dann im
	 * JScrollPane angezeigt wird
	 * 
	 * @param lobbyList
	 *            Liste aller vorhanden Lobbys
	 */
	public void updateLobbyList(Lobby[] lobbyList) {
		JPanel lobbyPanel = new JPanel();
		lobbyPanel.setBounds(0, 0, 789, 800);
		lobbyPanel.setOpaque(false);
		lobbyPanel.setLayout(null);
		lobbyPanel.setPreferredSize(new Dimension(789, 0));
		for (int i = 0; i < lobbyList.length; i++) {
			lobbyPanel.add(genNEWLobby(i, lobbyList[i]));
		}
		lobbyPanel.setPreferredSize(new Dimension(789,
				10 + (160 * lobbyList.length)));
		scroller.remove(jp);
		scroller.setViewportView(lobbyPanel);
		jp = lobbyPanel;
		reload(scroller);
	}

	/**
	 * JPanel mit zwei Eingabe Feldern für das erstellen einer Lobby
	 * 
	 * @return JPanel
	 */
	public JPanel createInfos() {
		JPanel en = new JPanel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 7618787260533400660L;

			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D f2 = (Graphics2D) g;
				f2.drawImage(new ImageIcon("Wallpaper/popup.png").getImage(), 0, 0, null);
				paintChildren(g);
				g.setColor(Color.RED);
			}
		};
		en.setLayout(null);
		en.setOpaque(true);
		//473 264
		en.setBounds(244, 283, 585, 199);
		en.setBackground(Color.RED);
		JLabel nl = new JLabel("Lobbyname: ");
		JLabel pl = new JLabel("Password: ");
		JButton c = new JButton("Create");
		JTextField n = new JTextField();
		JTextField p = new JTextField();
		nl.setFont(new Font("Arial", Font.BOLD, 12));
		nl.setForeground(Color.WHITE);
		pl.setFont(new Font("Arial", Font.BOLD, 12));
		pl.setForeground(Color.WHITE);
		c.setFont(new Font("Arial", Font.BOLD, 12));
		n.setFont(new Font("Arial", Font.BOLD, 12));
		p.setFont(new Font("Arial", Font.BOLD, 12));
		c.setBackground(new Color(255, 90, 0));
		nl.setBounds(20, 40, 130, 50);
		pl.setBounds(20, 130, 130, 50);
		n.setBounds(150, 40, 230, 50);
		p.setBounds(150, 130, 230, 50);
		c.setBounds(440, 80, 100, 60);
		c.addActionListener(e -> {
			loader.connection.createGroup(2, n.getText(), p.getText());
		});
		en.add(p);
		en.add(n);
		en.add(nl);
		en.add(pl);
		en.add(c);
		panelopen = true;
		return en;
	}

	/**
	 * Eingabefeld falls Lobby ein Passwort besitzt
	 * 
	 * @return JPanel
	 */
	public JPanel needPW() {
		JPanel np = new JPanel(){
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 7618787260533400660L;

			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D f2 = (Graphics2D) g;
				f2.drawImage(new ImageIcon("Wallpaper/popup.png").getImage(), 0, 0, null);
				paintChildren(g);
				g.setColor(Color.RED);
			}
			
		};
		JLabel ep = new JLabel(
				"This Lobby has a Password. Please enter it and press Enter");
		JTextField tp = new JTextField();
		tp.addActionListener(e -> {
			loader.connection.connectGroup(markedLobby, tp.getText());
		});
		tp.setBounds(117, 80, 350, 50);
		ep.setBounds(117, 20, 350, 50);
		ep.setFont(new Font("Arial", Font.BOLD, 12));
		ep.setForeground(Color.WHITE);
		np.setLayout(null);
		np.setOpaque(true);
		np.setBounds(244, 283, 585, 199);
		np.setBackground(Color.RED);
		np.add(tp);
		np.add(ep);
		panelopen = true;
		return np;
	}

	/**
	 * Ersetzt den Login und Register Button mit einem Logout Button und einem
	 * JLabel falls der Nutzer eingeloggt ist
	 * 
	 * @param name
	 *            Name des Nutzers
	 */
	public void loggedIn(String name) {
		panel.remove(btnLogin);
		panel.remove(btnRegister);
		loginRegisterPanel.popupdestroy(panel);
		JLabel user = new JLabel("Willkommen " + name);
		user.setBounds(755, 732, 155, 25);
		JButton btnlogout = new JButton("Logout");
		btnlogout.setBounds(920, 732, 90, 25);
		btnlogout.setBackground(new Color(255, 90, 0));
		btnlogout.addActionListener(e -> {
			loader.connection.logout();
			panel.remove(btnlogout);
			panel.remove(user);
			panel.add(btnLogin);
			panel.add(btnRegister);
			reload(panel);
		});
		panel.add(btnlogout);
		panel.add(user);
		reload(panel);
	}
	 /**
	  * Repaintet und revalidatet den übergebenen Component
	  * 
	  * @param c Component der Repaint werden soll
	  */
	public void reload(Component c){
		c.repaint();
		c.revalidate();
	}
}
