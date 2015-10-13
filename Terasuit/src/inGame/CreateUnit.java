package inGame;

import grafig.Panel;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CreateUnit {
	
	private JLabel label;

	// Produktion der Unit wird inizalisert
	// Cooldown der Unit wird abgewartet
	// Unit wird plaziert auf dem Feld
	// Unit wird eine Zorder festgelegt
	// Unit wird registriert
	
	public void createSoldir(Panel panel){
		// Back-Button
		label = new JLabel("");
		label.setIcon(new ImageIcon("Unit/Soldat_Blau_Links.png"));
		label.setForeground(Color.MAGENTA);
		label.setBackground(Color.red);

		label.setBounds(300 + (random(50)), 200 + (random(200)), 65, 142);
		panel.add(label);
		panel.repaint();
		return;
	}
	
	public int random(int zahl){
		int rand = (int) (Math.random()*zahl)+1;
		return rand;
		
	}
	
}
