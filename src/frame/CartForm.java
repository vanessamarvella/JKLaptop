package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import connect.Connect;
import model.Cart;
import model.DetailTransaction;
import model.HeaderTransaction;

public class CartForm extends JInternalFrame implements MouseListener{
	
	private JPanel mainPanel, centerPanel, cartDescPanel, tablePanel, buttonPanel;
	private JLabel titleCartForm, detailTxt, userIDTxt, userIDFill, userNameTxt, userNameFill, dateTxt, dateFill, totalPriceTxt, totalPriceFill;
	private JTable cartDetailTable;
	private JScrollPane cartTableScroll;
	private JButton checkOutBtn;
	private DefaultTableModel dtm;
	private Vector<Object> cartTableContent;
	private Connect conn = Connect.getConnection();
	private String userID;
	private ResultSet rs;
	private int price = 0, qty = 0;
	private String id;
	
	public CartForm(Connect c, String userID) {
		c = conn;
		this.userID = userID;

		init();
		
		// inisialisasi
		setSize(700, 600);
		setTitle("Cart");
		setClosable(true);
		setResizable(false);
		setMaximizable(true);
	}

	private void init() {
		// Main Panel
		mainPanel = new JPanel(new BorderLayout(5,2));
		
		// Title
		titleCartForm = new JLabel("Cart", JLabel.CENTER);
		titleCartForm.setFont(new Font("Arial", Font.BOLD, 16));
		
		
		// Center Panel
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
		
		// Cart Panel
		cartDescPanel = new JPanel(new GridLayout(2, 4));
		cartDescPanel.setPreferredSize(new Dimension(700,300));
		
		// User
		userIDTxt = new JLabel("User ID : ");
		userIDTxt.setFont(new Font("Arial", Font.BOLD, 12));
		
		ResultSet rs;
		String query = "SELECT UserName FROM user WHERE UserID = '" + userID + "'";

		String name = null;
		try {
			rs = conn.execQuery(query);
			rs.next();
			name = rs.getString("UserName");
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Error to get UserName");
		}
		
		userIDFill = new JLabel(userID);
		userIDFill.setFont(new Font("Arial", Font.BOLD, 12));
		
		userNameTxt = new JLabel("Username : ");
		userNameTxt.setFont(new Font("Arial", Font.BOLD, 12));
		
		userNameFill = new JLabel(name);
		userNameFill.setFont(new Font("Arial", Font.BOLD, 12));
		
		// Date
		dateTxt = new JLabel("Date : ");
		dateTxt.setFont(new Font("Arial", Font.BOLD, 12));
		
		dateFill = new JLabel(getCurrentDate());
		dateFill.setFont(new Font("Arial", Font.BOLD, 12));
		
		// Total Price
		totalPriceTxt = new JLabel("Total Price : ");
		totalPriceTxt.setFont(new Font("Arial", Font.BOLD, 12));
		
		String queryPIQ = "SELECT c.ProductID, p.ProductPrice, c.Qty FROM cart c JOIN product p ON p.ProductID = c.ProductID JOIN user u ON u.UserID = c.UserID WHERE c.UserID = '" + userID +"'";

		try {
			rs = conn.execQuery(queryPIQ);
			while(rs.next()) {
				id = rs.getString("ProductID");
				price = rs.getInt("ProductPrice");
				qty = rs.getInt("Qty");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Error to get ID, Price and Qty");
		}
		
		int total = price*qty;
		
		totalPriceFill = new JLabel(total + "");
		totalPriceFill.setFont(new Font("Arial", Font.BOLD, 12));
		
		cartDescPanel.add(userIDTxt);
		cartDescPanel.add(userIDFill);
		cartDescPanel.add(userNameTxt);
		cartDescPanel.add(userNameFill);
		cartDescPanel.add(dateTxt);
		cartDescPanel.add(dateFill);
		cartDescPanel.add(totalPriceTxt);
		cartDescPanel.add(totalPriceFill);
		
		// Table Panel
		tablePanel = new JPanel(new BorderLayout());
		
		// Table title
		detailTxt = new JLabel("Detail", JLabel.CENTER);
		detailTxt.setFont(new Font("Arial", Font.BOLD, 16));
		
		// Table
		cartDetailTable = new JTable(dtm);
		cartDetailTable.setFont(new Font("Arial", Font.PLAIN, 12));
		cartDetailTable.setRowHeight(15);
		
		fetchTableData();
		
		cartTableScroll = new JScrollPane(cartDetailTable);
		cartTableScroll.setPreferredSize(new Dimension(650, 300));
		
		tablePanel.add(detailTxt, BorderLayout.NORTH);
		tablePanel.add(cartTableScroll, BorderLayout.CENTER);
		
		
		// Button
		checkOutBtn = new JButton("Check Out");
		checkOutBtn.addMouseListener(this);
		
		// Button Panel
		buttonPanel = new JPanel();
		
		buttonPanel.add(checkOutBtn);
		
		// Add to Panel
		mainPanel.add(titleCartForm, BorderLayout.NORTH);
		centerPanel.add(cartDescPanel, BorderLayout.NORTH);
		centerPanel.add(tablePanel, BorderLayout.CENTER);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		
		add(mainPanel);
	}
	
	public void fetchTableData() {
		String query = "SELECT c.ProductID, ProductName, ProductPrice, Qty FROM cart c JOIN product p ON p.ProductID = c.ProductID JOIN user u ON u.UserID = c.UserID WHERE c.UserID = '" + userID +"'";
		conn.rs = conn.execQuery(query);
		
		String header[] = {
				"ProductID", "ProductName", "ProductPrice", "Qty"
		};
		
		dtm = new DefaultTableModel(header, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		try {
			while(conn.rs.next()) {
				cartTableContent = new Vector<Object>();
				
				for (int i = 1; i < conn.rsm.getColumnCount() + 1; i++) {
					cartTableContent.add(conn.rs.getObject(i) + "");
				}
				
				dtm.addRow(cartTableContent);
			}
			cartDetailTable.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getCurrentDate() {
		return (java.time.LocalDate.now().toString());  
	}

	
	private boolean insertToHeaderTrans(String transactionID, String uID, String date) {
		HeaderTransaction ht = new HeaderTransaction();
		
		ht.setTransactionID(transactionID);
		ht.setUserID(uID);
		ht.setDate(date);
		return ht.insert();
	}
	

	private boolean insertToDetailTrans(String transactionID, String productID, int qty) {
		DetailTransaction dt = new DetailTransaction();
		
		dt.setTransactionID(transactionID);
		dt.setProductID(productID);
		dt.setQty(qty);
		return dt.insert();
	}
	
	public String generateID() {
		String generateID = "TR";
		boolean check;
		
		do {
			for (int i = 0 ; i < 3; i++) {
				int rand = (int)(Math.random() * 10);
				generateID += rand;
			}
			check = validateTransactionID(generateID);
		} while(!check);
		
		return generateID;
	}
	
	public boolean validateTransactionID(String generateID) {
		String queryForCheck = "SELECT TransactionID FROM headertransaction";
		
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
	
	private boolean deleteCart(String userID, String productID) {
		Cart c = new Cart();
		
		c.setUserID(userID);
		c.setProductID(productID);
		return c.delete();
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == checkOutBtn) {
			String transID = generateID();
			String date = dateFill.getText();
			
			insertToHeaderTrans(transID, userID, date);
			insertToDetailTrans(transID, id, qty);
			
			JOptionPane.showMessageDialog(this, "Check Out Success", "Message", JOptionPane.INFORMATION_MESSAGE);
			deleteCart(userID, id);
			this.dispose();
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
