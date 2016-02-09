package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.DB;

public class DBtests {
	
	private DB db;
	
	public DBtests(){
		db = new DB();
		db.addUser("Frank", "123", "test@test", "1");
	}
	public void del(){
		db.delUser("Frank");
	}

	@Test
	public void insert_test() {
		assertEquals(true, db.search("Frank"));
		del();
		db.closeConnection();
	}
	
	@Test
	public void get_test() {
		assertEquals("123", db.getUser("Frank"));
		del();
		db.closeConnection();
	}
	
	@Test
	public void updatestats_test() {
		int i = db.getStat("Frank", "Wins");
		db.updateStat("Frank", "Wins", 1);
		assertEquals(i+1, db.getStat("Frank", "Wins"));
		del();
		db.closeConnection();
	}
	
	@Test
	public void del_test() {
		del();
		assertEquals(false, db.search("Frank"));
		db.closeConnection();
	}
	

}
