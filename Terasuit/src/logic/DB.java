package logic;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

	private Connection connection;
	private Statement statement;

	public void loadDatabase() {
		try {
			connection = DriverManager
					.getConnection("jdbc:sqlite:"
							+ "H\\:\\Eigene Dateien\\Git\\Terasuit\\Terasuit\\DB\\Data.db3");
			statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Data (Name TEXT NOT NULL, Password TEXT NOT NULL, Email TEXT NOT NULL, Usermode TEXT NOT NULL)");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Stats (User TEXT NOT NULL, Wins INT NOT NULL, Loses INT NOT NULL, Unitkills INT NOT NULL, Buildingkills INT NOT NULL)");
		} catch (SQLException e) {
			try {
				connection = DriverManager.getConnection("jdbc:sqlite:"
						+ new File("DB\\Data.db3").getAbsolutePath());
				statement = connection.createStatement();
				statement.executeUpdate("CREATE TABLE IF NOT EXISTS Data (Name TEXT DATE NOT NULL, Password TEXT NOT NULL, Email TEXT NOT NULL, Usermode TEXT NOT NULL)");
				statement.executeUpdate("CREATE TABLE IF NOT EXISTS Stats (User TEXT NOT NULL, Wins INT NOT NULL, Loses INT NOT NULL, Unitkills INT NOT NULL, Buildingkills INT NOT NULL)");
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		}
	}

	public DB() {
		loadDatabase();
		addUser("Frank","123","asd","admin");
		updateStat("Frank","Wins",1);
		System.out.println(getStat("Frank","Wins"));
	}

	public void updateStat(String User,String Stat, int Number){
		int newStat = getStat(User,Stat)+Number;
		try {
			statement.executeUpdate("UPDATE Stats SET " + Stat+ " = '" + newStat + "' WHERE User"+ " = '" + User +"';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getStat(String User, String Stat){
		try {
			ResultSet resultSet = statement.executeQuery("SELECT " + Stat + " FROM Stats WHERE User = '"+ User + "';");
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public void addUser(String Name, String Password, String Email, String Usermode) {
		try {
			statement.executeUpdate("INSERT INTO Data VALUES ('" + Name + "','" + Password + "','" + Email + "','" + Usermode + "');");
			statement.executeUpdate("INSERT INTO Stats VALUES ('" + Name + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "');");
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

	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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