package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import connect.Connect;

public class DetailTransaction {
	private String transactionID;
	private String productID;
	private int qty;
	private static Connect conn = Connect.getConnection();
	
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
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
		String query = "INSERT INTO detailtransaction VALUES(?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, transactionID);
			ps.setString(2, productID);
			ps.setInt(3, qty);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.err.println("Error insert data to Detail Transaction");
			return false;
		}
	}
	
}
