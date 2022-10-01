package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connect.Connect;

public class User {
	private String id;
	private String username;
	private String email;
	private String password;
	private String gender;
	private String address;
	private String role;
	
	private static Connect con = Connect.getConnection();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean insert() {
		String query = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, username);
			ps.setString(3, email);
			ps.setString(4, password);
			ps.setString(5, gender);
			ps.setString(6, address);
			ps.setString(7, role);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error to insert User!");
			return false;
		}
		
	}
	
	public static User get(String username, String password) {
		User user = null;
		String query = "SELECT * FROM user WHERE UserName ='" + username + "'AND UserPassword = '" + password + "'";
		try {
			ResultSet rs = con.execQuery(query);
			while(rs.next()) {
				user = new User();
				user.setId(rs.getString("UserID"));
				user.setUsername(rs.getString("UserName"));
				user.setEmail(rs.getString("UserEmail"));
				user.setPassword(rs.getString("UserPassword"));
				user.setGender(rs.getString("UserGender"));
				user.setAddress(rs.getString("UserAddress"));
				user.setRole(rs.getString("UserRole"));
			}
			return user;
		} catch (SQLException e) {
			System.err.println("Error to get user!");
			return user;
		}
	}
}
