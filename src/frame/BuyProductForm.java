package frame;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
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
import model.Cart;
import model.Product;

public class BuyProductForm extends JInternalFrame implements ActionListener, MouseListener{

	private JPanel mainPanel, topPanel, centerPanel, bottomPanel, formPanel, qtyPanel;
	private JLabel productFormTitle, productIDTxt, productNameTxt, productPriceTxt, productBrandTxt, qtyTxt, ratingTxt;
	private JTextField productIDField, productNameField, productPriceField, productBrandField, ratingField;
	private JTable productTable;
	private JScrollPane productTableScroll;
	private JSpinner qtyField;
	private DefaultTableModel dtm;
	private JButton addToCartBtn;
	private Vector<Object> productTableContent;
	private Connect conn = Connect.getConnection();
	private String userID;
	private JDesktopPane desktopPane;

	public BuyProductForm(JDesktopPane dp, Connect c, String userID) {
		init();
		
		// inisialisasi
		desktopPane = dp;
		c = conn;
		this.userID = userID;
		setSize(700, 600);
		setTitle("Buy Product");
		setClosable(true);
		setResizable(false);
		setMaximizable(true);
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
			productTable.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		// Main Panel
		mainPanel = new JPanel(new BorderLayout());
				
		// Top Panel
		topPanel = new JPanel();
				
		// Title
		productFormTitle = new JLabel("Our Product");
		productFormTitle.setFont(new Font("Arial", Font.BOLD, 16));
				
		topPanel.add(productFormTitle);
				
		// Center Panel
		centerPanel = new JPanel();
				
		// Tabel
		productTable = new JTable(dtm);
		productTable.setFont(new Font("Arial", Font.PLAIN, 12));
		productTable.setRowHeight(15);
		
		fetchTableData();
		
		productTableScroll = new JScrollPane(productTable);
		productTableScroll.setPreferredSize(new Dimension(650, 300));
		
		centerPanel.add(productTableScroll);
				
		// Form Panel
		formPanel = new JPanel(new GridLayout(6,2,130,2));
				
		// Product ID
		productIDTxt = new JLabel("ProductID");
		productIDTxt.setFont(new Font("Arial", Font.BOLD, 12));
		productIDTxt.setBorder(new EmptyBorder(10,250,10,10));
				
				
		productIDField = new JTextField("-");
		productIDField.setEditable(false);
		productIDField.setBorder(new EmptyBorder(10,4,10,10));
		productIDField.setFont(new Font("Arial", Font.PLAIN, 12));

		formPanel.add(productIDTxt);
		formPanel.add(productIDField);
				
		// Product Name
		productNameTxt = new JLabel("ProductName");
		productNameTxt.setFont(new Font("Arial", Font.BOLD, 12));
		productNameTxt.setBorder(new EmptyBorder(10,250,10,10));
				
				
		productNameField = new JTextField("-");
		productNameField.setEditable(false);
		productNameField.setBorder(new EmptyBorder(10,4,10,10));
		productNameField.setFont(new Font("Arial", Font.PLAIN, 12));

		formPanel.add(productNameTxt);
		formPanel.add(productNameField);
				
		// Product Price
		productPriceTxt = new JLabel("ProductPrice");
		productPriceTxt.setFont(new Font("Arial", Font.BOLD, 12));
		productPriceTxt.setBorder(new EmptyBorder(10,250,10,10));
				
				
		productPriceField = new JTextField("-");
		productPriceField.setEditable(false);
		productPriceField.setBorder(new EmptyBorder(10,4,10,10));
		productPriceField.setFont(new Font("Arial", Font.PLAIN, 12));

		formPanel.add(productPriceTxt);
		formPanel.add(productPriceField);
				
		// Product Brand
		productBrandTxt = new JLabel("ProductBrand");
		productBrandTxt.setFont(new Font("Arial", Font.BOLD, 12));
		productBrandTxt.setBorder(new EmptyBorder(10,250,10,10));
				
				
		productBrandField = new JTextField("-");
		productBrandField.setEditable(false);
		productBrandField.setBorder(new EmptyBorder(10,4,10,10));
		productBrandField.setFont(new Font("Arial", Font.PLAIN, 12));
				

		formPanel.add(productBrandTxt);
		formPanel.add(productBrandField);
				
				
		// Quantity
		qtyTxt = new JLabel("Quantity");
		qtyTxt.setFont(new Font("Arial", Font.BOLD, 12));
		qtyTxt.setBorder(new EmptyBorder(10,250,10,10));

		qtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		qtyField = new JSpinner(new SpinnerNumberModel(0, 0 , 100, 1));
		qtyField.setEnabled(false);
			
		qtyField.setPreferredSize(new Dimension(250,35));
				
		qtyPanel.add(qtyField);
				
		formPanel.add(qtyTxt);
		formPanel.add(qtyPanel);
				
		// Rating
		ratingTxt = new JLabel("Rating");
		ratingTxt.setFont(new Font("Arial", Font.BOLD, 12));
		ratingTxt.setBorder(new EmptyBorder(10,250,10,10));
				
				
		ratingField = new JTextField("-");
		ratingField.setEditable(false);
		ratingField.setBorder(new EmptyBorder(10,4,10,10));
		ratingField.setFont(new Font("Arial", Font.PLAIN, 12));

		formPanel.add(ratingTxt);
		formPanel.add(ratingField);
				
		centerPanel.add(formPanel);
				
		// Bottom Panel
		bottomPanel = new JPanel();
				
		// Button
		addToCartBtn = new JButton("Add to Cart");
		addToCartBtn.setEnabled(false);
				
		bottomPanel.add(addToCartBtn);
				
		// Add ke Panel
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
				
		add(mainPanel);
				
				
		// Listener
		productTable.addMouseListener(this);
		addToCartBtn.addActionListener(this);
	}
	
	private boolean insertToCart(String userID, String productID, int qty) {
		Cart c = new Cart();
		
		c.setUserID(userID);
		c.setProductID(productID);
		c.setQty(qty);
		return c.insert();
	}
	
	private boolean update (int productStock, String productID){
		Product pr = new Product();
		
		pr.setProductStock(productStock);
		pr.setProductID(productID);
		
		return pr.updateStock();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == productTable) {
			addToCartBtn.setEnabled(true);
			qtyField.setEnabled(true);
			
			int row = productTable.getSelectedRow();
			
			String productID = productTable.getValueAt(row, 0).toString();
			String brandName = productTable.getValueAt(row, 1).toString();
			String productName = productTable.getValueAt(row, 2).toString();
			String productPrice = productTable.getValueAt(row, 3).toString();
			String productRating = productTable.getValueAt(row, 5).toString();
			
			productIDField.setText(productID);
			productBrandField.setText(brandName);
			productNameField.setText(productName);
			productPriceField.setText(productPrice);
			ratingField.setText(productRating);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addToCartBtn) {
			int row = productTable.getSelectedRow();
			
			String qty = productTable.getValueAt(row, 4).toString();
			String inputQty = qtyField.getValue().toString();
			String id = productIDField.getText();
			
			Integer a = Integer.parseInt(qty);
			Integer b = Integer.parseInt(inputQty);
			Integer currentStock = a - b;
			
			if(b > a) {
				JOptionPane.showMessageDialog(this, "Quantity cannot be more than available stock", "Message", JOptionPane.ERROR_MESSAGE);
			} else if(b < 1) {
				JOptionPane.showMessageDialog(this, "Quantity minimum is 1", "Message", JOptionPane.ERROR_MESSAGE);
			} else {
				insertToCart(userID, productIDField.getText(), Integer.parseInt(inputQty));
				update(currentStock, id);
				desktopPane.remove(this);
				CartForm cf = new CartForm(conn, userID);
				desktopPane.add(cf);
				cf.setVisible(true);
			}

		}
	}

}
