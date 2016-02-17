package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import server.DB;
/**
 * 
 * @author Rogge
 *
 */
public class DBtests {
	
	private DB db;
	
	/**
	 * Öffnet die Datenbank verbindung und erstellt einen User
	 */
	public DBtests(){
		db = new DB();
		db.addUser("Frank", "123", "test@test", "1");
	}
	/**
	 * Löscht den User "Frank"
	 */
	public void del(){
		db.delUser("Frank");
	}

	/**
	 * Testet ob der gewünschte Nutzer in die Datenbank eingetragen wurde
	 */
	@Test
	public void insert_test() {
		assertEquals(true, db.search("Frank"));
		del();
		db.closeConnection();
	}
	/**
	 * Testet ob das richtige Passwort des Nutzers zurückgegeben wurde
	 */
	@Test
	public void get_test() {
		assertEquals("123", db.getUser("Frank"));
		del();
		db.closeConnection();
	}
	
	/**
	 * Testet ob die Stats des Nutzers richtig abgeändert werden
	 */
	@Test
	public void updatestats_test() {
		int i = db.getStat("Frank", "Wins");
		db.updateStat("Frank", "Wins", 1);
		assertEquals(i+1, db.getStat("Frank", "Wins"));
		del();
		db.closeConnection();
	}
	
	/**
	 * Testet ob der Nutzer nach dem Löschen noch in der Datenbank vorhanden ist
	 */
	@Test
	public void del_test() {
		del();
		assertEquals(false, db.search("Frank"));
		db.closeConnection();
	}
	

}
