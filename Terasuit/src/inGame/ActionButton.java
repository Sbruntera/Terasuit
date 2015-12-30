package inGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
			btn.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent arg0) {
					System.out.println("Funktion wird aufgerufen!!");
					int number = (int) (Math.random()*4)+1;
					func.createEntity(panel, "Unit/test2.png", number);
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
	}
	
	// Änder die Zustände einer ganzen Gruppe
	public void GroupEntity(ArrayList<Unit> entity, Panel panel){
		
	}
	
	public void Barracks(){
		
	}
}
