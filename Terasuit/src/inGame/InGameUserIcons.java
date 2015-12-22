package inGame;

import grafig.Panel;

import java.util.ArrayList;

import javax.swing.JLabel;

public class InGameUserIcons {
	
	 ArrayList<JLabel> labels = new ArrayList<JLabel>();
	 
	 private String barracks = ""
	 
	 public void createUserIcons(ArrayList<Buildings> BuildingsEntity, int i){
		 
		 
		 
	 }
	 
	 public void destroyUserIcons(Panel panel){
		 for (int i = 0; i < labels.size(); i++){
			 panel.remove(labels.get(i));
		 }
		 panel.repaint();
	 }

	 JLabel holder;

	 for (int i = 0; i < order.size(); i++){
	        holder = new JLabel();
	        holder.setText(order.get(i))  // assuming order's objects are Strings, otherwise .toString() on there somewhere
	        labels.add(holder); //adds holder to the ArrayList of JLabels
	 }
	
	
	
	
	 
}
