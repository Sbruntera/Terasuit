package de.szut.client.ingame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import de.szut.client.grafik.Loader;
import de.szut.client.grafik.Panel;

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
				loader.connection.moveUnit(loader.game.getSelectedUnits(),
						true, true, false);
			}
		});
		jButton.add(btn);
		console.add(btn);

		btn = new JButton(wrapLines("Fast Forward"));
		btnCreator.createOne(btn, 200 + (1 * 62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				loader.connection.moveUnit(loader.game.getSelectedUnits(),
						true, true, true);
			}
		});
		jButton.add(btn);
		console.add(btn);

		btn = new JButton(wrapLines("Stay"));
		btnCreator.createOne(btn, 200 + (2 * 62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				loader.connection.moveUnit(loader.game.getSelectedUnits(),
						true, false, false);
			}
		});
		jButton.add(btn);
		console.add(btn);

		btn = new JButton(wrapLines("Retreat"));
		btnCreator.createOne(btn, 200 + (3 * 62), 30, 60, 60, 87);
		btn.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				loader.connection.moveUnit(loader.game.getSelectedUnits(),
						false, true, false);
			}
		});
		jButton.add(btn);
		console.add(btn);
	}

	// Gebäudeoptionen
	public void createUserOptions(Panel console, Game game,
			Buildings[] buildingsArray, JProgressBar[] listOfJProgressBar,
			int slotID, int primID, Funktions func) {
		deselectOptions(console);
		game.setAllJProgressBarVisible(false);
		if (primID == 0) {
			noName = buildingsArray[slotID].getSpwanableEntity();
			if (listOfJProgressBar[slotID] != null) {
				noName = new String[1];
				noName[0] = "Cancel";
				game.replaceJProcessbar(slotID);
			}
		} else {
			noName = buildingsArray[primID].getSpwanableEntity();
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
						System.out.println(slotID);
						loader.connection.createBuilding(
								slotIDToBuildingPosition(slotID),
								cutHTMLout(((JButton) arg0.getSource())
										.getText()));
					} else if (type.equals("Ground")) {
						System.out.println(slotID);
						System.out
								.println("Eine Bodeneinheit wurde ausgewählt!");
						switch (cutHTMLout(((JButton) arg0.getSource())
								.getText())) {
						case ("Marine"):
							System.out.println("Marine");
							loader.connection.createUnit(1,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Chronite Tank"):
							System.out.println("Chronit Tank");
							loader.connection.createUnit(2,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Sniper"):
							System.out.println("Sniper");
							loader.connection.createUnit(3,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Gröditz"):
							System.out.println("Gröditz");
							loader.connection.createUnit(4,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Hover Tank"):
							System.out.println("Hover Tank");
							loader.connection.createUnit(5,
									slotIDToBuildingPosition(slotID));
							break;
						case ("A25-Roman"):
							System.out.println("A25-Roman");
							loader.connection.createUnit(7,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Sakata-MK2"):
							System.out.println("Sakata-Mk2");
							loader.connection.createUnit(10,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Sakata Spider"):
							System.out.println("Sakata Spider");
							loader.connection.createUnit(11,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Gladiator"):
							System.out.println("Gladiator");
							loader.connection.createUnit(12,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Meditec"):
							System.out.println("Meditec");
							loader.connection.createUnit(13,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Sphinx"):
							System.out.println("Sphinx");
							loader.connection.createUnit(15,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Modified Sakata"):
							System.out.println("Modified Sakata");
							loader.connection.createUnit(17,
									slotIDToBuildingPosition(slotID));
							break;
						}

					} else if (type.equals("Air")) {
						System.out
								.println("Eine Lufteinheit wurde ausgewählt!");
						switch (cutHTMLout(((JButton) arg0.getSource())
								.getText())) {
						case ("Black Queen"):
							System.out.println("Black Queen");
							loader.connection.createUnit(6,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Scout"):
							System.out.println("Scout");
							loader.connection.createUnit(8,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Phantom"):
							System.out.println("Phantom");
							loader.connection.createUnit(9,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Saint"):
							System.out.println("Saint");
							loader.connection.createUnit(14,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Modified Phantom"):
							System.out.println("Modified Phantom");
							loader.connection.createUnit(16,
									slotIDToBuildingPosition(slotID));
							break;
						}
					} else if (type.equals("Generation")) {
						System.out
								.println("Eine generierung wurde ausgewählt!");
					} else if (type.equals("Destroy")) {
						System.out
								.println("Das gewählte Gebäude wird abgerissen");
						loader.connection
								.destroyBuilding(slotIDToBuildingPosition(slotID));
					} else if (type.equals("Cancel")) {
						System.out.println("Abbruch");
						loader.connection.cancelBuilding(slotID);
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

	public void changeDescription(String description) {
		Description.setText(wrapLines(description));
	}

	public void changeBuildingName(String type) {
		BuildingNameLbl.setText(wrapLines(type));
	}

	public void deselectOptions(Panel panel) {
		JButton[] jButtonArray = new JButton[jButton.size()];
		jButtonArray = jButton.toArray(jButtonArray);
		if (jButtonArray.length != 0) {
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

	private int slotIDToBuildingPosition(int slotID) {
		return (slotID - (9 * (slotID / 10) + 1) & 3);
	}
}
