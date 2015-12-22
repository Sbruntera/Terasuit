package logic;

import java.util.ArrayList;

import inGame.Unit;

public class ActionUnit {
	
	/*
	 * 
	 */
	Unit unit = new Unit();	
	ArrayList<Unit> UpdateList = new ArrayList<Unit>();
	
	
	public ArrayList<Unit> getUpdateList() {
		return UpdateList;
	}


	public void setUpdateList(ArrayList<Unit> updateList) {
		UpdateList = updateList;
	}


	public void updateEntityList(ArrayList<Unit> List){
		
		for (int i = List.size(); i != 0; i--){
			UpdateList.add(List.get(i));
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
