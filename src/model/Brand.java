package model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import connect.Connect;

public class Brand {

	private String brandID;
	private String brandName;
	
	private static Connect con = Connect.getConnection();
	
	public String getBrandID(String brandID) {
		return brandID;
	}

	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public boolean insert() {
		String query = "INSERT INTO brand VALUES (?, ?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, brandID);
			ps.setString(2, brandName);
			ps.execute();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error to add Brand!");
			
			return false;
		}
		
	}
		
	public boolean update() {
		String query = "UPDATE brand SET BrandName = ? WHERE BrandID = ?";

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, brandName);
			ps.setString(2, brandID);
			ps.execute();
			
			return true;
		} catch (SQLException e) {
			System.err.println("Error to update Brand name!");
			return false;
		}
	}
	
	public boolean delete() {
		String query = "DELETE FROM brand WHERE BrandID = ?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, brandID);
			ps.execute();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error to delete Brand");
		
			return false;
		}
	}

}
