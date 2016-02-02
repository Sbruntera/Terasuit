package inGame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class BtnCreator {

	
	public void createOne(JButton btnBACK, int x, int y, int breite, int hoehe, int ColorNum){
		btnBACK.setBounds(x, y, breite, hoehe);//links / runter / breite / höhe
		btnBACK.setBackground(new Color(ColorNum,ColorNum,ColorNum));
		btnBACK.setFont(new Font("Arial", Font.BOLD, 10));

	}

}
