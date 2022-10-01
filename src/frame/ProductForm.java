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
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import connect.Connect;
import model.Product;
import model.User;

public class ProductForm extends JInternalFrame implements ActionListener, MouseListener{
	private JPanel mainPanel, bottomPanel, productListPanel, managePanel, productInputPanel, BtnPanel;
	private JLabel productFormTitle, productIDTxt, productNameTxt, productPriceTxt, productRatingTxt, productStockTxt, productBrandTxt;
	private JTextField productIDField, productNameField, productPriceField;
	private JSpinner productRatingField, productStockField;
	private JComboBox productBrandField;
	private JTable productListTable;
	private JScrollPane productTblScroll;
	private JButton insertBtn, updateBtn, deleteBtn, submitBtn, cancelBtn;
	private DefaultTableModel dtm;
	private Vector<Object> productTableContent;
	private Vector<String> vBrand;
	private ResultSet rs;
	private static Connect conn = Connect.getConnection();
	private String act = "";
	private boolean check = false;
	private String userID;
	private JDesktopPane desktopPane;
	
	public ProductForm(JDesktopPane dp, Connect c, String userID) {
		init();
		
		// inisialisasi
		dp = desktopPane;
		c = conn;
		this.userID = userID;
		init();
		setSize(1000, 600);
		setTitle("Manage Product");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void fetchTableData() {
		String query = "SELECT ProductID, brandName, ProductName, ProductPrice, ProductStock, ProductRating FROM product p JOIN brand b ON b.BrandID = p.BrandID";
		conn.rs = conn.execQuery(query);
		
		String header[] = {
				"ProductID", "BrandName", "ProductName", "ProductPrice", "ProductStock", "ProductRating"
		};
		
		dtm = new DefaultTableModel(header, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		try {
			while(conn.rs.next()) {
				productTableContent = new Vector<Object>();
				
				for (int i = 1; i < conn.rsm.getColumnCount() + 1; i++) {
					productTableContent.add(conn.rs.getObject(i) + "");
				}
				
				dtm.addRow(productTableContent);
			}
			productListTable.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void init() {
		// Main Panel
		mainPanel = new JPanel(new GridLayout(2,1,0,10));
		mainPanel.setBorder(new EmptyBorder(5,5,5,5));
				
		// Product List Panel
		productListPanel = new JPanel(new BorderLayout());
				
		// Title
		productFormTitle = new JLabel("Product List", JLabel.CENTER);
		productFormTitle.setFont(new Font("Arial", Font.BOLD, 16));
				
		productListPanel.add(productFormTitle, BorderLayout.NORTH);
				
		productListTable = new JTable(dtm);
		productListTable.addMouseListener(this);
			

		fetchTableData();
				
		// Table Scroll
		productTblScroll = new JScrollPane(productListTable);
		productTblScroll.setPreferredSize(new Dimension(800, 150));
				
		productListPanel.add(productTblScroll, BorderLayout.CENTER);
				
				
		// Manage Panel
		managePanel = new JPanel(new BorderLayout(10,20));
				
		// Product ID 
		productIDTxt = new JLabel("Product ID");
		productIDTxt.setFont(new Font("Arial", Font.BOLD, 12));
				
		productIDField = new JTextField();
		productIDField.setFont(new Font("Arial", Font.PLAIN, 12));
			
		// Product Name 
		productNameTxt = new JLabel("Product Name");
		productNameTxt.setFont(new Font("Arial", Font.BOLD, 12));

		productNameField = new JTextField();
		productNameField.setFont(new Font("Arial", Font.PLAIN, 12));
				
		// Product Price
		productPriceTxt = new JLabel("Product Price");
		productPriceTxt.setFont(new Font("Arial", Font.BOLD, 12));
				
		productPriceField = new JTextField();
		productPriceField.setFont(new Font("Arial", Font.PLAIN, 12));
				
		// Product Rating
		productRatingTxt = new JLabel("Product Rating");
		productRatingTxt.setFont(new Font("Arial", Font.BOLD, 12));
				
		productRatingField = new JSpinner(new SpinnerNumberModel(1, 1 , 100, 1));
		productRatingField.setEnabled(false);
				
		// Product Stock
		productStockTxt = new JLabel("Product Stock");
		productStockTxt.setFont(new Font("Arial", Font.BOLD, 12));
				
		productStockField = new JSpinner(new SpinnerNumberModel(1, 1 , 100, 1));
		productStockField.setEnabled(false);
				
		// Product Brand
		productBrandTxt = new JLabel("Product Brand");
		productBrandTxt.setFont(new Font("Arial", Font.BOLD, 12));
				
		vBrand = new Vector<>();
		addBrandName();
		productBrandField = new JComboBox<>(vBrand);
		productBrandField.setSelectedItem(null);
		productBrandField.setEnabled(false);
				
		// Product Input Panel
		productInputPanel = new JPanel(new GridLayout(6,2,5,0));
		productInputPanel.setPreferredSize(new Dimension(935,100));
				
		productInputPanel.add(productIDTxt);
		productInputPanel.add(productIDField);
		productInputPanel.add(productNameTxt);
		productInputPanel.add(productNameField);
		productInputPanel.add(productPriceTxt);
		productInputPanel.add(productPriceField);
		productInputPanel.add(productRatingTxt);
		productInputPanel.add(productRatingField);
		productInputPanel.add(productStockTxt);
		productInputPanel.add(productStockField);
		productInputPanel.add(productBrandTxt);
		productInputPanel.add(productBrandField);
				
		// Product to Panel
		managePanel.add(productInputPanel, BorderLayout.CENTER);
				
		// Button
		insertBtn = new JButton("Insert");
		insertBtn.setPreferredSize(new Dimension(400,30));
		insertBtn.addActionListener(this);
				
		updateBtn = new JButton("Update");
		updateBtn.setPreferredSize(new Dimension(400,30));
		updateBtn.addMouseListener(this);
				
		deleteBtn = new JButton("Delete");
		deleteBtn.setPreferredSize(new Dimension(400,30));
		deleteBtn.addMouseListener(this);
				
				
		submitBtn = new JButton("Submit");
		submitBtn.setPreferredSize(new Dimension(400,30));
		submitBtn.addActionListener(this);
		submitBtn.setEnabled(false);
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.setPreferredSize(new Dimension(400,30));
		cancelBtn.setEnabled(false);
		cancelBtn.addActionListener(this);
				
		// Button Panel
		BtnPanel = new JPanel(new GridLayout(5, 1, 100, 5));
				
			
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
				
		mainPanel.add(productListPanel);
		mainPanel.add(bottomPanel);
				
		this.add(mainPanel);
	}
	
	public void addBrandName() {
		ResultSet rs;
		String query = "SELECT BrandName from brand";
		rs = conn.execQuery(query);
		try {
			while(rs.next()) {
				String a;
				a = rs.getString("BrandName");
				vBrand.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String generateID() {
		String generateID = "PD";
		boolean check;
		
		do {
			for (int i = 0 ; i < 3; i++) {
				int rand = (int)(Math.random() * 10);
				generateID += rand;
			}
			check = validateProductID(generateID);
		} while(!check);
		
		return generateID;
	}
	
	public boolean validateProductID(String generateID) {
		String queryForCheck = "SELECT ProductID FROM product";
		
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
	
	
	public void clear() {
		insertBtn.setEnabled(true);
		updateBtn.setEnabled(true);
		deleteBtn.setEnabled(true);
		submitBtn.setEnabled(false);
		cancelBtn.setEnabled(false);
		productIDField.setEnabled(true);
		productNameField.setEnabled(true);
		productPriceField.setEnabled(true);
		productRatingField.setEnabled(false);
		productStockField.setEnabled(false);
		productBrandField.setEnabled(false);
		productBrandField.setSelectedItem(null);
		productListTable.clearSelection();
		productIDField.setText("");
		productNameField.setText("");
		productPriceField.setText("");
	}
	
	public void open() {
		insertBtn.setEnabled(false);
		updateBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
		submitBtn.setEnabled(true);
		cancelBtn.setEnabled(true);
		productIDField.setEnabled(false);
		productNameField.setEnabled(true);
		productPriceField.setEnabled(true);
		productRatingField.setEnabled(true);
		productStockField.setEnabled(true);
		productBrandField.setEnabled(true);
	}
	
	private boolean insertProduct(String productID, String brandID, String productName, int productPrice, int productRating, int productStock) {
		Product pr = new Product();

		pr.setProductID(productID);
		pr.setBrandID(brandID);
		pr.setProductName(productName);
		pr.setProductPrice(productPrice);
		pr.setProductStock(productStock);
		pr.setProductRating(productRating);
		return pr.insert();
	}
	
	private boolean updateProduct (String productID, String brandID, String productName, int productPrice, int productRating, int productStock) {
		Product pr = new Product();
		
		pr.setProductID(productID);
		pr.setBrandID(brandID);
		pr.setProductName(productName);
		pr.setProductPrice(productPrice);
		pr.setProductRating(productRating);
		pr.setProductStock(productStock);
		return pr.update();
	}
	
	private boolean deleteProduct (String productID){
		Product pr = new Product();
		
		pr.setProductID(productID);
		
		return pr.delete();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == insertBtn) {
			act = "insert";
			String id = generateID();
			
			open();
			productIDField.setText(id);

		} else if (e.getSource() == submitBtn) {
			if(act.equals("insert")) {
				int productRating = (int) productRatingField.getValue();
				if (productNameField.getText().isEmpty()){
					JOptionPane.showMessageDialog(this, "Product Name field must fill", "Message", JOptionPane.ERROR_MESSAGE);
				} else if (productPriceField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Product Price field must fill", "Message", JOptionPane.ERROR_MESSAGE);
				} else if (productBrandField.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(this, "Product Brand field must fill", "Message", JOptionPane.ERROR_MESSAGE);
				} else if (!(productRating >= 1 && productRating <= 10)) {
					JOptionPane.showMessageDialog(this, "Rating must be 1 to 10", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					String productID = productIDField.getText();
					String productName = productNameField.getText();
					int productPrice = Integer.parseInt(productPriceField.getText());
					int productStock = (int) productStockField.getValue();
					String brandProduct = productBrandField.getSelectedItem().toString();
					
					String query = "SELECT BrandID FROM brand WHERE BrandName = '" + brandProduct + "'";

					String id = null;
					try {
						rs = conn.execQuery(query);
						rs.next();
						id = rs.getString("BrandID");
					} catch (SQLException e1) {
						e1.printStackTrace();
						System.out.println("Error to get Brand ID");
					}
					insertProduct(productID, id, productName, productPrice, productRating, productStock);
					fetchTableData();
					JOptionPane.showMessageDialog(this, "Insert Success", "Message", JOptionPane.INFORMATION_MESSAGE);
					clear();
				}
			} 
			else if (act.equals("update")) {
				int productRating = (int) productRatingField.getValue();
				if (check == false) {
					JOptionPane.showMessageDialog(this, "You must select the data on the table", "Message", JOptionPane.ERROR_MESSAGE);
				} else if (productNameField.getText().isEmpty()){
					JOptionPane.showMessageDialog(this, "Product Name field must fill", "Message", JOptionPane.ERROR_MESSAGE);
				} else if (productPriceField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Product Price field must fill", "Message", JOptionPane.ERROR_MESSAGE);
				} else if (!(productRating >= 1 && productRating <= 10)) {
					JOptionPane.showMessageDialog(this, "Rating must be 1 to 10", "Message", JOptionPane.ERROR_MESSAGE);
				} else {
					String productID = productIDField.getText();
					String productName = productNameField.getText();
					int productPrice = Integer.parseInt(productPriceField.getText());
					int productStock = (int) productStockField.getValue();
					String brandProduct = productBrandField.getSelectedItem().toString();
					
					String query = "SELECT BrandID FROM brand WHERE BrandName = '" + brandProduct + "'";

					String id = null;
					try {
						rs = conn.execQuery(query);
						rs.next();
						id = rs.getString("BrandID");
					} catch (SQLException e1) {
						e1.printStackTrace();
						System.out.println("Error to get Brand ID");
					}
					
					updateProduct(productID, id, productName, productPrice, productRating, productStock);
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
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == updateBtn) {
			act ="update";
			open();
		} else if (arg0.getSource() == deleteBtn){
			act = "delete";
			insertBtn.setEnabled(false);
			updateBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			submitBtn.setEnabled(true);
			cancelBtn.setEnabled(true);
			productIDField.setEnabled(false);
			productNameField.setEnabled(false);
			productPriceField.setEnabled(false);
			productRatingField.setEnabled(false);
			productStockField.setEnabled(false);
			productBrandField.setEnabled(false);
		} else if(arg0.getSource() == productListTable) {
			int row = productListTable.getSelectedRow();
			
			String productID = productListTable.getValueAt(row, 0).toString();
				
			if(act.equals("update")) {
				productIDField.setText(productID);
			} else if (act.equals("delete")){
				deleteProduct(productID);
				fetchTableData();
				JOptionPane.showMessageDialog(this, "Delete Success", "Message", JOptionPane.INFORMATION_MESSAGE);
				clear();
			}
			
			String a = (String) productListTable.getValueAt(0, 1);
			if(!(a.isEmpty())) {
				check = true;
			}

		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
