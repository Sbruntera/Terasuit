package inGame;

import grafig.Panel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CreateUnit {

	Unit unit = new Unit();
	JLabel label = new JLabel("");
	SelectedUnits select = new SelectedUnits();
	int default_spawn_left_X = 300;
	int default_spawn_left_Y = 300;

	public void createEntity(Panel panel, String Entitytype,
			ArrayList<Unit> entity) {
		unit = new Unit();
		unit.setEntityNummer(entity.size() + 1);

		int randValue1 = random(50);
		int randValue2 = random(200);

		unit.setEntityPositionX(default_spawn_left_X + randValue1);
		unit.setEntityPositionY(default_spawn_left_Y + randValue2);

		// ImageIcon pic = new MirrorImageIcon(Entitytype);
		// label.setIcon(pic);

		ImageIcon pic = new ImageIcon(Entitytype);
		label = new JLabel("");
		label.setIcon(pic);

		label.setBounds(default_spawn_left_X + (randValue1),
				default_spawn_left_Y + (randValue2), pic.getIconWidth(),
				pic.getIconHeight());
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent objUnit) {
				select.getUnit(entity, objUnit, panel);
			}
		});
		unit.setLabel(label);
		entity.add(unit);
		panel.add(label);
		panel.repaint();
		return;
	}

	public int random(int zahl) {
		int rand = (int) (Math.random() * zahl) + 1;
		return rand;
	}

}
