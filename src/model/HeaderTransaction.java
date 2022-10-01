package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import connect.Connect;

public class HeaderTransaction {
	private String transactionID;
	private String userID;
	private String date;
	private static Connect conn = Connect.getConnection();
	
	
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public boolean insert() {
		String query = "INSERT INTO headertransaction VALUES(?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, transactionID);
			ps.setString(2, userID);
			ps.setString(3, date);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.err.println("Error insert data to Header Transaction");
			return false;
		}
	}

}
