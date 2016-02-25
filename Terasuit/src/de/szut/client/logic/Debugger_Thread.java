package de.szut.client.logic;

import java.util.ArrayList;

import javax.swing.JLabel;

import de.szut.client.ingame.Unit;


public class Debugger_Thread extends Thread {
	
	Unit unit = new Unit();
	ArrayList<Unit> entity = new ArrayList<Unit>();
	ArrayList<Unit> entity2 = new ArrayList<Unit>();
	JLabel label = new JLabel("");
	int default_spawn_left_X = 300;
	int default_spawn_left_Y = 300;
	int n = 1;
	


	public ArrayList<Unit> getEntity() {
		return entity;
	}

	public void updatedEntitylist(ArrayList<Unit> entity) {
		this.entity = entity;
	}
	
	
	
	// Update (Tick) Thread
	public void run() {
		while (true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
	public void startRound(){
		//Loader  
		//.changeTo(loader.Mainpage);
	}
	
	// UpdateEntity
	public void UpdateEntity(ArrayList<Unit> ClientList){
		
	}
	
	// Einheit hinzufügen
	public void addEntity(String Entitytype, int membership){
		unit = new Unit();
		unit.setEntityNummer(n);
		
		int randValue1 = random(50);
		int randValue2 = random(200);
		
		unit.setEntityPositionX(default_spawn_left_X+randValue1);
		unit.setEntityPositionY(default_spawn_left_Y+randValue2);
		unit.setEntityname(Entitytype);
		unit.setEntitymembership(membership);
		unit.setNewEntity(true);
		entity2 = getEntity();
		entity2.add(unit);
		updatedEntitylist(entity2);
		n++;
	}
	
	public int random(int zahl){
		int rand = (int) (Math.random()*zahl)+1;
		return rand;
	}
    
}

