package inGame;

import grafig.Panel;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class CreateUnit {
	
	private JLabel label[];

	// Produktion der Unit wird inizalisert
	// Cooldown der Unit wird abgewartet
	// Unit wird plaziert auf dem Feld
	// Unit wird eine Zorder festgelegt
	// Unit wird registriert
	
	public void createSoldir(Panel panel){
		// Back-Button
		label[1] = new JLabel("");
		label[1].setIcon(new ImageIcon("C:\\Users\\dqi12feldmann\\Desktop\\Soldat_Gelb_Rechts_2.png"));
		label[1].setForeground(Color.MAGENTA);
		label[1].setBackground(Color.red);

		label[1].setBounds(300 + (random(50)), 200 + (random(200)), 65, 142);
		panel.add(label[1]);
		panel.repaint();
		return;
	}
	
	public int random(int zahl){
		int rand = (int) (Math.random()*zahl)+1;
		return rand;
		
	}
	
}
