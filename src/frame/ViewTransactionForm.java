package frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connect.Connect;

public class ViewTransactionForm extends JInternalFrame {

	private JPanel mainPanel, headerPanel, detailPanel;
	private JLabel transListTxt, transDetailTxt;
	private JTable headerTransTable, detailTransTable;
	private DefaultTableModel dtm;
	private Vector<Object> headerTableContent, detailTableContent;
	private JScrollPane headerTableScroll, detailTableScroll;
	private Connect conn = Connect.getConnection();
	private String userID;
	private int role;
	private JDesktopPane desktopPane = new JDesktopPane();
	
	public ViewTransactionForm(JDesktopPane dp, Connect c, String userID, int role) {
		desktopPane = dp;
		conn = c;
		this.userID = userID;
		this.role = role;
		init();
		
		// inisialisasi
		setSize(700, 600);
		setTitle("View Transaction");
		setClosable(true);
		setResizable(false);
		setMaximizable(true);
	}

	private void init() {
		// Main Panel
		mainPanel = new JPanel(new GridLayout(2,1));
		
		
		// Header Panel
		headerPanel = new JPanel(new BorderLayout());
		
		// Transcation List Title
		transListTxt = new JLabel("Transaction List", JLabel.CENTER);
		transListTxt.setFont(new Font("Arial", Font.BOLD, 16));
		
		// Header Table
		headerTransTable = new JTable(dtm);
		headerTransTable.setFont(new Font("Arial", Font.PLAIN, 12));
		headerTransTable.setRowHeight(15);
		
		fetchTableDataHeader();
		
		headerTableScroll = new JScrollPane(headerTransTable);
		headerTableScroll.setPreferredSize(new Dimension(650, 300));
		
		headerPanel.add(transListTxt, BorderLayout.NORTH);
		headerPanel.add(headerTableScroll, BorderLayout.CENTER);
		
		
		// Detail Panel
		detailPanel = new JPanel(new BorderLayout());
				
		// Transaction Detail Title
		transDetailTxt = new JLabel("Transaction Detail", JLabel.CENTER);
		transDetailTxt.setFont(new Font("Arial", Font.BOLD, 16));
		
		// Detail Table
		detailTransTable = new JTable(dtm);
		detailTransTable.setFont(new Font("Arial", Font.PLAIN, 12));
		detailTransTable.setRowHeight(15);
		
		fetchTableDataDetail();
		
		detailTableScroll = new JScrollPane(detailTransTable);
		detailTableScroll.setPreferredSize(new Dimension(650, 300));
		
		detailPanel.add(transDetailTxt, BorderLayout.NORTH);
		detailPanel.add(detailTableScroll, BorderLayout.CENTER);
		
		mainPanel.add(headerPanel);
		mainPanel.add(detailPanel);
		
		add(mainPanel);
		
	}

	public void fetchTableDataHeader() {
		String query = "";
		
		if(role == 0) {
			 query = "SELECT * FROM headertransaction WHERE UserID = '" + userID + "'";
		} else if (role == 1) {
			 query = "SELECT * FROM headertransaction";
		}
		
		conn.rs = conn.execQuery(query);
		
		String header[] = {
				"TransactionID", "UserID", "TransactionDate"
		};
		
		dtm = new DefaultTableModel(header, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		try {
			while(conn.rs.next()) {
				headerTableContent = new Vector<Object>();
				
				for (int i = 1; i < conn.rsm.getColumnCount() + 1; i++) {
					headerTableContent.add(conn.rs.getObject(i) + "");
				}
				
				dtm.addRow(headerTableContent);
			}
			headerTransTable.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fetchTableDataDetail() {
		String query = "";
		
		if(role == 0) {
			 query = "SELECT * FROM detailtransaction dt JOIN headertransaction ht ON dt.TransactionID = ht.TransactionID JOIN user u ON ht.UserID = u.UserID WHERE u.UserID = '" + userID + "'";
		} else if (role == 1) {
			 query = "SELECT * FROM detailtransaction";
		}
		conn.rs = conn.execQuery(query);
		
		String header[] = {
				"TransactionID", "ProductID", "Qty"
		};
		
		dtm = new DefaultTableModel(header, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		try {
			while(conn.rs.next()) {
				detailTableContent = new Vector<Object>();
				
				for (int i = 1; i < conn.rsm.getColumnCount() + 1; i++) {
					detailTableContent.add(conn.rs.getObject(i) + "");
				}
				
				dtm.addRow(detailTableContent);
			}
			detailTransTable.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
