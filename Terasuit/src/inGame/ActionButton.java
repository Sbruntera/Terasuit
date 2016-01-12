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
	
	ArrayList<JButton> jButton = new ArrayList<JButton>();
	JButton btn;
	private static String wrapLines(String s) {
		return String.format("<html>%s</html>", s);
	}
	
	// Gebäudeoptionen
	public void createUserOptions(Panel panel, ArrayList<Buildings> BuildingsEntity, int i, Loader load, Funktions func){
		String [] noName  = BuildingsEntity.get(i).getSpwanableEntity();
		// Generiert alle möglichen Gebäudeoptionen
		for (int n = 0; n != noName.length; n++){
			System.out.println(noName[n]);
			btn = new JButton(wrapLines(noName[n]));
			btnCreator.createOne(btn, 200+(n*62), 600, 60, 60, 87);
			String type = getEntityAction(noName[n]);

			btn.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent arg0) {
					if (type.equals("Building")){
						System.out.println("Ein Gebäude wurde ausgewählt!");
					}else if (type.equals("Unit")){
						System.out.println("Ein Einheit wurde ausgewählt!");
						int number = (int) (Math.random()*4)+1;
						System.out.println(((JButton)arg0.getSource()).getText());
						String lol = "Unit/" + cutHTMLout(((JButton)arg0.getSource()).getText())+ ".png";
						System.out.println(lol);
						func.createEntity(panel, lol, number);
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
			panel.add(btn);
		}
		
		JLabel Description = new JLabel("");
		Description.setText(wrapLines(BuildingsEntity.get(i).getDescription()));
		Description.setForeground(Color.BLACK);
		Description.setBounds(20, 500, 180, 300);
		panel.add(Description);
		
		JLabel BuildingNameLbl = new JLabel("");
		BuildingNameLbl.setText(wrapLines(BuildingsEntity.get(i).getName()));
		BuildingNameLbl.setForeground(Color.BLACK);
		BuildingNameLbl.setFont(new Font("Arial", Font.PLAIN, 19));
		BuildingNameLbl.setBounds(20, 560, 180, 100);
		panel.add(BuildingNameLbl);

		panel.repaint();
	}
	
	public void deselectOptions(Panel panel){
		for (int i = jButton.size(); i != 0; i--){
			panel.remove(jButton.get(i));
			jButton.remove(i);
		}
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
		System.out.println(entity.get(i).getEntityNummer());
		System.out.println(entity.get(i).getEntityname());
	}
	
	// Änder die Zustände einer ganzen Gruppe
	public void GroupEntity(ArrayList<Unit> entity, Panel panel){
		
	}
	
	public void Barracks(){
		
	}
	
	public String getEntityAction(String EntityName){
		File file;
		
		file = new File("Buildings/" + EntityName + ".png");
		System.out.println(file.getName());
		if (file.exists()) {
			return "Building";
		}
		file = new File("Unit/" + EntityName + ".png");
		if (file.exists()) {
			return "Unit";
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
		ButtonName = parts[0]; // 004
		return ButtonName;
	}
	
	
	
	
	
	
}
