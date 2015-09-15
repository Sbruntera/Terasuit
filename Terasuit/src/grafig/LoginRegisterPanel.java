package grafig;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class LoginRegisterPanel {
	
	JLabel label = new JLabel("54545454");

	public void popupLogin(Panel panel) {
		
		label.setIcon(new ImageIcon("Wallpaper/register.png"));
		label.setBounds(20, 39, 110, 114);
		panel.add(label);
		
		JButton btnBACK = new JButton("RETURN");
		btnBACK.setBounds(430, 695, 170, 60);//links / runter / breite / höhe
		btnBACK.setBackground(new Color(255,90,0));
		btnBACK.setFont(new Font("Arial", Font.BOLD, 24));
		btnBACK.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// Der Weg zurück
				//loader.switchPanel(loader.Lobbypage);
			}
		});
		panel.add(btnBACK);	

		
	}
	
	public void popupRegister(Panel panel) {
		
		label.setIcon(new ImageIcon("Wallpaper/register.png"));
		label.setBounds(20, 39, 110, 114);
		panel.add(label);
		
	}


}
