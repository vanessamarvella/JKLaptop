package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import connect.Connect;
import model.User;

public class RegisterForm extends JFrame implements ActionListener{

	private JPanel mainPanel, centerPanel, bottomPanel, topPanel, genderPanel, usernamePanel, emailPanel, passwordPanel, addressPanel;
	private JLabel titleTxt, usernameTxt, emailTxt, passwordTxt, genderTxt, addressTxt;
	private JTextField usernameField, emailField;
	private JTextArea addressField;
	private JPasswordField passField;
	private JButton submitBtn, loginBtn, resetBtn;
	private JRadioButton maleBtn, femaleBtn;
	private ButtonGroup genderGroup;
	private ResultSet rs;
	private Connect conn = Connect.getConnection();
	
	public RegisterForm() {
		// Main Panel 
		mainPanel = new JPanel(new BorderLayout());
		
		// Top Panel
		topPanel = new JPanel();
		
		// Title
		titleTxt = new JLabel("JKLAPTOP");
		titleTxt.setFont(new Font("Arial", Font.BOLD, 16));
		
		topPanel.add(titleTxt);
		
		// Center Panel
		centerPanel = new JPanel(new GridLayout(5,2));
		
		// Username
		usernameTxt = new JLabel("Username");
		usernameTxt.setFont(new Font("Arial", Font.BOLD, 12));
		usernameTxt.setBorder(new EmptyBorder(10,10,10,10));
		
		usernameField = new JTextField();
		usernameField.setPreferredSize(new Dimension(230, 60));
		
		usernamePanel = new JPanel();
		usernamePanel.add(usernameField);
		
		centerPanel.add(usernameTxt);
		centerPanel.add(usernamePanel);
		
		// Email
		emailTxt = new JLabel("Email");
		emailTxt.setFont(new Font("Arial", Font.BOLD, 12));
		emailTxt.setBorder(new EmptyBorder(10,10,10,10));
		
		emailField = new JTextField();
		emailField.setPreferredSize(new Dimension(230, 60));
		
		emailPanel = new JPanel();
		emailPanel.add(emailField);
		
		centerPanel.add(emailTxt);
		centerPanel.add(emailPanel);
		
		// Password
		passwordTxt = new JLabel("Password");
		passwordTxt.setFont(new Font("Arial", Font.BOLD, 12));
		passwordTxt.setBorder(new EmptyBorder(10,10,10,10));
		
		passField = new JPasswordField();
		passField.setPreferredSize(new Dimension(230, 60));
		
		passwordPanel = new JPanel();
		passwordPanel.add(passField);
		
		centerPanel.add(passwordTxt);
		centerPanel.add(passwordPanel);
		
		// Gender
		genderTxt = new JLabel("Gender");
		genderTxt.setFont(new Font("Arial", Font.BOLD, 12));
		genderTxt.setBorder(new EmptyBorder(10,10,10,10));
		
		maleBtn = new JRadioButton("Male");
		femaleBtn = new JRadioButton("Female");

		
		// Gender Group
		genderGroup = new ButtonGroup();
		genderGroup.add(maleBtn);
		genderGroup.add(femaleBtn);
		
		// gender Panel
		genderPanel = new JPanel();
		genderPanel.add(maleBtn);
		genderPanel.add(femaleBtn);
		
		centerPanel.add(genderTxt);
		centerPanel.add(genderPanel);
		
		// Address
		addressTxt = new JLabel("Address");
		addressTxt.setFont(new Font("Arial", Font.BOLD, 12));
		addressTxt.setBorder(new EmptyBorder(10,10,10,10));
		
		addressField = new JTextArea(3,1);
		addressField.setPreferredSize(new Dimension(230, 60));
		addressField.setLineWrap(true);
		addressField.setWrapStyleWord(true);
		addressField.setBorder(BorderFactory.createLineBorder(Color.gray));

		addressPanel = new JPanel();
		addressPanel.add(addressField);
		
		centerPanel.add(addressTxt);
		centerPanel.add(addressPanel);
		
		// Bottom Panel
		bottomPanel = new JPanel(new FlowLayout(5, 70, 5));
		
		// Reset Button
		resetBtn = new JButton("Reset");
		resetBtn.addActionListener(this);
		
		// Login Button
		loginBtn = new JButton("Login");
		loginBtn.addActionListener(this);
		
		// Submit Button
		submitBtn = new JButton("Submit");
		submitBtn.addActionListener(this);
		
		bottomPanel.add(resetBtn);
		bottomPanel.add(loginBtn);
		bottomPanel.add(submitBtn);
		
		// Add to Panel
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		
		
		// inisialisasi frame
		setVisible(true);
		setLocationRelativeTo(null);
		setSize(500, 500);
		setTitle("JKLaptop");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static boolean isAlphaNumeric(String s) {
		int countLetter = 0, countDigit = 0;
		boolean status = false;
		
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(Character.isLetter(c)) {
				countLetter++;
			} else if (Character.isDigit(c)) {
				countDigit++;
			}
		}
		
		if (countDigit >= 1 && countLetter >= 1) {
			status = true;
		} else {
			status = false; 
		}
		
        return status;
    }
	
	public static boolean checkEmail(String s) {
		boolean status = false;
		int countTitik = 0, countAt = 0;
		
		for(int i = 0; i < s.length(); i++) {
			Character c = s.charAt(i);
			if (c.toString().contains("@")) {
				countAt++;
			} else if(c.toString().contains(".")) {
				countTitik++;
			}
			
			if (countAt == 1 && countTitik == 1) {
				status = true;
			} else {
				status = false;
			}
		}
		
		return status;
	}
	
	public String generateID() {
		String generateID = "US";
		boolean check;
		
		do {
			for (int i = 0 ; i < 3; i++) {
				int rand = (int)(Math.random() * 10);
				generateID += rand;
			}
			check = validateUserID(generateID);
		} while(!check);
		
		return generateID;
	}
	
	public boolean validateUserID(String generateID) {
		String queryForCheck = "SELECT UserID FROM user";
		
		rs = conn.execQuery(queryForCheck);
		
		try {
			if(!rs.next())
				
				return true;
			
			while (rs.next()) {
				String temp = rs.getObject(1).toString();
				
				if(temp.equals(generateID)) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	private boolean insertUser(String id, String username, String email, String password, String gender, String address, String role) {
		User user = new User();
		
		user.setId(id);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setGender(gender);
		user.setAddress(address);
		user.setRole(role);
		return user.insert();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitBtn) {
			if (usernameField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Username field must fill!", "Message", JOptionPane.ERROR_MESSAGE);
			} else if (usernameField.getText().length() < 5 || usernameField.getText().length() > 20) {
				JOptionPane.showMessageDialog(this, "Username length must between 5 and 20 characters", "Message", JOptionPane.ERROR_MESSAGE);
			} else if(emailField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Email field must fill!", "Message", JOptionPane.ERROR_MESSAGE);
			} else if(emailField.getText().contains("@.") ||emailField.getText().contains(".@") || emailField.getText().contains(".@.")) {
				JOptionPane.showMessageDialog(this, "Email character '@' must not be next to '.'", "Message", JOptionPane.ERROR_MESSAGE);
			} else if(emailField.getText().startsWith("@") ||emailField.getText().startsWith(".")  ) {
				JOptionPane.showMessageDialog(this, "Email input must not starts with '@' or '.'", "Message", JOptionPane.ERROR_MESSAGE);
			} else if(!emailField.getText().endsWith(".com")) {
				JOptionPane.showMessageDialog(this, "Email input must end with '.com'", "Message", JOptionPane.ERROR_MESSAGE);
			} else if(checkEmail(emailField.getText()) == false) {
				JOptionPane.showMessageDialog(this, "Email must not contains more than 1 '@' or '.'", "Message", JOptionPane.ERROR_MESSAGE);
			} else if (passField.getPassword().length == 0) {
				JOptionPane.showMessageDialog(this, "Password field must fill!", "Message", JOptionPane.ERROR_MESSAGE);
			} else if (isAlphaNumeric(new String(passField.getPassword())) == false) {
				System.out.println(new String(passField.getPassword()));
				JOptionPane.showMessageDialog(this, "Password must alphanumeric", "Message", JOptionPane.ERROR_MESSAGE);
			}  else if (!(maleBtn.isSelected() || femaleBtn.isSelected())) {
				JOptionPane.showMessageDialog(this, "One gender must be selected", "Message", JOptionPane.ERROR_MESSAGE);
			} else if(addressField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Address field must fill!", "Message", JOptionPane.ERROR_MESSAGE);
			} else if (!addressField.getText().endsWith("Street")){
				JOptionPane.showMessageDialog(this, "Address must ends with 'Street'", "Message", JOptionPane.ERROR_MESSAGE);
			} else {
				String selectedGender = "";
				
				if(maleBtn.isSelected()) {
					selectedGender = "Male";
				} else if(femaleBtn.isSelected()){
					selectedGender = "Female";
				}
				
				insertUser(generateID(), usernameField.getText(), emailField.getText(), passField.getText(), selectedGender, addressField.getText(), "Member");
				JOptionPane.showMessageDialog(this, "Register Success", "Message", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
				LoginForm lf = new LoginForm();
				lf.setVisible(true);
			}
		} else if (e.getSource() == loginBtn) {
			this.dispose();
			LoginForm lf = new LoginForm();
			lf.setVisible(true);
		} else if (e.getSource() == resetBtn) {
			usernameField.setText("");
			passField.setText("");
			emailField.setText("");
			addressField.setText("");
			genderGroup.clearSelection();
		}
	}
}
