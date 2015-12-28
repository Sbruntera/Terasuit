package grafig;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import logic.Login_Client;

public class LoginRegisterPanel {
	JLabel registerPanel = new JLabel();
	JLabel loginPanel = new JLabel();
	JTextField userField = new JTextField();
	JPasswordField passwortField = new JPasswordField();
	JButton btnlogin = new JButton();
	JPasswordField passwort2Field = new JPasswordField();
	JTextField mailField = new JTextField();
	JLabel lbusername = new JLabel();
	JLabel lbpassword = new JLabel();
	JLabel lbpassword2 = new JLabel();
	JLabel lbmail = new JLabel();
	Login_Client loginClient = new Login_Client();

	public void popupLogin(Panel panel) {

		btnlogin = new JButton("Login");
		btnlogin.setBounds(800, 650, 200, 50);
		btnlogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Thread thread = new Thread(loginClient);
				loginClient.login(userField.getText(),
						passwortField.getPassword());
				Controller controller = new Controller(thread, 5000);
				Thread controlThread = new Thread(controller);
				thread.start();
				controlThread.start();
			}
		});
		panel.add(btnlogin);

		lbusername = new JLabel("Username:");
		lbusername.setBounds(800, 450, 200, 50);
		panel.add(lbusername);

		lbpassword = new JLabel("Password:");
		lbpassword.setBounds(800, 490, 200, 50);
		panel.add(lbpassword);

		createpopup(panel);

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

	public void popupRegister(Panel panel) {

		btnlogin = new JButton("Register");
		btnlogin.setBounds(800, 650, 200, 50);
		btnlogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				Thread thread = new Thread(loginClient);
				loginClient.register(userField.getText(),
						passwortField.getPassword(),
						passwort2Field.getPassword(), mailField.getText());
				thread.start();
			}
		});
		panel.add(btnlogin);

		lbusername = new JLabel("Username:");
		lbusername.setBounds(800, 450, 200, 50);
		panel.add(lbusername);

		lbpassword = new JLabel("Password:");
		lbpassword.setBounds(800, 490, 200, 50);
		panel.add(lbpassword);

		lbpassword2 = new JLabel("Password again:");
		lbpassword2.setBounds(800, 530, 200, 50);
		panel.add(lbpassword2);

		lbmail = new JLabel("E-Mail:");
		lbmail.setBounds(800, 570, 200, 50);
		panel.add(lbmail);

		createpopup(panel);

		userField = new JTextField();
		userField.setBounds(800, 485, 200, 20);
		panel.add(userField);
		userField.setColumns(10);

		passwortField = new JPasswordField();
		passwortField.setBounds(800, 525, 200, 20);
		panel.add(passwortField);
		passwortField.setColumns(10);

		passwort2Field = new JPasswordField();
		passwort2Field.setBounds(800, 565, 200, 20);
		panel.add(passwort2Field);
		passwort2Field.setColumns(10);

		mailField = new JTextField();
		mailField.setBounds(800, 605, 200, 20);
		panel.add(mailField);
		mailField.setColumns(10);

		panel.repaint();

	}

	private void createpopup(Panel panel) {
		registerPanel = new JLabel("");
		registerPanel.setIcon(new ImageIcon("Wallpaper/kkk.png"));
		registerPanel.setBounds(770, 335, 500, 500); // links / runter / wbreite
														// / höhe
		panel.add(registerPanel);
	}

	public void popupdestroy(Panel panel) {

		panel.remove(lbmail);
		panel.remove(lbpassword);
		panel.remove(lbpassword2);
		panel.remove(lbusername);
		panel.remove(passwort2Field);
		panel.remove(mailField);
		panel.remove(btnlogin);
		panel.remove(passwortField);
		panel.remove(userField);
		panel.remove(registerPanel);
		panel.repaint();
	}
}
