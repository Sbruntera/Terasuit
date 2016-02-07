package inGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

import grafig.Loader;
import grafig.Panel;

public class ActionButton {

//	Funktions func = new Funktions();
	BtnCreator btnCreator = new BtnCreator();
	JButton btnForward = new JButton("Forward");
	JButton btnBackward = new JButton("Backward");
	JButton btnBuilding = new JButton("Building");
	JButton btnSpawnSoldir = new JButton("Building");
	ArrayList<JButton> ContentButtonArray = new ArrayList<JButton>();
	
	ArrayList<JButton> jButton = new ArrayList<JButton>();
	JButton btn;
	private static String wrapLines(String s) {
		return String.format("<html>%s</html>", s);
	}
	
	// Gebäudeoptionen
	public void createUserOptions(Panel console, Panel field, ArrayList<Buildings> BuildingsEntity, int i, Loader load, Funktions func){
		deselectOptions(console);
		String [] noName  = BuildingsEntity.get(i).getSpwanableEntity();
		// Generiert alle möglichen Gebäudeoptionen
		for (int n = 0; n != noName.length; n++){
			btn = new JButton(wrapLines(noName[n]));
			btnCreator.createOne(btn, 200+(n*62), 30, 60, 60, 87);
			String type = getEntityAction(noName[n]);
			
			btn.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent arg0) {
					if (type.equals("Building")){
						System.out.println("Ein Gebäude wurde ausgewählt!");
					}else if (type.equals("Ground")){
						System.out.println("Eine Bodeneinheit wurde ausgewählt!");
						int number = (int) (Math.random()*4)+1;
						String UnitString = "Unit/Ground/" + cutHTMLout(((JButton)arg0.getSource()).getText())+ ".png";
						func.createEntity(field, UnitString, number, false);
					}else if (type.equals("Air")){
						System.out.println("Eine Lufteinheit wurde ausgewählt!");
						int number = (int) (Math.random()*4)+1;
						String UnitString = "Unit/Air/" + cutHTMLout(((JButton)arg0.getSource()).getText())+ ".png";
						func.createEntity(field, UnitString, number, true);
					}else if (type.equals("Generation")){
						System.out.println("Eine generierung wurde ausgewählt!");
					}else if (type.equals("Destroy")){
						System.out.println("Das gewählte Gebäude wird abgerissen");
					}else if (type.equals("null")){
						System.out.println("Keine Option für dieses Button vorhanden!!");
					}else{
						System.out.println("Kritischer Fehler: ActionButton.java => getEntityType.mth");
					}	
				}
			});
			jButton.add(btn);
			console.add(btn);
		}
		System.out.println(jButton.size());
		JLabel Description = new JLabel("");
		Description.setText(wrapLines(BuildingsEntity.get(i).getDescription()));
		Description.setForeground(Color.BLACK);
		Description.setBounds(20, -50, 180, 300);
		console.add(Description);
		
		JLabel BuildingNameLbl = new JLabel("");
		BuildingNameLbl.setText(wrapLines(BuildingsEntity.get(i).getName()));
		BuildingNameLbl.setForeground(Color.BLACK);
		BuildingNameLbl.setFont(new Font("Arial", Font.PLAIN, 19));
		BuildingNameLbl.setBounds(20, -20, 180, 100);
		console.add(BuildingNameLbl);

		console.repaint();
	}
	
	public void deselectOptions(Panel panel){
		JButton[] jButtonArray = new JButton[jButton.size()];
		jButtonArray = jButton.toArray(jButtonArray);
		if (jButtonArray.length == 0){
		}else{
			
			for (JButton n : jButtonArray){
				panel.remove(n);
			}
		}
		jButton.clear();;
		panel.repaint();
	}

	// Ändert einen Zustand eine Entitys
	public void Entity(int i, Panel panel, ArrayList<Unit> entity){

		panel.remove(btnBuilding);
		panel.remove(btnSpawnSoldir);

		
		// Building-Button
		btnForward = new JButton("Forward");
		btnCreator.createOne(btnForward, 20, 600, 60, 60, 87);
		btnForward.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {

			}
		});
		panel.add(btnForward);
		
		// Building-Button
		btnBackward = new JButton("Backward");
		btnCreator.createOne(btnBackward, 90, 600, 60, 60, 87);
		btnBackward.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {

			}
		});
		panel.add(btnBackward);	
		panel.repaint();
	}
	
	// Änder die Zustände einer ganzen Gruppe
	public void GroupEntity(ArrayList<Unit> entity, Panel panel){
		
	}
	
	public void Barracks(){
		
	}
	
	public String getEntityAction(String EntityName){
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
		}

		return "null";
		
	}	
	
	public String cutHTMLout(String html){
		String ButtonName = html.substring(6);
		String[] parts = ButtonName.split("<");
		ButtonName = parts[0];
		return ButtonName;
	}
	
	
	
	
	
	
}
