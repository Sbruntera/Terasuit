package grafig;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PopupServer {

	JLabel lbcreateServer = new JLabel("");

	JTextField lbpassword = new JTextField();
	JLabel lbusername = new JLabel();
	JTextField userField = new JTextField();
	JPasswordField passwortField = new JPasswordField();

	public void createPopup(Panel panel) {

		lbusername = new JLabel("Username:");
		lbusername.setBounds(800, 450, 200, 50);
		panel.add(lbusername);

		// lbpassword = new JLabel("Password:");
		lbpassword.setBounds(800, 490, 200, 50);
		panel.add(lbpassword);

		lbcreateServer = new JLabel("");
		lbcreateServer.setIcon(new ImageIcon("Wallpaper/kkk.png"));
		lbcreateServer.setBounds(300, 150, 500, 500); // links / runter /
														// wbreite / höhe
		panel.add(lbcreateServer);

		userField = new JTextField();
		userField.setBounds(800, 485, 200, 20);
		panel.add(userField);
		userField.setColumns(10);

		passwortField = new JPasswordField();
		passwortField.setBounds(800, 525, 200, 20);
		panel.add(passwortField);
		passwortField.setColumns(10);

		panel.repaint();

	}

	public void createdestroy(Panel panel) {
		panel.remove(lbcreateServer);
		panel.repaint();
	}
}
