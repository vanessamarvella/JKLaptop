package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import connect.Connect;

public class Cart {

	private String userID;
	private String productID;
	private int qty;
	private static Connect conn = Connect.getConnection();
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}

	public boolean insert() {
		String query = "INSERT INTO cart VALUES(?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userID);
			ps.setString(2, productID);
			ps.setInt(3, qty);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.err.println("Error insert data to Cart");
			return false;
		}
	}
	
	public boolean delete() {
		String query = "DELETE FROM cart WHERE UserID = ? AND ProductID = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userID);
			ps.setString(2, productID);
			ps.execute();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error delete data in Cart");
		
			return false;
		}
	}
	
}
