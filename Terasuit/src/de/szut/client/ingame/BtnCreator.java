package de.szut.client.ingame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class BtnCreator {

	/**
	 * Erstellt aus angegebenen Werten Buttons
	 * 
	 * @param defaultButton
	 *            Button der bearbeitet werden soll
	 * @param x
	 *            neue X-Position
	 * @param y
	 *            neue Y-Position
	 * @param breite
	 *            neue Breite des Buttons
	 * @param höhe
	 *            neue Höhe des Buttons
	 * @param ColorNum
	 *            neue Helligkeit des Buttons
	 */
	public void createOne(JButton defaultButton, int x, int y, int breite,
			int höhe, int ColorNum) {
		defaultButton.setBounds(x, y, breite, höhe);
		defaultButton.setBackground(new Color(ColorNum, ColorNum, ColorNum));
		defaultButton.setFont(new Font("Arial", Font.BOLD, 10));
	}
}
