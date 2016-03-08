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
import de.szut.client.logic.UnitData;

/**
 * 
 * @author Alexander, Simeon
 *
 */
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
	UnitData dataOfUnit;

	/**
	 * Initialisiert ein ActionButton Objekt
	 * 
	 * @param loader
	 *            Der Loader des Clients
	 */
	public ActionButton(Loader loader) {
		this.loader = loader;
		this.dataOfUnit = new UnitData();
		this.dataOfUnit.createUnitData();
	}

	/**
	 * Formatiert einen Text
	 * 
	 * @param s
	 *            zu Formatierender Text
	 * @return formatierter Text
	 */
	private static String wrapLines(String s) {
		return String.format("<html>%s</html>", s);
	}

	/**
	 * Unitknöpfe werden auf ein Panel gesetzt, alle haben ein MouseListener
	 * 
	 * @param console
	 */
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

	/**
	 * Hier werden alle Gebäudeaktionen erstellt und auf ein Panel gesetzt, alle
	 * Knöpfe besitzen MouseListener Jede Aktion wird hier an den Server
	 * gerichtet
	 * 
	 * @param console
	 * @param game
	 * @param buildingsArray
	 * @param listOfJProgressBar
	 * @param slotID
	 * @param primID
	 * @param func
	 */
	public void createUserOptions(Panel console, Game game,
			Buildings[] buildingsArray, JProgressBar[] listOfJProgressBar,
			int slotID, int primID, Funktions func) {

		// Alle Optionen und andere Felder werden auf der Console gelöscht
		deselectOptions(console);
		game.setAllJProgressBarVisible(false);

		// Unterscheidung von Slots und gebäude
		if (primID == 0) {
			// Ein Slot wurde ausgewählt
			noName = buildingsArray[slotID].getSpwanableEntity();
			if (listOfJProgressBar[slotID] != null) {
				noName = new String[1];

				noName[0] = "Cancel";
				game.replaceJProcessbar(slotID);
			}
		} else {
			// Ein Gebäude wurde ausgewählt
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

				// MouseListener startet eine übersetztung des Knopfes, welche
				// Aktion ausgeführt werden soll
				// Möglichkeiten:
				// Ground, Air, Building, Generation, Destroy, Cancel
				public void mouseReleased(MouseEvent arg0) {
					if (type.equals("Building")) {
						loader.connection.createBuilding(
								slotIDToBuildingPosition(slotID),
								cutHTMLout(((JButton) arg0.getSource())
										.getText()));
					} else if (type.equals("Ground")) {
						switch (cutHTMLout(((JButton) arg0.getSource())
								.getText())) {
						case ("Marine"):
							loader.connection.createUnit(1,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Chronite Tank"):
							loader.connection.createUnit(2,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Sniper"):
							loader.connection.createUnit(3,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Gröditz"):
							loader.connection.createUnit(4,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Hover Tank"):
							loader.connection.createUnit(5,
									slotIDToBuildingPosition(slotID));
							break;
						case ("A25-Roman"):
							loader.connection.createUnit(7,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Sakata-MK2"):
							loader.connection.createUnit(10,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Sakata Spider"):
							loader.connection.createUnit(11,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Gladiator"):
							loader.connection.createUnit(12,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Meditec"):
							loader.connection.createUnit(13,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Sphinx"):
							loader.connection.createUnit(15,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Modified Sakata"):
							loader.connection.createUnit(17,
									slotIDToBuildingPosition(slotID));
							break;
						}

					} else if (type.equals("Air")) {
						switch (cutHTMLout(((JButton) arg0.getSource())
								.getText())) {
						case ("Black Queen"):
							loader.connection.createUnit(6,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Scout"):
							loader.connection.createUnit(8,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Phantom"):
							loader.connection.createUnit(9,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Saint"):
							loader.connection.createUnit(14,
									slotIDToBuildingPosition(slotID));
							break;
						case ("Modified Phantom"):
							loader.connection.createUnit(16,
									slotIDToBuildingPosition(slotID));
							break;
						}
					} else if (type.equals("Generation")) {
					} else if (type.equals("Destroy")) {
						loader.connection
								.destroyBuilding(slotIDToBuildingPosition(slotID));
					} else if (type.equals("Cancel")) {
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
			int []cost = searchForPrice(noName[n]);
			if (cost != null){
				String costString = "";
				for (int i = 0; i != cost.length; i++){
					costString = costString + " " + cost[i];
				}
				btn.setToolTipText(costString);
			}else{
				if (dataOfUnit.returnUnitData(noName[n]) != null){
					cost = dataOfUnit.returnUnitData(noName[n]).getPrice();
					String costString = "";
					for (int i = 0; i != cost.length; i++){
						costString = costString + " " + cost[i];
					}
					btn.setToolTipText(costString);
				}
			}
			
			jButton.add(btn);
			console.add(btn);
		}

		// Neue Beschreibungen werden gesetzt
		if (primID == 0) {
			Description.setText(wrapLines(buildingsArray[slotID]
					.getDescription()));
		} else {
			Description.setText(wrapLines(buildingsArray[primID]
					.getDescription()));
		}
		Description.setForeground(Color.BLACK);
		Description.setBounds(20, -50, 180, 300);

		// Neue Gebäudename wird auf die Konsole gesetzt
		if (primID == 0) {
			if (buildingsArray[slotID].getPrimerBuilding() == null) {
				BuildingNameLbl.setText(wrapLines(buildingsArray[slotID]
						.getName()));
			} else {
				BuildingNameLbl.setText(wrapLines(buildingsArray[slotID]
						.getPrimerBuilding().getName()));
			}
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
	
	/**
	 * Preislisten für Gebäude
	 * 
	 * @param buildingName
	 *            Name des Gebäudes
	 * @return Preis des Gebäudes
	 */
	private int[] searchForPrice(String buildingName) {
		switch (buildingName) {
		case "Outpost":
			return new int[] { 40, 30, 30, 00 };
		case "Barracks":
			return new int[] { 30, 00, 00, 00 };
		case "Arsenal":
			return new int[] { 80, 50, 00, 00 };
		case "Forge":
			return new int[] { 40, 30, 30, 00 };
		case "Manufactory":
			return new int[] { 60, 30, 30, 00 };
		case "Mechanics Terminal":
			return new int[] { 70, 20, 00, 00 };
		case "Armory":
			return new int[] { 60, 25, 25, 00 };
		case "Generator":
			return new int[] { 50, 00, 00, 00 };
		case "Solar Grid":
			return new int[] { 50, 00, 00, 00 };
		case "Bank":
			return new int[] { 50, 00, 00, 00 };
		case "Treasury":
			return new int[] { 50, 00, 00, 00 };
		case "Hospital":
			return new int[] { 40, 30, 10, 00 };
		case "War Sanctum":
			return new int[] { 65, 00, 00, 00 };
		case "Special Operations":
			return new int[] { 80, 30, 30, 00 };
		default:
			return null;
		}
	}
	
	/**
	 * Schreibt die Beschreibung um auf der Konsole
	 * 
	 * @param description
	 */
	public void changeDescription(String description) {
		Description.setText(wrapLines(description));
	}

	/**
	 * Schreibt den angezeigten Namen um
	 * 
	 * @param type
	 */
	public void changeBuildingName(String type) {
		BuildingNameLbl.setText(wrapLines(type));
	}

	/**
	 * Alle Optionen werden entfernt und das Arraylist wird geleert
	 * 
	 * @param panel
	 */
	public void deselectOptions(Panel panel) {
		JButton[] jButtonArray = new JButton[jButton.size()];
		jButtonArray = jButton.toArray(jButtonArray);
		if (jButtonArray.length != 0) {
			for (JButton n : jButtonArray) {
				panel.remove(n);
			}
		}
		jButton.clear();
		panel.repaint();
	}

	/**
	 * Aus einem String wird die Aktion ermittelt, die eine spezielle Funktion
	 * aufruft Falls keine Aktion gefunden wird, wird NULL zurück gegeben
	 * 
	 * @param EntityName
	 * @return EntityAktion
	 */
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

	/**
	 * Schneidet aus einem HTML String die Tags aus
	 */
	public String cutHTMLout(String html) {
		String ButtonName = html.substring(6);
		String[] parts = ButtonName.split("<");
		ButtonName = parts[0];
		return ButtonName;
	}

	/**
	 * Löscht mit einem Funktionsaufruf alle Useroptionen aus dem übergebenden
	 * Panel
	 * 
	 * @param console
	 */
	public void destroyUserOptions(Panel console) {
		deselectOptions(console);
	}

	/**
	 * Rechnet die SlotID in eine Zahl von 0-4 um
	 * 
	 * @param slotID
	 * @return
	 */
	private int slotIDToBuildingPosition(int slotID) {
		return (slotID - (9 * (slotID / 10) + 1) & 3);
	}
}
