package de.szut.server.logic;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Jan-Philipp
 *
 */
public class DB {

	private Connection connection;
	private Statement statement;

	/**
	 * Lädt die Datenbank und erstellt eine neue fals keine existiert
	 */
	public void loadDatabase() {
		try {
			connection = DriverManager
					.getConnection("jdbc:sqlite:"
							+ "H\\:\\Eigene Dateien\\Git\\Terasuit\\Terasuit\\DB\\Data.db3");
			statement = connection.createStatement();
			statement
					.executeUpdate("CREATE TABLE IF NOT EXISTS Data (Name TEXT NOT NULL, Password TEXT NOT NULL, Email TEXT NOT NULL, Usermode TEXT NOT NULL)");
			statement
					.executeUpdate("CREATE TABLE IF NOT EXISTS Stats (User TEXT NOT NULL, Wins INT NOT NULL, Loses INT NOT NULL, Unitkills INT NOT NULL, Buildingkills INT NOT NULL)");
		} catch (SQLException e) {
			try {
				connection = DriverManager.getConnection("jdbc:sqlite:"
						+ new File("DB\\Data.db3").getAbsolutePath());
				statement = connection.createStatement();
				statement
						.executeUpdate("CREATE TABLE IF NOT EXISTS Data (Name TEXT DATE NOT NULL, Password TEXT NOT NULL, Email TEXT NOT NULL, Usermode TEXT NOT NULL)");
				statement
						.executeUpdate("CREATE TABLE IF NOT EXISTS Stats (User TEXT NOT NULL, Wins INT NOT NULL, Loses INT NOT NULL, Unitkills INT NOT NULL, Buildingkills INT NOT NULL)");
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
	}

	public DB() {
		loadDatabase();
	}

	/**
	 * Erweitert die Satistik um ein neue Daten
	 * 
	 * @param User
	 *            : Nutzer der neue Statwerte erhält
	 * @param Stat
	 *            : Statistik Typ der upgegraded werden soll
	 * @param Number
	 *            : Wert um den die Satistik erweitert werden soll
	 */
	public void updateStat(String User, String Stat, int Number) {
		int newStat = getStat(User, Stat) + Number;
		try {
			statement.executeUpdate("UPDATE Stats SET " + Stat + " = '"
					+ newStat + "' WHERE User" + " = '" + User + "';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gibt den Wert eines Eintrages zurück
	 * 
	 * @param User
	 *            : Nutzer der geladen werden soll
	 * @param Stat
	 *            : Name des Eintrages der geladen werden soll
	 * @return
	 */
	public int getStat(String User, String Stat) {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT " + Stat
					+ " FROM Stats WHERE User = '" + User + "';");
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void addUser(String Name, String Password, String Email,
			String Usermode) {
		try {
			statement.executeUpdate("INSERT INTO Data VALUES ('" + Name + "','"
					+ Password + "','" + Email + "','" + Usermode + "');");
			statement.executeUpdate("INSERT INTO Stats VALUES ('" + Name
					+ "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getUser(String name) {
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT Password FROM Data WHERE Name = '"
							+ name + "';");
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delUser(String name){
		try {
			statement.executeUpdate("DELETE FROM Data WHERE Name = '"+name+"';");
			statement.executeUpdate("DELETE FROM Stats WHERE User = '"+name+"';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Schließt die verbindung zur Datenbank
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gibt zurück ob ein Nutzer in der Datenbank ezistiert
	 * 
	 * @param name
	 *            : Name des Nutzers der geprüft werden soll
	 * @return: boolean ob er existiert
	 */
	public boolean search(String name) {
		try {
			ResultSet resultSet = statement
					.executeQuery("SELECT Name FROM Data WHERE Name = '" + name
							+ "';");
			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}