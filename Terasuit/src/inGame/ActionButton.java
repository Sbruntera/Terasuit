package inGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import grafig.Loader;
import grafig.Panel;

public class ActionButton {

	BtnCreator btnCreator = new BtnCreator();
	JButton btnForward = new JButton("Forward");
	JButton btnBackward = new JButton("Backward");
	JButton btnBuilding = new JButton("Building");
	JButton btnSpawnSoldir = new JButton("Building");
	ArrayList<JButton> ContentButtonArray = new ArrayList<JButton>();
	ArrayList<JButton> jButton = new ArrayList<JButton>();
	JLabel BuildingNameLbl = new JLabel("");
	JLabel Description = new JLabel("");
	boolean preload = false;
	JButton btn;
	String[] noName;
	private Loader loader;

	public ActionButton(Loader loader) {
		this.loader = loader;
	}

	private static String wrapLines(String s) {
		return String.format("<html>%s</html>", s);
	}

	public void createUserUnitOptions(Panel console) {
		deselectOptions(console);
		btn = new JButton(wrapLines("Forward"));
		btnCreator.createOne(btn, 200 + (0 * 62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("Forward! MY FIRENDS ATACKKKKKK!!!");
			}
		});
		jButton.add(btn);
		console.add(btn);

		btn = new JButton(wrapLines("Fast Forward"));
		btnCreator.createOne(btn, 200 + (1 * 62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("NOW! ATTACK THE BASE!");
			}
		});
		jButton.add(btn);
		console.add(btn);

		btn = new JButton(wrapLines("Stay"));
		btnCreator.createOne(btn, 200 + (2 * 62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("Stop guys. Let them come!");
			}
		});
		jButton.add(btn);
		console.add(btn);

		btn = new JButton(wrapLines("Retreat"));
		btnCreator.createOne(btn, 200 + (3 * 62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				System.out.println("Reverse of Forward!!");
			}
		});
		jButton.add(btn);
		console.add(btn);
	}

	// Gebäudeoptionen
	public void createUserOptions(Panel console, Game game,
			Buildings[] buildingsArray, JProgressBar[] listOfJProgressBar,
			int slotID, int primID, Funktions func) {
		System.out.println("slotID " + slotID);
		System.out.println("primID " + primID);
		deselectOptions(console);
		game.setAllJProgressBarVisible(false);
		if (primID == 0) {
			noName = buildingsArray[slotID].getSpwanableEntity();
			System.out.println("1");
			if (listOfJProgressBar[slotID] != null) {
				noName = new String[1];
				noName[0] = "Cancel";
				game.replaceJProcessbar(slotID);
			}
		} else {
			noName = buildingsArray[primID].getSpwanableEntity();
			System.out.println("3");
			if (listOfJProgressBar[slotID] != null) {
				noName = new String[1];
				noName[0] = "Cancel";
				game.replaceJProcessbar(slotID);
			}
		}

		// Generiert alle möglichen Gebäudeoptionen
		for (int n = 0; n != noName.length; n++) {
			btn = new JButton(wrapLines(noName[n]));
			btnCreator.createOne(btn, 200 + (n * 62), 30, 60, 60, 87);
			String type = getEntityAction(noName[n]);

			btn.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent arg0) {
					if (type.equals("Building")) {
						System.out.println("Ein Gebäude wurde ausgewählt!");
						loader.connection.createBuilding((slotID
								- (9 * (slotID / 10) + 1) & 3),
								cutHTMLout(((JButton) arg0.getSource())
										.getText()));
					} else if (type.equals("Ground")) {
						System.out
								.println("Eine Bodeneinheit wurde ausgewählt!");
						switch (cutHTMLout(((JButton) arg0.getSource())
								.getText())) {
						case ("Marine"):
							System.out.println("Marine");
							loader.connection.createUnit(1, (slotID
									- (9 * (slotID / 10) + 1) & 3));
							break;
						case ("Chronite Tank"):
							System.out.println("Chronit Tank");
							loader.connection.createUnit(2, (slotID
									- (9 * (slotID / 10) + 1) & 3));
							break;
						case ("Sniper"):
							System.out.println("Sniper");
							loader.connection.createUnit(3, (slotID
									- (9 * (slotID / 10) + 1) & 3));
							break;
						case ("Gröditz"):
							System.out.println("Gröditz");
							loader.connection.createUnit(4, (slotID
									- (9 * (slotID / 10) + 1) & 3));
							break;
						case ("Hover Tank"):
							System.out.println("Hover Tank");
							loader.connection.createUnit(5, (slotID
									- (9 * (slotID / 10) + 1) & 3));
							break;
						}

					} else if (type.equals("Air")) {
						System.out
								.println("Eine Lufteinheit wurde ausgewählt!");
						switch (cutHTMLout(((JButton) arg0.getSource())
								.getText())) {
						case ("Black Queen"):
							System.out.println("Black Queen");
							loader.connection.createUnit(6, (slotID
									- (9 * (slotID / 10) + 1) & 3));
							break;
						}
					} else if (type.equals("Generation")) {
						System.out
								.println("Eine generierung wurde ausgewählt!");
					} else if (type.equals("Destroy")) {
						System.out
								.println("Das gewählte Gebäude wird abgerissen");
						System.out.println(primID + "jalla");
						loader.connection.destroyBuilding((slotID
								- (9 * (slotID / 10) + 1) & 3));
					} else if (type.equals("Cancel")) {
						System.out.println("Abbruch");

						if (primID == 0) {
							loader.connection.cancelBuilding(slotID);
							game.cancel(slotID);
						} else {
							game.cancel(primID);
						}
					} else if (type.equals("null")) {
						System.out
								.println("Keine Option für dieses Button vorhanden!!");
					} else {
						System.out
								.println("Kritischer Fehler: ActionButton.java => getEntityType.mth");
					}
				}
			});
			jButton.add(btn);
			console.add(btn);
		}

		if (primID == 0) {
			Description.setText(wrapLines(buildingsArray[slotID]
					.getDescription()));
		} else {
			Description.setText(wrapLines(buildingsArray[primID]
					.getDescription()));
		}
		Description.setForeground(Color.BLACK);
		Description.setBounds(20, -50, 180, 300);

		if (primID == 0) {
			BuildingNameLbl
					.setText(wrapLines(buildingsArray[slotID].getName()));
		} else {
			BuildingNameLbl
					.setText(wrapLines(buildingsArray[primID].getName()));
		}
		BuildingNameLbl.setForeground(Color.BLACK);
		BuildingNameLbl.setFont(new Font("Arial", Font.PLAIN, 19));
		BuildingNameLbl.setBounds(20, -20, 180, 100);
		if (preload == false) {
			console.add(BuildingNameLbl);
			console.add(Description);
			this.preload = true;
		}
		console.repaint();
	}
	
	public void changeDescription(String description){
		Description.setText(wrapLines(description));
	}
	
	public void changeBuildingName(String type){
		BuildingNameLbl.setText(wrapLines(type));
	}

	public void deselectOptions(Panel panel) {
		JButton[] jButtonArray = new JButton[jButton.size()];
		jButtonArray = jButton.toArray(jButtonArray);
		if (jButtonArray.length == 0) {
		} else {
			for (JButton n : jButtonArray) {
				panel.remove(n);
			}
		}
		jButton.clear();
		;
		panel.repaint();
	}

	public String getEntityAction(String EntityName) {
		File file;
		file = new File("Buildings/" + EntityName + ".png");
		if (file.exists()) {
			return "Building";
		}
		file = new File("Unit/Air/" + EntityName + ".png");
		if (file.exists()) {
			return "Air";
		}
		file = new File("Unit/Ground/" + EntityName + ".png");
		if (file.exists()) {
			return "Ground";
		}

		switch (EntityName) {
		case "Recruit":
			return "Generation";
		case "Salvage":
			return "Generation";
		case "Financel Support":
			return "Generation";
		case "Reinforcments":
			return "Generation";
		case "Reserve Energy":
			return "Generation";
		case "Power":
			return "Generation";
		case "Exhange":
			return "Generation";
		case "Traiding":
			return "Generation";
		case "Resuscitate":
			return "Generation";
		case "Recover":
			return "Generation";
		case "Black Operations":
			return "Generation";
		case "Destroy":
			return "Destroy";
		case "Cancel":
			return "Cancel";
		}
		return "null";
	}

	public String cutHTMLout(String html) {
		String ButtonName = html.substring(6);
		String[] parts = ButtonName.split("<");
		ButtonName = parts[0];
		return ButtonName;
	}

	public void destroyUserOptions(Panel console) {
		deselectOptions(console);
	}

}
