package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import connect.Connect;
import model.Brand;
import model.User;

public class BrandForm extends JInternalFrame implements ActionListener, MouseListener{
	
	private JPanel mainPanel, bottomPanel, brandListPanel, managePanel, brandInputPanel, BtnPanel;
	private JLabel brandFormTitle, brandIDTxt, brandNameTxt, spaceTxt;
	private JTextField brandIDField, brandNameField;
	private JTable brandListTable;
	private JScrollPane brandTblScroll;
	private JButton insertBtn, updateBtn, deleteBtn, submitBtn, cancelBtn;
	private DefaultTableModel dtm;
	private Vector<Object> brandTableContent;
	private ResultSet rs;
	private Connect conn = Connect.getConnection();
	private String act;
	private boolean check = false;
	JDesktopPane desktopPane;
	User user;
	String userID;
	
	public BrandForm(JDesktopPane dp, Connect c, String userID) {
		init();
		
		// inisialisasi
		dp = desktopPane;
		c = conn;
		this.userID = userID;
		setSize(1000, 600);
		setTitle("Manage Brand");
		setResizable(false);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void fetchTableData() {
		String query = "SELECT * FROM brand";
		conn.rs = conn.execQuery(query);
		
		String header[] = {
				"BrandID", "BrandName"
		};
		
		dtm = new DefaultTableModel(header, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		try {
			while(conn.rs.next()) {
				brandTableContent = new Vector<Object>();
				
				for (int i = 1; i < conn.rsm.getColumnCount() + 1; i++) {
					brandTableContent.add(conn.rs.getObject(i) + "");
				}
				
				dtm.addRow(brandTableContent);
			}
			brandListTable.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		// Main Panel
		mainPanel = new JPanel(new GridLayout(2,1,0,10));
		mainPanel.setBorder(new EmptyBorder(5,5,5,5));
		
		// Brand List Panel
		brandListPanel = new JPanel(new BorderLayout());
		
		// Title
		brandFormTitle = new JLabel("Brand List", JLabel.CENTER);
		brandFormTitle.setFont(new Font("Arial", Font.BOLD, 16));
		
		brandListPanel.add(brandFormTitle, BorderLayout.NORTH);
		
		brandListTable = new JTable(dtm);
		brandListTable.addMouseListener(this);
	

		fetchTableData();
		
		// Table Scroll
		brandTblScroll = new JScrollPane(brandListTable);
		brandTblScroll.setPreferredSize(new Dimension(800, 150));
		
		brandListPanel.add(brandTblScroll, BorderLayout.CENTER);
		
		
		// Manage Panel
		managePanel = new JPanel(new BorderLayout(10,20));
		
		// Brand ID Input
		brandIDTxt = new JLabel("Brand ID");
		brandIDTxt.setFont(new Font("Arial", Font.BOLD, 12));
		
		brandIDField = new JTextField();
		brandIDField.setFont(new Font("Arial", Font.PLAIN, 12));
		
		// Brand Name Input
		brandNameTxt = new JLabel("Brand Name");
		brandNameTxt.setFont(new Font("Arial", Font.BOLD, 12));

		brandNameField = new JTextField();
		brandNameField.setFont(new Font("Arial", Font.PLAIN, 12));
		
		spaceTxt = new JLabel("");
		
		// Brand Input Panel
		brandInputPanel = new JPanel(new GridLayout(4,2,5,0));
		brandInputPanel.setPreferredSize(new Dimension(1035,100));
		
		brandInputPanel.add(brandIDTxt);
		brandInputPanel.add(brandIDField);
		brandInputPanel.add(brandNameTxt);
		brandInputPanel.add(brandNameField);
		brandInputPanel.add(spaceTxt);
		brandInputPanel.add(spaceTxt);
		brandInputPanel.add(spaceTxt);
		brandInputPanel.add(spaceTxt);
		
		// Brand to Panel
		managePanel.add(brandInputPanel, BorderLayout.CENTER);
		
		// Button
		insertBtn = new JButton("Insert");
		insertBtn.setPreferredSize(new Dimension(300,30));
		insertBtn.addActionListener(this);
		
		updateBtn = new JButton("Update");
		updateBtn.setPreferredSize(new Dimension(300,30));
		updateBtn.addMouseListener(this);
		
		deleteBtn = new JButton("Delete");
		deleteBtn.setPreferredSize(new Dimension(300,30));
		deleteBtn.addMouseListener(this);
		
		
		submitBtn = new JButton("Submit");
		submitBtn.setPreferredSize(new Dimension(300,30));
		submitBtn.addActionListener(this);
		submitBtn.setEnabled(false);
		cancelBtn = new JButton("Cancel");
		cancelBtn.setPreferredSize(new Dimension(300,30));
		cancelBtn.setEnabled(false);
		cancelBtn.addActionListener(this);
		
		// Button Panel
		BtnPanel = new JPanel(new GridLayout(5, 1, 40, 5));
		
	
		// Insert button to Panel
		BtnPanel.add(insertBtn);
		BtnPanel.add(updateBtn);
		BtnPanel.add(deleteBtn);
		BtnPanel.add(submitBtn);
		BtnPanel.add(cancelBtn);
		
		managePanel.add(BtnPanel, BorderLayout.EAST);
		
		
		// Bottom Panel
		bottomPanel = new JPanel(new BorderLayout(40,10));
		
		bottomPanel.add(managePanel, BorderLayout.WEST);
		
		mainPanel.add(brandListPanel);
		mainPanel.add(bottomPanel);
		
		this.add(mainPanel);
	}
	public void clear() {
		insertBtn.setEnabled(true);
		updateBtn.setEnabled(true);
		deleteBtn.setEnabled(true);
		submitBtn.setEnabled(false);
		cancelBtn.setEnabled(false);
		brandIDField.setEnabled(true);
		brandNameField.setEnabled(true);
		brandListTable.clearSelection();
		brandIDField.setText("");
		brandNameField.setText("");
	}

	public String generateID() {
		String generateID = "BD";
		boolean check;
		
		do {
			for (int i = 0 ; i < 3; i++) {
				int rand = (int)(Math.random() * 10);
				generateID += rand;
			}
			check = validateBrandID(generateID);
		} while(!check);
		
		return generateID;
	}
	
	public boolean validateBrandID(String generateID) {
		String queryForCheck = "SELECT BrandID FROM brand";
		
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
	
	private boolean insertBrand(String brandID, String brandName) {
		Brand br = new Brand();
		
		br.setBrandID(brandID);
		br.setBrandName(brandName);
		return br.insert();
	}
	
	private boolean updateBrand(String brandID, String brandName) {
		Brand br = new Brand();
		
		br.setBrandID(brandID);
		br.setBrandName(brandName);
		return br.update();
	}
	
	private boolean deleteBrand(String brandID) {
		Brand br = new Brand();
		
		br.setBrandID(brandID);
		return br.delete();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == insertBtn) {
			act = "insert";
			String id = generateID();
			
			brandIDField.setEnabled(false);
			brandIDField.setText(id);
			insertBtn.setEnabled(false);
			updateBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
				
			submitBtn.setEnabled(true);
			cancelBtn.setEnabled(true);
			
			
			
		} else if (e.getSource() == submitBtn) {
			if(act.equals("insert")) {
				if(brandNameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Brand Name field must fill!", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					insertBrand(brandIDField.getText(), brandNameField.getText());
					
					fetchTableData();
					
					JOptionPane.showMessageDialog(this, "Insert Success", "Message", JOptionPane.INFORMATION_MESSAGE);
					clear();
				}
			} else if (act.equals("update")) {
				if (check == false) {
					JOptionPane.showMessageDialog(this, "You must select the data on the table", "Message", JOptionPane.ERROR_MESSAGE);
				} else if(brandNameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Brand Name field must fill", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					updateBrand(brandIDField.getText(), brandNameField.getText());
					fetchTableData();
					JOptionPane.showMessageDialog(this, "Update Success", "Message", JOptionPane.INFORMATION_MESSAGE);
					clear();
				}
			} else if (act.equals("delete")) {
				if (check == false) {
					JOptionPane.showMessageDialog(this, "You must select the data on the table", "Message", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if(e.getSource() == cancelBtn) {
			clear();
		} 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == updateBtn) {
			act ="update";
			insertBtn.setEnabled(false);
			updateBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			submitBtn.setEnabled(true);
			cancelBtn.setEnabled(true);
			brandIDField.setEnabled(false);
			brandNameField.setEnabled(true);
		} else if (e.getSource() == deleteBtn){
			act = "delete";
			insertBtn.setEnabled(false);
			updateBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			submitBtn.setEnabled(true);
			cancelBtn.setEnabled(true);
			brandIDField.setEnabled(false);
			brandNameField.setEnabled(false);
		} else if(e.getSource() == brandListTable) {
			int row = brandListTable.getSelectedRow();
			
			String brandID = brandListTable.getValueAt(row, 0).toString();
			String brandName = brandListTable.getValueAt(row, 1).toString();
				
			if(act.equals("update")) {
				brandIDField.setText(brandID);
			} else if (act.equals("delete")){
				deleteBrand(brandID);
				fetchTableData();
				JOptionPane.showMessageDialog(this, "Delete Success", "Message", JOptionPane.INFORMATION_MESSAGE);
				clear();
			}
			String a = (String) brandListTable.getValueAt(0, 1);
			if(!(a.isEmpty())) {
				check = true;
			}

		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
