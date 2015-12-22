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
	
		
	private static String wrapLines(String s) {
		return String.format("<html>%s</html>", s);
	}
	
	public void Building(Panel panel, ArrayList<Buildings> BuildingsEntity, int i, Loader load, Funktions func){


		panel.remove(btnForward);
		panel.remove(btnBackward);

		 
		// Building-Button
		JButton btnBuilding = new JButton("Building");
		btnCreator.createOne(btnBuilding, 200, 600, 60, 60, 87);
		btnBuilding.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {

			}
		});
		panel.add(btnBuilding);	
		
		// Einheit wird Produziert
		JButton btnSpawnSoldir = new JButton("Soldat");
		btnCreator.createOne(btnSpawnSoldir, 270, 600, 60, 60, 87);
		btnSpawnSoldir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				func.createEntity(panel, "Unit/Soldat_Blau_Rechts2.png");
				panel.repaint();
			}
		});
		
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
		
		panel.add(btnSpawnSoldir);
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
