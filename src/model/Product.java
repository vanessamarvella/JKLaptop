package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import connect.Connect;

public class Product {
	private String productID;
	private String brandID;
	private String productName;
	private int productPrice;
	private int productStock;
	private int productRating;
	private static Connect conn = Connect.getConnection();
	
	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getBrandID() {
		return brandID;
	}

	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductStock() {
		return productStock;
	}

	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}

	public int getProductRating() {
		return productRating;
	}

	public void setProductRating(int productRating) {
		this.productRating = productRating;
	}

	public boolean insert() {
		String query = "INSERT INTO product VALUES(?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, productID);
			ps.setString(2, brandID);
			ps.setString(3, productName);
			ps.setInt(4, productPrice);
			ps.setInt(5, productStock);
			ps.setInt(6, productRating);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.err.println("Error to insert Product");
			return false;
		}
	}
	
	public boolean update() {
		String query = "UPDATE product SET BrandID = ?, ProductName = ?, ProductPrice = ?, ProductRating = ?, ProductStock = ? WHERE ProductID = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, brandID);
			ps.setString(2, productName);
			ps.setInt(3, productPrice);
			ps.setInt(4, productRating);
			ps.setInt(5, productStock);
			ps.setString(6, productID);
			ps.execute();
			return true;
		} catch (SQLException e) {
			System.err.println("Error to update Product");
			return false;
		}
	}
	
	public boolean updateStock() {
		String query = "UPDATE product SET ProductStock = ? WHERE ProductID = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, productStock);
			ps.setString(2, productID);
			ps.execute();
			return true;
		} catch (Exception e) {
			System.err.println("Error to update Product stock");
			return false;
		}
	}
	
	public boolean delete() {
		String query = "DELETE FROM product WHERE ProductID = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, productID);
			ps.execute();
			return true;
		} catch (Exception e) {
			System.err.println("Error to delete Product");
			return false;
		}
	}
	
	

}
