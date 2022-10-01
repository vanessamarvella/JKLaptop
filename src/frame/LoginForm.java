package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import connect.Connect;
import model.User;

public class LoginForm extends JFrame implements ActionListener{
	private JPanel mainPanel, topPanel, centerPanel, bottomPanel, usernamePanel, passwordPanel;
	private JLabel loginTxt, usernameTxt, passTxt;
	private JTextField usernameField;
	private JPasswordField passField;
	private JButton registBtn, submitBtn;
	private Connect con = Connect.getConnection();
	
	public void init() {
		// Main Panel
		mainPanel = new JPanel(new BorderLayout());
		
		
		// Top Panel 
		topPanel = new JPanel();
		
		// Login Form Title
		loginTxt = new JLabel("Login");
		loginTxt.setFont(new Font("Arial", Font.BOLD, 16));
		
		topPanel.add(loginTxt);
		
		// Center Panel
		centerPanel = new JPanel(new GridLayout(2,2));
		
		// Username
		usernameTxt = new JLabel("Username");
		usernameTxt.setFont(new Font("Arial", Font.BOLD, 12));
		usernameTxt.setBorder(new EmptyBorder(10,10,10,10));
		usernameTxt.setVerticalAlignment(JLabel.TOP);
		
		usernameField = new JTextField();
		usernameField.setPreferredSize(new Dimension(175,25));
		
		usernamePanel = new JPanel();
		usernamePanel.add(usernameField);
		
		centerPanel.add(usernameTxt);
		centerPanel.add(usernamePanel);
		
		// Password
		passTxt = new JLabel("Password");
		passTxt.setFont(new Font("Arial", Font.BOLD, 12));
		passTxt.setBorder(new EmptyBorder(10,10,10,10));
		passTxt.setVerticalAlignment(JLabel.TOP);
		
		passField = new JPasswordField();
		passField.setPreferredSize(new Dimension(175,25));
		
		passwordPanel = new JPanel();
		passwordPanel.add(passField);
		
		centerPanel.add(passTxt);
		centerPanel.add(passwordPanel);
		
		
		// Bottom Panel
		bottomPanel = new JPanel(new FlowLayout(5, 75, 5));
		
		// Register Button
		registBtn = new JButton("Register");
		registBtn.addActionListener(this);
		
		// Submit Button
		submitBtn = new JButton("Submit");
		submitBtn.addActionListener(this);
		
		bottomPanel.add(registBtn);
		bottomPanel.add(submitBtn);
		
		
		
		// Add to Panel
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		this.add(mainPanel);
	}
	
	public LoginForm() {
		// inisialisasi frame
		init();
		setLocationRelativeTo(null);
		setSize(400, 170);
		setTitle("JKLaptop");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private User validateLogin(String username, String password) {
		ResultSet rs;
		User user = User.get(username, password);
		int role = -1;
		
		String query = "SELECT UserID FROM user WHERE UserName = '" + username + "'AND UserPassword = '" + password + "'";

		String userID = null;
		try {
			rs = con.execQuery(query);
			rs.next();
			userID = rs.getString("UserID");
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Error to get User ID");
		}
		
		if(user != null) {
			if(user.getRole().equalsIgnoreCase("Admin")) {
				role = 1;
				new MainForm(userID, role);
			}else if (user.getRole().equalsIgnoreCase("Member")){
				role = 0;
				new MainForm(userID, role);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Inputted username and password is invalid", "Message", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == submitBtn) {
			if (usernameField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Username Field must be filled", "Message", JOptionPane.ERROR_MESSAGE);
			} else if(passField.getPassword().length == 0) {
				JOptionPane.showMessageDialog(this, "Password Field must be filled", "Message", JOptionPane.ERROR_MESSAGE);
			} else {
				String username = usernameField.getText();
				String password = passField.getText();
				if(validateLogin(username, password) != null){
					this.dispose();
				}
			}
		} else if (arg0.getSource() == registBtn) {
			this.dispose();
			new RegisterForm();
		}
	}
}
