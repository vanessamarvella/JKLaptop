package frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import connect.Connect;
import model.User;

public class MainForm extends JFrame implements ActionListener{
	
	private JPanel mainPanel;
	private JMenuBar mainMenuBar;
	private JMenu manageMenu, transMenu, logoutMenu;
	private JMenuItem brandMenuItem, productMenuItem, buyMenuItem, viewTransMenuItem, logOutItem;
	private User user;
	private JDesktopPane desktopPane;
	private int role;
	private String userID;
	private Connect con = Connect.getConnection();
	
	public MainForm(String userID, int role)  {
		init();
		
		if(role == 0) { // Member
			mainMenuBar.add(transMenu);
			mainMenuBar.add(logoutMenu);
		
			transMenu.add(buyMenuItem);
			transMenu.add(viewTransMenuItem);
			logoutMenu.add(logOutItem);
		} else if (role == 1) { // Admin
			mainMenuBar.add(manageMenu);
			mainMenuBar.add(transMenu);
			mainMenuBar.add(logoutMenu);
			
			manageMenu.add(brandMenuItem);
			manageMenu.add(productMenuItem);
			transMenu.add(viewTransMenuItem);
			logoutMenu.add(logOutItem);
		}
		
		// Listener 
		brandMenuItem.addActionListener(this);
		productMenuItem.addActionListener(this);
		logOutItem.addActionListener(this);
		buyMenuItem.addActionListener(this);
		logOutItem.addActionListener(this);
		viewTransMenuItem.addActionListener(this);
		mainPanel.add(mainMenuBar, BorderLayout.NORTH);
		
		add(mainPanel);

		// inisialisasi frame
		desktopPane = new JDesktopPane();
		setContentPane(desktopPane);
		setLayout(new BorderLayout());
		this.userID = userID;
		this.role = role;
		con = new Connect();
		setVisible(true);
		setLocationRelativeTo(null);
		setSize(700, 600);
		setTitle("Main Form");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setJMenuBar(mainMenuBar);
	}

	private void init() {
		// Main Panel
		mainPanel = new JPanel(new BorderLayout());
						
		// Top Panel 		
		mainMenuBar = new JMenuBar();
		manageMenu = new JMenu("Manage");
		transMenu = new JMenu("Transaction");
		logoutMenu = new JMenu("Logout");
				
		brandMenuItem = new JMenuItem("Brand");
		productMenuItem = new JMenuItem("Product");
		buyMenuItem = new JMenuItem("Buy Product");
		viewTransMenuItem = new JMenuItem("View Transaction");
		logOutItem = new JMenuItem("Logout");
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		desktopPane.removeAll();
		if (arg0.getSource() == brandMenuItem) {
			BrandForm bf= new BrandForm(desktopPane, con, userID);
			desktopPane.add(bf);
			bf.setVisible(true);
		} else if (arg0.getSource() == productMenuItem) {
			ProductForm pf = new ProductForm(desktopPane, con, userID);
			desktopPane.add(pf);
			pf.setVisible(true);
		} else if (arg0.getSource() == buyMenuItem) {
			BuyProductForm bpf = new BuyProductForm(desktopPane, con, userID);
			desktopPane.add(bpf);
			bpf.setVisible(true);
		} else if (arg0.getSource() == viewTransMenuItem) {
			ViewTransactionForm vtf = new ViewTransactionForm(desktopPane, con, userID, role);
			desktopPane.add(vtf);
			vtf.setVisible(true);
		} else if (arg0.getSource() == logOutItem) {
			this.dispose();
			new LoginForm().setVisible(true);
		}
	}

}
