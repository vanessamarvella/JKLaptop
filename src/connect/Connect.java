package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public final class Connect {
	private final String USERNAME = "root";
	private final String PASS = "";
	private final String DB = "jklaptop";
	private final String URL = "jdbc:mysql://localhost:3306/" + DB;
	private final String JDBC = "com.mysql.cj.jdbc.Driver";
	
	public ResultSet rs; 
	public ResultSetMetaData rsm;
	public PreparedStatement ps;
	private Statement stmt;
	private Connection con;
	private static Connect connect;
	
	
	public Connect() {
		try {
			Class.forName(JDBC);
			con = DriverManager.getConnection(URL, USERNAME, PASS);
			stmt = con.createStatement();
			
			System.out.println("Database connection success!");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to connect the database, system is terminated.");
			System.exit(0);
		}
	}

	 public static synchronized Connect getConnection() {
		 return connect = (connect == null) ? new Connect() : connect;
	 }
	
	public ResultSet execQuery(String query) {
		try {
			rs = stmt.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	 public PreparedStatement prepareStatement(String query) {
	  	PreparedStatement ps = null;
	   try {
		ps = con.prepareStatement(query);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return ps;
	}
	

}
