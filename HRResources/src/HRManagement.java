import java.awt.EventQueue;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.awt.Toolkit;
import com.toedter.calendar.JDateChooser;

public class HRManagement  {

	public static JFrame frmHumanResourcesManagement;
	private JTextField jtxtEmployeeID;
	private JTextField jtxtInsuranceNumber;
	private JTextField jtxtFirstName;
	private JTextField jtxtSurname;
	private JTextField jtxtGender;
	private JTextField jtxtDateofBrith;
	private JTextField jtxtAge;
	private JTextField jtxtSalary;
	private JTable table;
	
	/**
	 * Those action are made to conected with data base!
	 */
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	DefaultTableModel model = new DefaultTableModel();
	
	//---This method were made for calling to Calculate salarys in J Frame CalP--//
	private JTextField txtUsername;
	private JPasswordField txtpasswordField;
	private JTextField txtEmployeeRefNumber;
	private JTextField txtEmployeeName;
	private JTextField txtEmployeeAdress;
	private JTextField txtPostCode;
	private JTextField txtEmployersName;
	private JTextField txtInnerCity;
	private JTextField txtBasicSalary;
	private JTextField txtOverTime;
	private JTextField txtGrossPay;
	private JTextField txtPensionablePay;
	private JTextField txtNetPay;
	private JTextField txtPayDate;
	private JTextField txtNINumber;
	private JTextField txtTax;
	private JTextField txtPensione;
	private JTextField txtStudentLoan;
	private JTextField txtNIPayment;
	private JTextField txtTaxablePay;
	private JTextField txtDeductions;
	private JComboBox comboBoxEmployeeName;
	
	
	
	//--This method were made for calling to Calculate salarys in J Frame CalP--//
	double InnerCity;
	double BasicWage;
	double OverTime;
	double Gross;
	private JTextField textField;
	
	
	//---This was made to calling from IDEmployeeCARD---//
	private JComboBox comboBoxIDNumberIDCard;
	private JComboBox comboBoxGenderIDCard;
	private JTextField txtnameIDCard;
	private JTextField txtIDSurname;
	private JTextField textField_PathForImage;
	private JTextField textField_PathForEmblame;
	private JTextField txtCompanyIDCard;
	private JTextField txtSurnameCal;
	private JDateChooser dateChooserBrithIDCard;
	
	//private JLabel lblNewLabelDImage;
	
	/**
     * This metodth was made to generetae Button PRINT in JPANEL for IDCARD from JFrame IDEMployedCard//
     */
	public void printRecord(JComponent c)
	{
		PrinterJob pj=PrinterJob.getPrinterJob();
		pj.setJobName("Print");
		pj.setPrintable(new Printable()
				{
					@Override
					public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
						if(pageIndex>0)
							return Printable.NO_SUCH_PAGE;
						Graphics2D gd=(Graphics2D)graphics;
						gd.translate(pageFormat.getImageableX(),pageFormat.getImageableY());
						c.printAll(gd);
						return Printable.PAGE_EXISTS;
					}
			
				});
		if (pj.printDialog() == false)
		  return;

		  try {
		        pj.print();
		  } catch (PrinterException ex) {
		        // handle exception
		  }
	}
    
	/**
	 * This method was made to Update Table in JTable window in Jframe!
	 */
	
	public void updateTable() {
		conn = EmployeeData.ConnectDB();
		 if(conn !=null)
		{
			String sql ="SELECT EmployeeID,InsuranceNumber,Firstname,Surname,Gender,DateofBrith,Age,Salary FROM employee";
		try
		{
	
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
		Object [] columnData = new Object [8];
				
		while(rs.next()) {
			columnData [0] = rs.getString("EmployeeID");
			columnData [1] = rs.getString("InsuranceNumber");
			columnData [2] = rs.getString("Firstname");
			columnData [3] = rs.getString("Surname");
			columnData [4] = rs.getString("Gender");
			columnData [5] = rs.getString("DateofBrith");
			columnData [6] = rs.getString("Age");
			columnData [7] = rs.getString("Salary");
			
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			
			model.addRow(columnData);
		}
	}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
	}finally {
		try {
			rs.close();
			pst.close();
	}catch(Exception e) {
			}
		}	
	}
}
	
	/**
	 * This method was made to fullfill comboBox for FirstName an Surname in Calculation Salary with data from data base Sqlite!
	 */
	
	@SuppressWarnings("unchecked")
	public void fillComboBox() {
		
		try {
			String query ="select * from employee";
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()) {	
				comboBoxEmployeeName.addItem(rs.getString("Firstname"));
				comboBoxIDNumberIDCard.addItem(rs.getString("EmployeeID"));	
		}
			
	}
		catch (Exception e) {
			e.printStackTrace();		
		}
	}
	
	
	

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HRManagement window1 = new HRManagement();
					window1.frmHumanResourcesManagement.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public HRManagement() {
		initialize();
		
		// --This action was  made to conected data Table from Sqlite data base with class HRManagement!--//
		
		conn = EmployeeData.ConnectDB();
		Object col[]= {"EmployeeID","InsuranceNumber","Firstname","Surname", "Gender","DateofBrith", "Age", "Salary"};
		model.setColumnIdentifiers(col);
		
		updateTable();
		fillComboBox();
		
	}

	public static void Menu() {
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("rawtypes")
	private void initialize() {
		
		//--- Stabalized new Icon to Jframe---//
		frmHumanResourcesManagement = new JFrame();
		frmHumanResourcesManagement.setIconImage(Toolkit.getDefaultToolkit().getImage("res\\IconHuman.jpg"));
		frmHumanResourcesManagement.setTitle("Human Resources Management");
		frmHumanResourcesManagement.addWindowListener(new WindowAdapter() {
			//----- This action was made to show date in the Calculation Salary in PayDate section and EmployeRef Number----//
			public void windowActivated(WindowEvent e) {
				int refs = 1325 + (int)(Math.random()*4238);
				Calendar timer = Calendar.getInstance();
				timer.getTime();
				SimpleDateFormat tTime = new SimpleDateFormat("HH:mm:ss");
				tTime.format(timer.getTime());
				SimpleDateFormat Tdate = new SimpleDateFormat("dd-MMM-yyyy");
				Tdate.format(timer.getTime());
				txtPayDate.setText(Tdate.format(timer.getTime()));
				
				String sRef = String.format("EMPNO"+ refs);
				txtEmployeeRefNumber.setText(sRef);
			}
		});
		frmHumanResourcesManagement.setBounds(100, 100, 1106, 634);
		frmHumanResourcesManagement.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CardLayout myLayout = new CardLayout();
		frmHumanResourcesManagement.getContentPane().setLayout(myLayout);
		
		JPanel LoginPage = new JPanel();
		frmHumanResourcesManagement.getContentPane().add(LoginPage, "login");
		LoginPage.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login System");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(458, 36, 89, 14);
		LoginPage.add(lblLogin);
		
		JButton btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "main");
			}
		});
		btnCheck.setBounds(529, 274, 89, 23);
		LoginPage.add(btnCheck);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsername.setBounds(325, 135, 84, 14);
		LoginPage.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPassword.setBounds(325, 196, 84, 14);
		LoginPage.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(461, 135, 176, 20);
		LoginPage.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtpasswordField = new JPasswordField();
		txtpasswordField.setBounds(461, 193, 176, 20);
		LoginPage.add(txtpasswordField);
		
		// --This button was made to Login in the next JPanel with Password and Username!--//
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = txtpasswordField.getText();
				String username = txtUsername.getText();
				
				if(password.contains("sivka") && username.contains("sivka")) {
					txtpasswordField.setText(null);
					txtUsername.setText(null);
					myLayout.show(frmHumanResourcesManagement.getContentPane(), "main");
				}else {
					JOptionPane.showMessageDialog(null, "Invalind Login Details","Login Error", JOptionPane.ERROR_MESSAGE);
					txtpasswordField.setText(null);
					txtUsername.setText(null);
				}
					
			}
		});
		btnLogin.setBounds(286, 274, 89, 23);
		LoginPage.add(btnLogin);
		
		// This button was made to RESET the text in Username and Password Field!
		
		JButton btnResetLogin = new JButton("Reset");
		btnResetLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUsername.setText(null);
				txtpasswordField.setText(null);
			}
		});
		btnResetLogin.setBounds(407, 274, 89, 23);
		LoginPage.add(btnResetLogin);
		
		// This button was made to get Exit from Login JFrame!
		
		JButton btnExitLogin = new JButton("Exit");
		btnExitLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmHumanResourcesManagement = new JFrame ("Exit");
				if (JOptionPane.showConfirmDialog(frmHumanResourcesManagement,"Confirm if you want to exit!", "Employee data base system", 
						JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExitLogin.setBounds(651, 274, 89, 23);
		LoginPage.add(btnExitLogin);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(286, 242, 454, 2);
		LoginPage.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(286, 91, 454, 2);
		LoginPage.add(separator_1);
		
		/*
		 * THIS IS A JPanel MAINPAGE (BELOW SHOWING ALL BUTTONS FROM MAIN PAGE)!
		 */
		JPanel MainPage = new JPanel();
		frmHumanResourcesManagement.getContentPane().add(MainPage, "main");
		MainPage.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setIcon(new ImageIcon(getClass().getClassLoader().getResource("HumanResources.png")));
		//C:\\Users\\hn\\Desktop\\Project\\Programiranje\\Eclipse\\HRResources\\
		lblNewLabel_4.setBounds(225, 79, 774, 409);
		MainPage.add(lblNewLabel_4);
	
		// This is a Button in MainMenu to enter in Employee databse
		JButton btnManagement = new JButton("Employee database");
		btnManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "management");
			}
		});
		btnManagement.setBounds(45, 99, 170, 41);
		MainPage.add(btnManagement);
		
		// This is a Button in MainMenu to enter in Salary calculation
		JButton btnNewButton = new JButton("Salary calculation");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "calc");
			}
		});
		btnNewButton.setBounds(45, 165, 170, 41);
		MainPage.add(btnNewButton);
		
		// This is a Button in MainMenu to enter in Employee identity cards
		JButton btnNewButton_1 = new JButton("Employee identity cards");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "test");
			}
		});
		btnNewButton_1.setBounds(45, 229, 170, 41);
		MainPage.add(btnNewButton_1);
		
		// This is a Button in MainMenu to EXIT from MainPage to login section
		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "login");
			}
		});
		btnNewButton_2.setBounds(45, 296, 170, 41);
		MainPage.add(btnNewButton_2);
		
		
		JLabel lblNewLabel = new JLabel("Main Menu");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(468, 47, 291, 28);
		MainPage.add(lblNewLabel);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1092, 22);
		MainPage.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		// JMenuItem (MainPage) to enter in Employee database section
		JMenuItem mntmNewMenuItem = new JMenuItem("Employee databse");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "management");
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		// JMenuItem (MainPage) to enter in Salary Calculation section
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Salary calculation");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "calc");
			}
		});
		
		mnNewMenu.add(mntmNewMenuItem_1);
		
		// JMenuItem (MainPage) to enter in Employee cards
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Employee cards");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "test");
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		
		
		/*
		 * THIS IS A NEW JPanel CALCULATION SALARY (shows all data for salary calculation)!
		 */
		
		JPanel CalP = new JPanel();
		frmHumanResourcesManagement.getContentPane().add(CalP, "calc");
		CalP.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Calculation salary");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(336, 11, 139, 14);
		CalP.add(lblNewLabel_1);
		
		
		// This is a back button to go on main menu!//This action was made to go back to main menu!
		
		JButton btnNewButton_3 = new JButton("Back");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "main");
			}
		});
		btnNewButton_3.setBounds(24, 448, 89, 23);
		CalP.add(btnNewButton_3);
		
		JLabel lblEmployeRefNumber = new JLabel("Employee Ref. Num.");
		lblEmployeRefNumber.setBounds(36, 76, 139, 14);
		CalP.add(lblEmployeRefNumber);
		
		JLabel lblEmployeeName = new JLabel("Employee Name");
		lblEmployeeName.setBounds(36, 101, 139, 14);
		CalP.add(lblEmployeeName);
		
		JLabel lblEmployeeAdress = new JLabel("Employee Adress");
		lblEmployeeAdress.setBounds(36, 154, 139, 14);
		CalP.add(lblEmployeeAdress);
		
		JLabel lblPostCode = new JLabel("PostCode");
		lblPostCode.setBounds(36, 185, 106, 14);
		CalP.add(lblPostCode);
		
		txtEmployeeRefNumber = new JTextField();
		txtEmployeeRefNumber.setBounds(167, 73, 106, 20);
		CalP.add(txtEmployeeRefNumber);
		txtEmployeeRefNumber.setColumns(10);
		
		comboBoxEmployeeName = new JComboBox();
		comboBoxEmployeeName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String sql ="select * from employee where Firstname=?";
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setString(1, (String)comboBoxEmployeeName.getSelectedItem());
					
					ResultSet rs=pst.executeQuery();
					
					while(rs.next()){
		                
		                txtSurnameCal.setText(rs.getString("Surname"));
		                txtNINumber.setText(rs.getString("InsuranceNumber"));
		                txtBasicSalary.setText(rs.getString("Salary").replace(".","").replace(" ","").replace("$",""));
		                	                
		            }	
						
				}catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		comboBoxEmployeeName.setBounds(167, 100, 106, 21);
		CalP.add(comboBoxEmployeeName);
		
		txtSurnameCal = new JTextField();
		txtSurnameCal.setBounds(167, 125, 106, 19);
		CalP.add(txtSurnameCal);
		txtSurnameCal.setColumns(10);
		
		txtEmployeeAdress = new JTextField();
		txtEmployeeAdress.setColumns(10);
		txtEmployeeAdress.setBounds(167, 152, 106, 20);
		CalP.add(txtEmployeeAdress);
		
		txtPostCode = new JTextField();
		txtPostCode.setColumns(10);
		txtPostCode.setBounds(167, 183, 106, 20);
		CalP.add(txtPostCode);
		
		JLabel lblEmployersName = new JLabel("Employer's Name");
		lblEmployersName.setBounds(36, 48, 139, 14);
		CalP.add(lblEmployersName);
		
		txtEmployersName = new JTextField();
		txtEmployersName.setColumns(10);
		txtEmployersName.setBounds(167, 45, 106, 20);
		CalP.add(txtEmployersName);
		
		JLabel lblInnerCity = new JLabel("Inner City");
		lblInnerCity.setBounds(36, 213, 139, 14);
		CalP.add(lblInnerCity);
		
		txtInnerCity = new JTextField();
		txtInnerCity.setColumns(10);
		txtInnerCity.setBounds(167, 210, 106, 20);
		CalP.add(txtInnerCity);
		
		txtBasicSalary = new JTextField();
		txtBasicSalary.setColumns(10);
		txtBasicSalary.setBounds(167, 238, 106, 20);
		CalP.add(txtBasicSalary);
		
		JLabel lblBasicSalary = new JLabel("Basic Salary");
		lblBasicSalary.setBounds(36, 241, 139, 14);
		CalP.add(lblBasicSalary);
		
		JLabel lblOverTime = new JLabel("Over Time");
		lblOverTime.setBounds(36, 269, 106, 14);
		CalP.add(lblOverTime);
		
		txtOverTime = new JTextField();
		txtOverTime.setColumns(10);
		txtOverTime.setBounds(167, 266, 106, 20);
		CalP.add(txtOverTime);
		
		JLabel lblGrossPay = new JLabel("Gross Pay");
		lblGrossPay.setBounds(36, 319, 139, 14);
		CalP.add(lblGrossPay);
		
		txtGrossPay = new JTextField();
		txtGrossPay.setColumns(10);
		txtGrossPay.setBounds(167, 316, 106, 20);
		CalP.add(txtGrossPay);
		
		txtPensionablePay = new JTextField();
		txtPensionablePay.setColumns(10);
		txtPensionablePay.setBounds(167, 344, 106, 20);
		CalP.add(txtPensionablePay);
		
		JLabel lblPensionablePay = new JLabel("Pensionable Pay");
		lblPensionablePay.setBounds(36, 347, 139, 14);
		CalP.add(lblPensionablePay);
		
		JLabel lblNetpay = new JLabel("Net Pay");
		lblNetpay.setBounds(36, 375, 106, 14);
		CalP.add(lblNetpay);
		
		txtNetPay = new JTextField();
		txtNetPay.setColumns(10);
		txtNetPay.setBounds(167, 372, 106, 20);
		CalP.add(txtNetPay);
		
		JLabel lblPayDate = new JLabel("Pay Date");
		lblPayDate.setBounds(313, 48, 106, 14);
		CalP.add(lblPayDate);
		
		txtPayDate = new JTextField();
		txtPayDate.setColumns(10);
		txtPayDate.setBounds(435, 42, 115, 20);
		CalP.add(txtPayDate);
		
		JLabel lblTaxPeriod = new JLabel("Tax Period");
		lblTaxPeriod.setBounds(313, 79, 106, 14);
		CalP.add(lblTaxPeriod);
		
		JComboBox comBoxTaxPeriod = new JComboBox();
		comBoxTaxPeriod.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comBoxTaxPeriod.setBounds(435, 73, 115, 23);
		CalP.add(comBoxTaxPeriod);
		
		JLabel lblTaxCode = new JLabel("Tax Code");
		lblTaxCode.setBounds(313, 113, 106, 14);
		CalP.add(lblTaxCode);
		
		JComboBox comBoxTaxCode = new JComboBox();
		comBoxTaxCode.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G"}));
		comBoxTaxCode.setBounds(435, 107, 115, 23);
		CalP.add(comBoxTaxCode);
		
		JLabel lblNiCode = new JLabel("NI Code");
		lblNiCode.setBounds(313, 154, 106, 14);
		CalP.add(lblNiCode);
		
		JComboBox comBoxNICode = new JComboBox();
		comBoxNICode.setModel(new DefaultComboBoxModel(new String[] {"A0000", "A3000", "B4000", "C5000", "D6000", "E7000", "F8000", "G9000"}));
		comBoxNICode.setBounds(435, 148, 115, 23);
		CalP.add(comBoxNICode);
		
		JLabel lblNiNumber = new JLabel("NI Number");
		lblNiNumber.setBounds(313, 188, 106, 14);
		CalP.add(lblNiNumber);
		
		txtNINumber = new JTextField();
		txtNINumber.setColumns(10);
		txtNINumber.setBounds(435, 182, 115, 20);
		CalP.add(txtNINumber);
		
		JLabel lblTax = new JLabel("Tax");
		lblTax.setBounds(313, 216, 139, 14);
		CalP.add(lblTax);
		
		txtTax = new JTextField();
		txtTax.setColumns(10);
		txtTax.setBounds(435, 213, 115, 20);
		CalP.add(txtTax);
		
		JLabel lblPensione = new JLabel("Pensione");
		lblPensione.setBounds(313, 244, 139, 14);
		CalP.add(lblPensione);
		
		txtPensione = new JTextField();
		txtPensione.setColumns(10);
		txtPensione.setBounds(435, 241, 115, 20);
		CalP.add(txtPensione);
		
		JLabel lblStudentLoan = new JLabel("Student Loan");
		lblStudentLoan.setBounds(313, 272, 106, 14);
		CalP.add(lblStudentLoan);
		
		txtStudentLoan = new JTextField();
		txtStudentLoan.setColumns(10);
		txtStudentLoan.setBounds(435, 269, 115, 20);
		CalP.add(txtStudentLoan);
		
		JLabel lblNIPayment = new JLabel("NI Payment");
		lblNIPayment.setBounds(313, 322, 139, 14);
		CalP.add(lblNIPayment);
		
		txtNIPayment = new JTextField();
		txtNIPayment.setColumns(10);
		txtNIPayment.setBounds(435, 319, 115, 20);
		CalP.add(txtNIPayment);
		
		JLabel lblTaxablePay = new JLabel("Taxable Pay");
		lblTaxablePay.setBounds(313, 350, 139, 14);
		CalP.add(lblTaxablePay);
		
		txtTaxablePay = new JTextField();
		txtTaxablePay.setColumns(10);
		txtTaxablePay.setBounds(435, 347, 115, 20);
		CalP.add(txtTaxablePay);
		
		JLabel lblDeductions = new JLabel("Deductions");
		lblDeductions.setBounds(313, 378, 106, 14);
		CalP.add(lblDeductions);
		
		txtDeductions = new JTextField();
		txtDeductions.setColumns(10);
		txtDeductions.setBounds(435, 375, 115, 20);
		CalP.add(txtDeductions);
		
		JLabel lblPaySlip = new JLabel("Pay Slip:");
		lblPaySlip.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPaySlip.setBounds(602, 42, 72, 14);
		CalP.add(lblPaySlip);
		
		JTextArea rtfPaySlip = new JTextArea();
		rtfPaySlip.setFont(new Font("Monospaced", Font.PLAIN, 15));
		rtfPaySlip.setBounds(585, 73, 467, 316);
		CalP.add(rtfPaySlip);
		
		
		// This button was made to calculate GrossPay (InnerCity + BasicSalary + OverTime)
		JButton btnNetWages = new JButton("Net Wages");
		btnNetWages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double A00, A30, B40, C50, D60, E70, F80, G90, PensionablePay, TaxablePay;
				double deduction, Pension, StudentLoad, NIPayment, TotalDeduction, NetPay;
				
				InnerCity = Double.parseDouble(txtInnerCity.getText());
				BasicWage = Double.parseDouble(txtBasicSalary.getText());
				OverTime = Double.parseDouble(txtOverTime.getText());
				Gross = InnerCity + BasicWage + OverTime;
				String GrossPay = String.format("$%.2f", Gross);
				txtGrossPay.setText(GrossPay);
				
				//=================================================================//
				//=================================================================//
				//=================================================================//
				
				try {
					
					if(comBoxNICode.getSelectedItem().equals("A0000")) {
						A00 = (Gross * 9)/100;
						deduction = Gross - A00;
						Pension = (deduction *12)/100;
						
						StudentLoad = Pension + A00;
						StudentLoad = Gross - StudentLoad;
						StudentLoad = (StudentLoad * 5.7)/100;
						
						NIPayment = StudentLoad + Pension + A00;
						NIPayment = Gross - NIPayment;
						NIPayment = (NIPayment * 4.2)/100;
						
						String TaxPaid = String.format("%.2f", A00);
						txtTax.setText(TaxPaid);
						txtTax.setText("$" + TaxPaid);
						
						String PensionPaid = String.format("%.2f", Pension);
						txtPensione.setText(PensionPaid);
						txtPensione.setText("$" + PensionPaid);
						
						String StudentLoanPaid = String.format("%.2f", StudentLoad);
						txtStudentLoan.setText(StudentLoanPaid);
						txtStudentLoan.setText("$" + StudentLoanPaid);
						
						String NIPaymentPaid = String.format("%.2f", NIPayment);
						txtNIPayment.setText(NIPaymentPaid);
						txtNIPayment.setText("$" + NIPaymentPaid);
						
						TotalDeduction = A00 + Pension + StudentLoad + NIPayment;
						NetPay = Gross - TotalDeduction;
						
						String TDeduction = String.format("%.2f", TotalDeduction);
						txtDeductions.setText(TDeduction);
						txtDeductions.setText("$" + TDeduction);
						
						String TNetPay = String.format("%.2f", NetPay);
						txtNetPay.setText(TNetPay);
						txtNetPay.setText("$" + TNetPay);
						
						String TaxPeriod = comBoxTaxPeriod.getSelectedItem().toString();
						TaxablePay = Double.parseDouble(TaxPeriod);
						PensionablePay = TaxablePay * Pension;
						TaxablePay = TaxablePay * A00;
						
						String PensionPayment = String.format("%.2f", PensionablePay);
						txtPensionablePay.setText(PensionPayment);
						txtPensionablePay.setText("$" + PensionPayment);
						
						String TaxablePayPayment = String.format("%.2f", TaxablePay);
						txtTaxablePay.setText(TaxablePayPayment);
						txtTaxablePay.setText("$" + TaxablePayPayment);
						
					}
					
					else if(comBoxNICode.getSelectedItem().equals("A3000")) {
						A30 = 3000/12;
						A00 = (Gross * 9)/100;
						deduction = Gross - A00;
						Pension = (deduction *12)/100;
						
						StudentLoad = Pension + A00;
						StudentLoad = Gross - StudentLoad;
						StudentLoad = (StudentLoad * 5.7)/100;
						
						NIPayment = StudentLoad + Pension + A00;
						NIPayment = Gross - NIPayment;
						NIPayment = (NIPayment * 4.2)/100;
						
						String TaxPaid = String.format("%.2f", A00);
						txtTax.setText(TaxPaid);
						txtTax.setText("$" + TaxPaid);
						
						String PensionPaid = String.format("%.2f", Pension);
						txtPensione.setText(PensionPaid);
						txtPensione.setText("$" + PensionPaid);
						
						String StudentLoanPaid = String.format("%.2f", StudentLoad);
						txtStudentLoan.setText(StudentLoanPaid);
						txtStudentLoan.setText("$" + StudentLoanPaid);
						
						String NIPaymentPaid = String.format("%.2f", NIPayment);
						txtNIPayment.setText(NIPaymentPaid);
						txtNIPayment.setText("$" + NIPaymentPaid);
						
						TotalDeduction = A00 + Pension + StudentLoad + NIPayment;
						NetPay = Gross - TotalDeduction;
						NetPay = NetPay - A30;
						
						String TDeduction = String.format("%.2f", TotalDeduction);
						txtDeductions.setText(TDeduction);
						txtDeductions.setText("$" + TDeduction);
						
						String TNetPay = String.format("%.2f", NetPay);
						txtNetPay.setText(TNetPay);
						txtNetPay.setText("$" + TNetPay);
						
						String TaxPeriod = comBoxTaxPeriod.getSelectedItem().toString();
						TaxablePay = Double.parseDouble(TaxPeriod);
						PensionablePay = TaxablePay * Pension;
						TaxablePay = TaxablePay * A00;
						
						String PensionPayment = String.format("%.2f", PensionablePay);
						txtPensionablePay.setText(PensionPayment);
						txtPensionablePay.setText("$" + PensionPayment);
						
						String TaxablePayPayment = String.format("%.2f", TaxablePay);
						txtTaxablePay.setText(TaxablePayPayment);
						txtTaxablePay.setText("$" + TaxablePayPayment);
					}
					
					else if(comBoxNICode.getSelectedItem().equals("B4000")) {
						B40 = 4000/12;
						A00 = (Gross * 9)/100;
						deduction = Gross - A00;
						Pension = (deduction *12)/100;
						
						StudentLoad = Pension + A00;
						StudentLoad = Gross - StudentLoad;
						StudentLoad = (StudentLoad * 5.7)/100;
						
						NIPayment = StudentLoad + Pension + A00;
						NIPayment = Gross - NIPayment;
						NIPayment = (NIPayment * 4.2)/100;
						
						String TaxPaid = String.format("%.2f", A00);
						txtTax.setText(TaxPaid);
						txtTax.setText("$" + TaxPaid);
						
						String PensionPaid = String.format("%.2f", Pension);
						txtPensione.setText(PensionPaid);
						txtPensione.setText("$" + PensionPaid);
						
						String StudentLoanPaid = String.format("%.2f", StudentLoad);
						txtStudentLoan.setText(StudentLoanPaid);
						txtStudentLoan.setText("$" + StudentLoanPaid);
						
						String NIPaymentPaid = String.format("%.2f", NIPayment);
						txtNIPayment.setText(NIPaymentPaid);
						txtNIPayment.setText("$" + NIPaymentPaid);
						
						TotalDeduction = A00 + Pension + StudentLoad + NIPayment;
						NetPay = Gross - TotalDeduction;
						NetPay = NetPay - B40;
						
						String TDeduction = String.format("%.2f", TotalDeduction);
						txtDeductions.setText(TDeduction);
						txtDeductions.setText("$" + TDeduction);
						
						String TNetPay = String.format("%.2f", NetPay);
						txtNetPay.setText(TNetPay);
						txtNetPay.setText("$" + TNetPay);
						
						String TaxPeriod = comBoxTaxPeriod.getSelectedItem().toString();
						TaxablePay = Double.parseDouble(TaxPeriod);
						PensionablePay = TaxablePay * Pension;
						TaxablePay = TaxablePay * A00;
						
						String PensionPayment = String.format("%.2f", PensionablePay);
						txtPensionablePay.setText(PensionPayment);
						txtPensionablePay.setText("$" + PensionPayment);
						
						String TaxablePayPayment = String.format("%.2f", TaxablePay);
						txtTaxablePay.setText(TaxablePayPayment);
						txtTaxablePay.setText("$" + TaxablePayPayment);
					}
					else if(comBoxNICode.getSelectedItem().equals("C5000")) {
						C50 = 5000/12;
						A00 = (Gross * 9)/100;
						deduction = Gross - A00;
						Pension = (deduction *12)/100;
						
						StudentLoad = Pension + A00;
						StudentLoad = Gross - StudentLoad;
						StudentLoad = (StudentLoad * 5.7)/100;
						
						NIPayment = StudentLoad + Pension + A00;
						NIPayment = Gross - NIPayment;
						NIPayment = (NIPayment * 4.2)/100;
						
						String TaxPaid = String.format("%.2f", A00);
						txtTax.setText(TaxPaid);
						txtTax.setText("$" + TaxPaid);
						
						String PensionPaid = String.format("%.2f", Pension);
						txtPensione.setText(PensionPaid);
						txtPensione.setText("$" + PensionPaid);
						
						String StudentLoanPaid = String.format("%.2f", StudentLoad);
						txtStudentLoan.setText(StudentLoanPaid);
						txtStudentLoan.setText("$" + StudentLoanPaid);
						
						String NIPaymentPaid = String.format("%.2f", NIPayment);
						txtNIPayment.setText(NIPaymentPaid);
						txtNIPayment.setText("$" + NIPaymentPaid);
						
						TotalDeduction = A00 + Pension + StudentLoad + NIPayment;
						NetPay = Gross - TotalDeduction;
						NetPay = NetPay - C50;
						
						String TDeduction = String.format("%.2f", TotalDeduction);
						txtDeductions.setText(TDeduction);
						txtDeductions.setText("$" + TDeduction);
						
						String TNetPay = String.format("%.2f", NetPay);
						txtNetPay.setText(TNetPay);
						txtNetPay.setText("$" + TNetPay);
						
						String TaxPeriod = comBoxTaxPeriod.getSelectedItem().toString();
						TaxablePay = Double.parseDouble(TaxPeriod);
						PensionablePay = TaxablePay * Pension;
						TaxablePay = TaxablePay * A00;
						
						String PensionPayment = String.format("%.2f", PensionablePay);
						txtPensionablePay.setText(PensionPayment);
						txtPensionablePay.setText("$" + PensionPayment);
						
						String TaxablePayPayment = String.format("%.2f", TaxablePay);
						txtTaxablePay.setText(TaxablePayPayment);
						txtTaxablePay.setText("$" + TaxablePayPayment);
					}
					
					else if(comBoxNICode.getSelectedItem().equals("D6000")) {
						D60 = 6000/12;
						A00 = (Gross * 9)/100;
						deduction = Gross - A00;
						Pension = (deduction *12)/100;
						
						StudentLoad = Pension + A00;
						StudentLoad = Gross - StudentLoad;
						StudentLoad = (StudentLoad * 5.7)/100;
						
						NIPayment = StudentLoad + Pension + A00;
						NIPayment = Gross - NIPayment;
						NIPayment = (NIPayment * 4.2)/100;
						
						String TaxPaid = String.format("%.2f", A00);
						txtTax.setText(TaxPaid);
						txtTax.setText("$" + TaxPaid);
						
						String PensionPaid = String.format("%.2f", Pension);
						txtPensione.setText(PensionPaid);
						txtPensione.setText("$" + PensionPaid);
						
						String StudentLoanPaid = String.format("%.2f", StudentLoad);
						txtStudentLoan.setText(StudentLoanPaid);
						txtStudentLoan.setText("$" + StudentLoanPaid);
						
						String NIPaymentPaid = String.format("%.2f", NIPayment);
						txtNIPayment.setText(NIPaymentPaid);
						txtNIPayment.setText("$" + NIPaymentPaid);
						
						TotalDeduction = A00 + Pension + StudentLoad + NIPayment;
						NetPay = Gross - TotalDeduction;
						NetPay = NetPay - D60;
						
						String TDeduction = String.format("%.2f", TotalDeduction);
						txtDeductions.setText(TDeduction);
						txtDeductions.setText("$" + TDeduction);
						
						String TNetPay = String.format("%.2f", NetPay);
						txtNetPay.setText(TNetPay);
						txtNetPay.setText("$" + TNetPay);
						
						String TaxPeriod = comBoxTaxPeriod.getSelectedItem().toString();
						TaxablePay = Double.parseDouble(TaxPeriod);
						PensionablePay = TaxablePay * Pension;
						TaxablePay = TaxablePay * A00;
						
						String PensionPayment = String.format("%.2f", PensionablePay);
						txtPensionablePay.setText(PensionPayment);
						txtPensionablePay.setText("$" + PensionPayment);
						
						String TaxablePayPayment = String.format("%.2f", TaxablePay);
						txtTaxablePay.setText(TaxablePayPayment);
						txtTaxablePay.setText("$" + TaxablePayPayment);
					}
					else if(comBoxNICode.getSelectedItem().equals("E7000")) {
						E70 = 7000/12;
						A00 = (Gross * 9)/100;
						deduction = Gross - A00;
						Pension = (deduction *12)/100;
						
						StudentLoad = Pension + A00;
						StudentLoad = Gross - StudentLoad;
						StudentLoad = (StudentLoad * 5.7)/100;
						
						NIPayment = StudentLoad + Pension + A00;
						NIPayment = Gross - NIPayment;
						NIPayment = (NIPayment * 4.2)/100;
						
						String TaxPaid = String.format("%.2f", A00);
						txtTax.setText(TaxPaid);
						txtTax.setText("$" + TaxPaid);
						
						String PensionPaid = String.format("%.2f", Pension);
						txtPensione.setText(PensionPaid);
						txtPensione.setText("$" + PensionPaid);
						
						String StudentLoanPaid = String.format("%.2f", StudentLoad);
						txtStudentLoan.setText(StudentLoanPaid);
						txtStudentLoan.setText("$" + StudentLoanPaid);
						
						String NIPaymentPaid = String.format("%.2f", NIPayment);
						txtNIPayment.setText(NIPaymentPaid);
						txtNIPayment.setText("$" + NIPaymentPaid);
						
						TotalDeduction = A00 + Pension + StudentLoad + NIPayment;
						NetPay = Gross - TotalDeduction;
						NetPay = NetPay - E70;
						
						String TDeduction = String.format("%.2f", TotalDeduction);
						txtDeductions.setText(TDeduction);
						txtDeductions.setText("$" + TDeduction);
						
						String TNetPay = String.format("%.2f", NetPay);
						txtNetPay.setText(TNetPay);
						txtNetPay.setText("$" + TNetPay);
						
						String TaxPeriod = comBoxTaxPeriod.getSelectedItem().toString();
						TaxablePay = Double.parseDouble(TaxPeriod);
						PensionablePay = TaxablePay * Pension;
						TaxablePay = TaxablePay * A00;
						
						String PensionPayment = String.format("%.2f", PensionablePay);
						txtPensionablePay.setText(PensionPayment);
						txtPensionablePay.setText("$" + PensionPayment);
						
						String TaxablePayPayment = String.format("%.2f", TaxablePay);
						txtTaxablePay.setText(TaxablePayPayment);
						txtTaxablePay.setText("$" + TaxablePayPayment);
				}
					else if(comBoxNICode.getSelectedItem().equals("F8000")) {
						F80 = 8000/12;
						A00 = (Gross * 9)/100;
						deduction = Gross - A00;
						Pension = (deduction *12)/100;
						
						StudentLoad = Pension + A00;
						StudentLoad = Gross - StudentLoad;
						StudentLoad = (StudentLoad * 5.7)/100;
						
						NIPayment = StudentLoad + Pension + A00;
						NIPayment = Gross - NIPayment;
						NIPayment = (NIPayment * 4.2)/100;
						
						String TaxPaid = String.format("%.2f", A00);
						txtTax.setText(TaxPaid);
						txtTax.setText("$" + TaxPaid);
						
						String PensionPaid = String.format("%.2f", Pension);
						txtPensione.setText(PensionPaid);
						txtPensione.setText("$" + PensionPaid);
						
						String StudentLoanPaid = String.format("%.2f", StudentLoad);
						txtStudentLoan.setText(StudentLoanPaid);
						txtStudentLoan.setText("$" + StudentLoanPaid);
						
						String NIPaymentPaid = String.format("%.2f", NIPayment);
						txtNIPayment.setText(NIPaymentPaid);
						txtNIPayment.setText("$" + NIPaymentPaid);
						
						TotalDeduction = A00 + Pension + StudentLoad + NIPayment;
						NetPay = Gross - TotalDeduction;
						NetPay = NetPay - F80;
						
						String TDeduction = String.format("%.2f", TotalDeduction);
						txtDeductions.setText(TDeduction);
						txtDeductions.setText("$" + TDeduction);
						
						String TNetPay = String.format("%.2f", NetPay);
						txtNetPay.setText(TNetPay);
						txtNetPay.setText("$" + TNetPay);
						
						String TaxPeriod = comBoxTaxPeriod.getSelectedItem().toString();
						TaxablePay = Double.parseDouble(TaxPeriod);
						PensionablePay = TaxablePay * Pension;
						TaxablePay = TaxablePay * A00;
						
						String PensionPayment = String.format("%.2f", PensionablePay);
						txtPensionablePay.setText(PensionPayment);
						txtPensionablePay.setText("$" + PensionPayment);
						
						String TaxablePayPayment = String.format("%.2f", TaxablePay);
						txtTaxablePay.setText(TaxablePayPayment);
						txtTaxablePay.setText("$" + TaxablePayPayment);
				}
					else if(comBoxNICode.getSelectedItem().equals("G9000")) {
						G90 = 9000/12;
						A00 = (Gross * 9)/100;
						deduction = Gross - A00;
						Pension = (deduction *12)/100;
						
						StudentLoad = Pension + A00;
						StudentLoad = Gross - StudentLoad;
						StudentLoad = (StudentLoad * 5.7)/100;
						
						NIPayment = StudentLoad + Pension + A00;
						NIPayment = Gross - NIPayment;
						NIPayment = (NIPayment * 4.2)/100;
						
						String TaxPaid = String.format("%.2f", A00);
						txtTax.setText(TaxPaid);
						txtTax.setText("$" + TaxPaid);
						
						String PensionPaid = String.format("%.2f", Pension);
						txtPensione.setText(PensionPaid);
						txtPensione.setText("$" + PensionPaid);
						
						String StudentLoanPaid = String.format("%.2f", StudentLoad);
						txtStudentLoan.setText(StudentLoanPaid);
						txtStudentLoan.setText("$" + StudentLoanPaid);
						
						String NIPaymentPaid = String.format("%.2f", NIPayment);
						txtNIPayment.setText(NIPaymentPaid);
						txtNIPayment.setText("$" + NIPaymentPaid);
						
						TotalDeduction = A00 + Pension + StudentLoad + NIPayment;
						NetPay = Gross - TotalDeduction;
						NetPay = NetPay - G90;
						
						String TDeduction = String.format("%.2f", TotalDeduction);
						txtDeductions.setText(TDeduction);
						txtDeductions.setText("$" + TDeduction);
						
						String TNetPay = String.format("%.2f", NetPay);
						txtNetPay.setText(TNetPay);
						txtNetPay.setText("$" + TNetPay);
						
						String TaxPeriod = comBoxTaxPeriod.getSelectedItem().toString();
						TaxablePay = Double.parseDouble(TaxPeriod);
						PensionablePay = TaxablePay * Pension;
						TaxablePay = TaxablePay * A00;
						
						String PensionPayment = String.format("%.2f", PensionablePay);
						txtPensionablePay.setText(PensionPayment);
						txtPensionablePay.setText("$" + PensionPayment);
						
						String TaxablePayPayment = String.format("%.2f", TaxablePay);
						txtTaxablePay.setText(TaxablePayPayment);
						txtTaxablePay.setText("$" + TaxablePayPayment);
				}
					txtGrossPay.setText("$"+ GrossPay);
				}catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Enter a valid Number", "Payroll System",
							JOptionPane.INFORMATION_MESSAGE );
				}
				
				//===============================================================//
				//===============================================================//
				//===============================================================//
				
				// This was made to change after clicking the button NetWages into dollars in J TextFiled
				// Change for (InnerCity, BaicWage and OverTime) JText Filed chanching in dollars!
				
				String sInnerCity = String.format("$%.2f", InnerCity);
				txtInnerCity.setText(sInnerCity);
				
				String sBasicWage = String.format("$%.2f", BasicWage);
				txtBasicSalary.setText(sBasicWage);
				
				String sOverTime = String.format("$%.2f", OverTime);
				txtOverTime.setText(sOverTime);
				
				
			}
		});
		
		btnNetWages.setBounds(585, 412, 106, 23);
		CalP.add(btnNetWages);
		
		//-------This button Pay Slip showing the slip after fulfil all JtablesLines in CalP-----///
		JButton btnPaySlip = new JButton("Pay Slip");
		btnPaySlip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtfPaySlip.append("Empoyer's name: " + txtEmployersName.getText()+ "\n");
				rtfPaySlip.append("Emp.Ref: " + txtEmployeeRefNumber.getText() +"\n"+ "Date: " + txtPayDate.getText() + "\n");
				rtfPaySlip.append("Emp.Name: " + (String) comboBoxEmployeeName.getSelectedItem() +"\n"+ "Emp.Surname: "+ txtSurnameCal.getText() +"\n"+ "Tax Paid: " + txtTax.getText() + "\n");
				rtfPaySlip.append("\t==================\n");
				rtfPaySlip.append("Inner City: " + txtInnerCity.getText() +"\t" + "Student Loan: " + txtStudentLoan.getText() + "\n" );
				rtfPaySlip.append("Basic Salary: " + txtBasicSalary.getText() +"\t"+ "NI Payment: " + txtNIPayment.getText() + "\n");
				rtfPaySlip.append("Over Time: " + txtOverTime.getText() +"\t"+ "Pensionable Pay: " + txtPensionablePay.getText() + "\n");
				rtfPaySlip.append("\t==================\n");
				rtfPaySlip.append("Gross Pay: " + txtGrossPay.getText() +"\t"+ "Taxable Pay: " + txtTaxablePay.getText() + "\n");
				rtfPaySlip.append("Pension Paid: " + txtPensione.getText() +"\t"+ "Deductions: " + txtDeductions.getText() + "\n");
				rtfPaySlip.append("Net Pay: " + txtNetPay.getText() +"\t"+ "Tax Paid: " + comBoxNICode.getSelectedItem()+"\n");
			}
		});
		btnPaySlip.setBounds(701, 412, 89, 23);
		CalP.add(btnPaySlip);
		
		
		// This button for Reset was made to reset all filed inside J Text Field in JPanel Calculation!
		JButton btnNewButton_5 = new JButton("Reset");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtfPaySlip.setText(null);
				txtPayDate.setText(null);
				txtEmployersName.setText(null);
				txtSurnameCal.setText(null);
				txtEmployeeRefNumber.setText(null);
				txtEmployeeAdress.setText(null);
				txtPostCode.setText(null);
				txtInnerCity.setText(null);
				txtBasicSalary.setText(null);
				txtOverTime.setText(null);
				txtGrossPay.setText(null);
				txtGrossPay.setText(null);
				txtPensionablePay.setText(null);
				txtNetPay.setText(null);
				txtPayDate.setText(null);
				txtNINumber.setText(null);
				txtTax.setText(null);
				txtPensione.setText(null);
				txtStudentLoan.setText(null);
				txtNIPayment.setText(null);
				txtTaxablePay.setText(null);
				txtTaxablePay.setText(null);
				txtDeductions.setText(null);
				
				//-This was made after clicing Reset Button wil show agian the date and new ReferenNumber-//
				int refs = 1325 + (int)(Math.random()*4238);
				Calendar timer = Calendar.getInstance();
				timer.getTime();
				SimpleDateFormat tTime = new SimpleDateFormat("HH:mm:ss");
				tTime.format(timer.getTime());
				SimpleDateFormat Tdate = new SimpleDateFormat("dd-MMM-yyyy");
				Tdate.format(timer.getTime());
				txtPayDate.setText(Tdate.format(timer.getTime()));
				
				String sRef = String.format("EMPNO"+ refs);
				txtEmployeeRefNumber.setText(sRef);
				
				
			}
		});
		btnNewButton_5.setBounds(800, 412, 89, 23);
		CalP.add(btnNewButton_5);
		
		// This button was made to Exit from Calulation Salary!
		JButton btnExitCalculationSalary = new JButton("Exit");
		btnExitCalculationSalary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmHumanResourcesManagement = new JFrame ("Exit");
				if (JOptionPane.showConfirmDialog(frmHumanResourcesManagement,"Confirm if you want to exit!", "Employee data base system", 
						JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExitCalculationSalary.setBounds(899, 412, 89, 23);
		CalP.add(btnExitCalculationSalary);
		
		//---This button was made for PaySlip Print in Calculation Salary---//
		JButton btnPrintPaySlip = new JButton("Print Pay Slip");
		btnPrintPaySlip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageFormat header = new MessageFormat ("Printing in Progress");
				MessageFormat footer = new MessageFormat ("Page {0, number, integer}");
				
				try {
					rtfPaySlip.print();
				}catch(java.awt.print.PrinterException ev) {
					System.err.format("No Printer found", ev.getMessage());
				}
			}
		});
		btnPrintPaySlip.setBounds(585, 448, 115, 23);
		CalP.add(btnPrintPaySlip);
		
		JLabel lblNewLabel_3 = new JLabel("Employee Surname");
		lblNewLabel_3.setBounds(36, 131, 121, 13);
		CalP.add(lblNewLabel_3);
		
		/*
		 * 
		 * THIS JPanel IS SHOWING A CONTENT OF IDEmployeeCard *************
		 * (Jlabel to insert data of employees and generate IDEmployeeCard)!
		 * 
		 */
		JPanel IDEmployeeCard = new JPanel();
		frmHumanResourcesManagement.getContentPane().add(IDEmployeeCard, "test");
		IDEmployeeCard.setLayout(null);
		
		JButton btnNewButton_4 = new JButton("Back");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "main");
			}
		});
		btnNewButton_4.setBounds(976, 548, 89, 23);
		IDEmployeeCard.add(btnNewButton_4);
		
		JLabel lblIDNumberIDCard = new JLabel("ID Number");
		lblIDNumberIDCard.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblIDNumberIDCard.setBounds(28, 20, 89, 13);
		IDEmployeeCard.add(lblIDNumberIDCard);
		
		comboBoxIDNumberIDCard = new JComboBox();
		comboBoxIDNumberIDCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * *
				//This while loop fill the txtnameIDCard adn txtIDSurname with text from sqlitle when choosing iteam
				in ComboBoc
				**
				*/
				try {
					String sql ="select * from employee where EmployeeID=?";
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setString(1, (String)comboBoxIDNumberIDCard.getSelectedItem());
					
					ResultSet rs=pst.executeQuery();
					
					while(rs.next()){
		                txtnameIDCard.setText(rs.getString("Firstname"));
		                txtIDSurname.setText(rs.getString("Surname"));
		                
						//dateChooserBrithIDCard.setDate(rs.getDate("DateofBrith"));
		                //comboBoxGenderIDCard.addItem(rs.getString("Gender"));         
		            }	
					
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		comboBoxIDNumberIDCard.setBounds(138, 17, 182, 21);
		IDEmployeeCard.add(comboBoxIDNumberIDCard);
		
		JLabel lblCompanyIDCard = new JLabel();
		lblCompanyIDCard.setText("Company: ");
		lblCompanyIDCard.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCompanyIDCard.setBounds(28, 57, 79, 15);
		IDEmployeeCard.add(lblCompanyIDCard);
		
		txtCompanyIDCard = new JTextField();
		txtCompanyIDCard.setBounds(138, 56, 182, 19);
		IDEmployeeCard.add(txtCompanyIDCard);
		
		JLabel lblNameIDCard = new JLabel();
		lblNameIDCard.setText("Name:");
		lblNameIDCard.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNameIDCard.setBounds(28, 96, 79, 15);
		IDEmployeeCard.add(lblNameIDCard);
		
		txtnameIDCard = new JTextField();
		txtnameIDCard.setBounds(137, 95, 183, 19);
		IDEmployeeCard.add(txtnameIDCard);
		
		JLabel lblIDSurname = new JLabel();
		lblIDSurname.setText("Surname:");
		lblIDSurname.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblIDSurname.setBounds(28, 131, 89, 15);
		IDEmployeeCard.add(lblIDSurname);
		
		JLabel lblGenderIDCard = new JLabel();
		lblGenderIDCard.setText("Gender: ");
		lblGenderIDCard.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGenderIDCard.setBounds(28, 168, 89, 15);
		IDEmployeeCard.add(lblGenderIDCard);
		
		JComboBox<String> comboBoxGenderIDCard = new JComboBox<String>();
		comboBoxGenderIDCard.setEditable(true);
		comboBoxGenderIDCard.addItem("Select");
		comboBoxGenderIDCard.addItem("Male");
		comboBoxGenderIDCard.addItem("Female");
		comboBoxGenderIDCard.setBounds(138, 167, 182, 19);
		IDEmployeeCard.add(comboBoxGenderIDCard);
		
		txtIDSurname = new JTextField();
		txtIDSurname.setBounds(137, 130, 183, 19);
		IDEmployeeCard.add(txtIDSurname);
		
		JLabel lblDateOfBrithIDCard = new JLabel();
		lblDateOfBrithIDCard.setText("Date of Brith: ");
		lblDateOfBrithIDCard.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDateOfBrithIDCard.setBounds(28, 208, 89, 15);
		IDEmployeeCard.add(lblDateOfBrithIDCard);
		
		JDateChooser dateChooserBrithIDCard = new JDateChooser();
		dateChooserBrithIDCard.setDateFormatString("dd.MM.yyyy");
		dateChooserBrithIDCard.setBounds(138, 208, 182, 19);
		IDEmployeeCard.add(dateChooserBrithIDCard);
		
		JLabel lblProfessionIDCard = new JLabel();
		lblProfessionIDCard.setText("Profession:");
		lblProfessionIDCard.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProfessionIDCard.setBounds(28, 256, 89, 15);
		IDEmployeeCard.add(lblProfessionIDCard);
		
		JComboBox<String> comboBoxProfessionIDcard = new JComboBox<String>();
		comboBoxProfessionIDcard.setBounds(138, 254, 182, 21);
		comboBoxProfessionIDcard.addItem("Select");
		comboBoxProfessionIDcard.addItem("Manager");
		comboBoxProfessionIDcard.addItem("Financial Managers");
		comboBoxProfessionIDcard.addItem("Accountant");
		comboBoxProfessionIDcard.addItem("Economists");
		comboBoxProfessionIDcard.addItem("Administrator");
		comboBoxProfessionIDcard.addItem("Engineer");
		comboBoxProfessionIDcard.addItem("Mechanic");
		comboBoxProfessionIDcard.addItem("Electrician");
		comboBoxProfessionIDcard.addItem("Scientist");
		comboBoxProfessionIDcard.addItem("Developer");
		comboBoxProfessionIDcard.addItem("Receptionist");
		comboBoxProfessionIDcard.addItem("Security guard");
		comboBoxProfessionIDcard.addItem("Worker");
		comboBoxProfessionIDcard.addItem("Cleaner");
		comboBoxProfessionIDcard.setEditable(true);
		IDEmployeeCard.add(comboBoxProfessionIDcard);
		
		JLabel lblAddressIDCard = new JLabel();
		lblAddressIDCard.setText("Address: ");
		lblAddressIDCard.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAddressIDCard.setBounds(28, 297, 89, 15);
		IDEmployeeCard.add(lblAddressIDCard);
		
		JTextArea txtadressIDCard = new JTextArea();
		txtadressIDCard.setRows(5);
		txtadressIDCard.setColumns(20);
		txtadressIDCard.setBounds(138, 298, 182, 64);
		IDEmployeeCard.add(txtadressIDCard);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(255, 239, 213));
		panel_1.setBounds(495, 59, 570, 322);
		IDEmployeeCard.add(panel_1);
		
		JLabel lblNewLabel_IDEmblame = new JLabel((Icon) null);
		lblNewLabel_IDEmblame.setBounds(0, 0, 94, 87);
		panel_1.add(lblNewLabel_IDEmblame);
		
		JLabel lblNewLabel_3_1Name = new JLabel("Name:");
		lblNewLabel_3_1Name.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_3_1Name.setBounds(104, 51, 43, 18);
		panel_1.add(lblNewLabel_3_1Name);
		
		JLabel lblNewLabel_3_1_1Surname = new JLabel("Surname:");
		lblNewLabel_3_1_1Surname.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_3_1_1Surname.setBounds(104, 80, 90, 18);
		panel_1.add(lblNewLabel_3_1_1Surname);
		
		JLabel lblNewLabel_3_2Profession = new JLabel("Profession:");
		lblNewLabel_3_2Profession.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_3_2Profession.setBounds(104, 167, 90, 18);
		panel_1.add(lblNewLabel_3_2Profession);
		
		JLabel lblNewLabel_3_3DateBrith = new JLabel("Date of birth:");
		lblNewLabel_3_3DateBrith.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_3_3DateBrith.setBounds(104, 139, 87, 18);
		panel_1.add(lblNewLabel_3_3DateBrith);
		
		JLabel lblNewLabel_3_4Gender = new JLabel("Gender:");
		lblNewLabel_3_4Gender.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_3_4Gender.setBounds(104, 109, 89, 18);
		panel_1.add(lblNewLabel_3_4Gender);
		
		JLabel lblNewLabelDImage = new JLabel();;
		lblNewLabelDImage.setBounds(391, 51, 101, 127);
		panel_1.add(lblNewLabelDImage);
		
		JLabel lblNewLabel_3_6Adress = new JLabel("Address:");
		lblNewLabel_3_6Adress.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_3_6Adress.setBounds(104, 195, 60, 18);
		panel_1.add(lblNewLabel_3_6Adress);
		
		JLabel lblNewLabel_4_1IDNumber = new JLabel("ID:");
		lblNewLabel_4_1IDNumber.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_4_1IDNumber.setBounds(335, 22, 22, 18);
		panel_1.add(lblNewLabel_4_1IDNumber);
		
		JLabel lblNewLabel_3_7Name = new JLabel("");
		lblNewLabel_3_7Name.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_3_7Name.setBounds(202, 50, 130, 18);
		panel_1.add(lblNewLabel_3_7Name);
		
		JLabel lblNewLabel_3_7_1Surname = new JLabel((String) null);
		lblNewLabel_3_7_1Surname.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_3_7_1Surname.setBounds(202, 79, 136, 18);
		panel_1.add(lblNewLabel_3_7_1Surname);
		
		JLabel lblNewLabel_3_7_2DateBrith = new JLabel((String) null);
		lblNewLabel_3_7_2DateBrith.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_3_7_2DateBrith.setBounds(202, 139, 130, 18);
		panel_1.add(lblNewLabel_3_7_2DateBrith);
		
		JLabel lblNewLabel_3_7_3Profession = new JLabel((String) null);
		lblNewLabel_3_7_3Profession.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_3_7_3Profession.setBounds(201, 168, 156, 18);
		panel_1.add(lblNewLabel_3_7_3Profession);
		
		JLabel lblNewLabel_3_7_4Gender = new JLabel((String) null);
		lblNewLabel_3_7_4Gender.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_3_7_4Gender.setBounds(203, 108, 119, 18);
		panel_1.add(lblNewLabel_3_7_4Gender);
		
		JLabel lblNewLabel_3_7_7IDNumber = new JLabel((String) null);
		lblNewLabel_3_7_7IDNumber.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblNewLabel_3_7_7IDNumber.setBounds(359, 21, 158, 18);
		panel_1.add(lblNewLabel_3_7_7IDNumber);
		
		JLabel lblNewLabel_5Signature = new JLabel("Signature:");
		lblNewLabel_5Signature.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_5Signature.setBounds(398, 252, 94, 18);
		panel_1.add(lblNewLabel_5Signature);
		
		JLabel lblNewLabel_6Compaanyname = new JLabel("          ABC COMPANY");
		lblNewLabel_6Compaanyname.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_6Compaanyname.setForeground(new Color(250, 128, 114));
		lblNewLabel_6Compaanyname.setFont(new Font("Century Gothic", Font.BOLD, 19));
		lblNewLabel_6Compaanyname.setBounds(104, 11, 202, 29);
		panel_1.add(lblNewLabel_6Compaanyname);
		
		JTextArea textArea_1IDAdress = new JTextArea();
		textArea_1IDAdress.setFont(new Font("Verdana", Font.PLAIN, 14));
		textArea_1IDAdress.setBackground(new Color(255, 239, 213));
		textArea_1IDAdress.setBounds(202, 198, 189, 72);
		panel_1.add(textArea_1IDAdress);
		
		JButton jButtonSetImage = new JButton();
		jButtonSetImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("Files",ImageIO.getReaderFileSuffixes());
				chooser.setFileFilter(filter);
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				 
				
				try {
					BufferedImage img = null;
					img = ImageIO.read(new File(f.getAbsolutePath()));
					Image img1 = img.getScaledInstance(lblNewLabelDImage.getWidth(), lblNewLabelDImage.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon format = new ImageIcon(img1);
					lblNewLabelDImage.setIcon(format);
					
					String filename = f.getAbsolutePath();
					textField_PathForImage.setText(filename);
					
					
				}catch(Exception e1) {
					//JOptionPane.showMessageDialog(null, e1,"Error", JOptionPane.ERROR_MESSAGE);
					
				}	
				
			}
		});
		jButtonSetImage.setText("Set Image");
		jButtonSetImage.setFont(new Font("Tahoma", Font.BOLD, 10));
		jButtonSetImage.setBounds(337, 392, 118, 29);
		IDEmployeeCard.add(jButtonSetImage);
		
		JButton jButtonGenerateID = new JButton();
		jButtonGenerateID.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String cou = txtCompanyIDCard.getText();
				String IDNumber = comboBoxIDNumberIDCard.getSelectedItem().toString();
				String name = txtnameIDCard.getText();
				String surname = txtIDSurname.getText();
				String gender = comboBoxGenderIDCard.getSelectedItem().toString();
				String dateBrith=((JTextField)dateChooserBrithIDCard.getDateEditor().getUiComponent()).getText();
				String profession = comboBoxProfessionIDcard.getSelectedItem().toString();
				String adress = txtadressIDCard.getText();
				
				
				lblNewLabel_6Compaanyname.setText(cou);
				lblNewLabel_3_7_7IDNumber.setText(IDNumber);
				lblNewLabel_3_7Name.setText(name);
				lblNewLabel_3_7_1Surname.setText(surname);
				lblNewLabel_3_7_4Gender.setText(gender);
				lblNewLabel_3_7_2DateBrith.setText(dateBrith);
				lblNewLabel_3_7_3Profession.setText(profession);
				textArea_1IDAdress.setText(adress);
			}
		});
		jButtonGenerateID.setText("Genarate ID");
		jButtonGenerateID.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 14));
		jButtonGenerateID.setBounds(28, 528, 133, 43);
		IDEmployeeCard.add(jButtonGenerateID);
		
		JButton jButtonPrintID = new JButton();
		jButtonPrintID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printRecord(panel_1);
			}
		});
		jButtonPrintID.setText("Print");
		jButtonPrintID.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 14));
		jButtonPrintID.setBounds(192, 528, 128, 43);
		IDEmployeeCard.add(jButtonPrintID);
		
		textField_PathForImage = new JTextField();
		textField_PathForImage.setEditable(false);
		textField_PathForImage.setBounds(28, 397, 292, 19);
		IDEmployeeCard.add(textField_PathForImage);
		textField_PathForImage.setColumns(10);
		
		textField_PathForEmblame = new JTextField();
		textField_PathForEmblame.setEditable(false);
		textField_PathForEmblame.setColumns(10);
		textField_PathForEmblame.setBounds(28, 444, 292, 19);
		IDEmployeeCard.add(textField_PathForEmblame);
		
		JButton jButtonSetEmblame = new JButton();
		jButtonSetEmblame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("Files",ImageIO.getReaderFileSuffixes());
				chooser.setFileFilter(filter);
				chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				try {
					BufferedImage img = null;
					img = ImageIO.read(new File(f.getAbsolutePath()));
					Image img1 = img.getScaledInstance(lblNewLabel_IDEmblame.getWidth(), lblNewLabel_IDEmblame.getHeight(), Image.SCALE_SMOOTH);
					ImageIcon format = new ImageIcon(img1);
					lblNewLabel_IDEmblame.setIcon(format);
					
					String filename = f.getAbsolutePath();
					textField_PathForEmblame.setText(filename);
					
				}catch(Exception e1) {
					//JOptionPane.showMessageDialog(null, e1,"Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		jButtonSetEmblame.setText("Set Emblame");
		jButtonSetEmblame.setFont(new Font("Tahoma", Font.BOLD, 10));
		jButtonSetEmblame.setBounds(337, 439, 118, 29);
		IDEmployeeCard.add(jButtonSetEmblame);
		
		JButton jButtonResetIDCARD = new JButton("Reset Fields");
		jButtonResetIDCARD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCompanyIDCard.setText(null);
				txtnameIDCard.setText(null);
				txtIDSurname.setText(null);
				
				//comboBoxIDNumberIDCard.setSelectedIndex(0);
				comboBoxGenderIDCard.setSelectedIndex(0);
				dateChooserBrithIDCard.setDate(null);
				comboBoxProfessionIDcard.setSelectedIndex(0);
				txtadressIDCard.setText(null);
				
				textField_PathForImage.setText(null);
				textField_PathForEmblame.setText(null);
				
				lblNewLabel_3_7_7IDNumber.setText(null);
				lblNewLabel_3_7Name.setText(null);
				lblNewLabel_3_7_1Surname.setText(null);
				lblNewLabel_3_7_4Gender.setText(null);
				lblNewLabel_3_7_2DateBrith.setText(null);
				lblNewLabel_3_7_3Profession.setText(null);
				textArea_1IDAdress.setText(null);
				
				lblNewLabelDImage.setIcon(null);
				lblNewLabel_IDEmblame.setIcon(null);
				
			}
		});
		jButtonResetIDCARD.setFont(new Font("Tahoma", Font.BOLD, 10));
		jButtonResetIDCARD.setBounds(337, 486, 118, 29);
		IDEmployeeCard.add(jButtonResetIDCARD);
		
			
		/*
		 * 
		 * THIS JPanel IS SHOWING A CONTENT OF HRMangement (JTable conected with DataBase SqLite, 
		 * different buttons (Add New, Print, Reset, Exit), Jlabel to insert data of employees)! 
		 * 
		 */
		JPanel HRManagement = new JPanel();
		frmHumanResourcesManagement.getContentPane().add(HRManagement, "management");
		HRManagement.setLayout(null);
		
		// This is a Button for adding new text into column ADD NEW data in JTable!
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO employee(EmployeeID,InsuranceNumber,Firstname,Surname,Gender,DateofBrith,Age,Salary)VALUES(?,?,?,?,?,?,?,?)";
				try
				{
					//***
					// This is for adding text in rows in JTable!
					//**
					
					pst = conn.prepareStatement(sql);
					pst.setString(1, jtxtEmployeeID.getText());
					pst.setString(2, jtxtInsuranceNumber.getText());
					pst.setString(3, jtxtFirstName.getText());
					pst.setString(4, jtxtSurname.getText());
					pst.setString(5, jtxtGender.getText());
					pst.setString(6, jtxtDateofBrith.getText());
					pst.setString(7, jtxtAge.getText());
					pst.setString(8, jtxtSalary.getText());
					
					pst.execute();
					
					rs.close();
					pst.close();
				
				}
				catch(Exception ev)
				{
					JOptionPane.showMessageDialog(null, "System Upadte Completed");
				}
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {
						
					jtxtEmployeeID.getText(),
					jtxtInsuranceNumber.getText(),
					jtxtFirstName.getText(),
					jtxtSurname.getText(),
					jtxtGender.getText(),
					jtxtDateofBrith.getText(),
					jtxtAge.getText(),
					jtxtSalary.getText(),	
				
			});
				
			if (table.getSelectedRow() == -1) {
				if (table.getRowCount() == 0) {
				JOptionPane.showMessageDialog(null, "Mebership Update confirmed", "Employee Database System",
						JOptionPane.OK_OPTION);
						}
					}
			 		//updateTable();
			}
		});
		btnAddNew.setBounds(40, 440, 89, 23);
		HRManagement.add(btnAddNew);
		
		// This is a button for PRINT!
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// This actionPerformed was made to print JTable text!
				
				MessageFormat header = new MessageFormat ("Printing in Progress");
				MessageFormat footer = new MessageFormat ("Page {0, number, integer}");
				
				try {
					table.print();
				}catch(java.awt.print.PrinterException ev) {
					System.err.format("No Printer found", ev.getMessage());
				}
			}
		});
		btnPrint.setBounds(337, 440, 89, 23);
		HRManagement.add(btnPrint);
		
		// This is a button for RESET!
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// This actionPerformed was made to SetText on null! Reset all was writen inside jtxt!

					jtxtEmployeeID.setText(null);
					jtxtInsuranceNumber.setText(null);
					jtxtFirstName.setText(null);
					jtxtSurname.setText(null);
					jtxtGender.setText(null);
					jtxtDateofBrith.setText(null);
					jtxtAge.setText(null);
					jtxtSalary.setText(null);
					
				}
			});
			
		
		btnReset.setBounds(139, 440, 89, 23);
		HRManagement.add(btnReset);
		
		//This is a button made for EXIT (in JPanel HRManagement)!
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				/*
				 * This new JFrame was made for opening new window for confirmation of Exit!
				 */
				
				frmHumanResourcesManagement = new JFrame ("Exit");
				if (JOptionPane.showConfirmDialog(frmHumanResourcesManagement,"Confirm if you want to exit!", "Employee data base system", 
						JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setBounds(139, 499, 89, 23);
		HRManagement.add(btnExit);
		
		// This is a JLabel for text Employee ID and JTEXTFIELD for entering data of EmployeeID!
		
		JLabel lblEmployeeID = new JLabel("Employee ID");
		lblEmployeeID.setBounds(40, 82, 82, 14);
		HRManagement.add(lblEmployeeID);
		
		jtxtEmployeeID = new JTextField();
		jtxtEmployeeID.setColumns(10);
		jtxtEmployeeID.setBounds(184, 76, 125, 20);
		HRManagement.add(jtxtEmployeeID);
		
		// This is a Label for text INSURANCE NUMBER and JTEXTFIELD for entering data of Insurance!
		
		JLabel lblInsuranceNumber = new JLabel("Insurance Number");
		lblInsuranceNumber.setBounds(40, 113, 114, 14);
		HRManagement.add(lblInsuranceNumber);
		
		jtxtInsuranceNumber = new JTextField();
		jtxtInsuranceNumber.setColumns(10);
		jtxtInsuranceNumber.setBounds(184, 107, 125, 20);
		HRManagement.add(jtxtInsuranceNumber);
		
		// This is a Label for text FirstName and JTEXTFIELD for entering data of FirstName!
		
		JLabel lblFirstname = new JLabel("Firstname");
		lblFirstname.setBounds(40, 144, 82, 14);
		HRManagement.add(lblFirstname);
		
		jtxtFirstName = new JTextField();
		jtxtFirstName.setColumns(10);
		jtxtFirstName.setBounds(184, 138, 125, 20);
		HRManagement.add(jtxtFirstName);
		
		// This is a Label for text Surname and JTEXTFIELD for entering data of Surname!
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(40, 172, 82, 14);
		HRManagement.add(lblSurname);
		
		jtxtSurname = new JTextField();
		jtxtSurname.setColumns(10);
		jtxtSurname.setBounds(184, 166, 125, 20);
		HRManagement.add(jtxtSurname);
		
		// This is a Label for text Gender and JTEXTFIELD for entering data of Gender!
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(40, 200, 82, 14);
		HRManagement.add(lblGender);
		
		jtxtGender = new JTextField();
		jtxtGender.setColumns(10);
		jtxtGender.setBounds(184, 194, 125, 20);
		HRManagement.add(jtxtGender);
		
		// This is a Label for text Date of Brith and JTEXTFIELD for entering data of Date of Brith!
		
		JLabel lblDateOfBrith = new JLabel("Date of Brith");
		lblDateOfBrith.setBounds(40, 228, 82, 14);
		HRManagement.add(lblDateOfBrith);
		
		jtxtDateofBrith = new JTextField();
		jtxtDateofBrith.setColumns(10);
		jtxtDateofBrith.setBounds(184, 222, 125, 20);
		HRManagement.add(jtxtDateofBrith);
		
		// This is a Label for text Age and JTEXTFIELD for entering data of Age!
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setBounds(40, 256, 82, 14);
		HRManagement.add(lblAge);
		
		jtxtAge = new JTextField();
		jtxtAge.setColumns(10);
		jtxtAge.setBounds(184, 250, 125, 20);
		HRManagement.add(jtxtAge);
		
		// This is a Label for text Salary and JTEXTFIELD for entering data of Salary!
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setBounds(40, 287, 82, 14);
		HRManagement.add(lblSalary);
		
		jtxtSalary = new JTextField();
		jtxtSalary.setColumns(10);
		jtxtSalary.setBounds(184, 281, 125, 20);
		HRManagement.add(jtxtSalary);
		
		// This is a JTABLE surrounded with JScrollPane for showing all data which were put into database for employee of the company!
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(337, 43, 708, 363);
		HRManagement.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"EmployeeID", "Insurance Number", "Firstname", "Surname", "Gender", "Date of Brith", "Age", "Salary"
			}
		));
		table.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane.setViewportView(table);	    
		
		//---This button was made to get back in Main Menu--//
		JButton btnNewButton_3_1 = new JButton("Back");
		btnNewButton_3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myLayout.show(frmHumanResourcesManagement.getContentPane(), "main");
			}
		});
		btnNewButton_3_1.setBounds(40, 499, 89, 23);
		HRManagement.add(btnNewButton_3_1);
		
		// -- This button was made to deleted rows in JTable - HR MANAGEMENT --//
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel tblModel = (DefaultTableModel) table.getModel();
				if(table.getSelectedRowCount() == 1) {
		               // remove selected row from the table //
					String selectedID = (String) tblModel.getValueAt(table.getSelectedRow(),0);
					String sql = "DELETE FROM employee WHERE EmployeeID=?";
					try {
						pst = conn.prepareStatement(sql);
						pst.setString(1, selectedID);
						pst.executeUpdate();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					//(System.out.print(sql);
					   tblModel.removeRow(table.getSelectedRow());
					   
		               JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
		            }else {
		            	if(table.getRowCount()==0) {
		            		// if table is empty (no data in the table) //
		            		JOptionPane.showMessageDialog(null, "Table is Empty");
		            	}else {
		            		// if table is not empty but row is not selected or multiple selected //
		            		JOptionPane.showMessageDialog(null, "Please selected a single Row for Delete");
		            	}
		            }
			}
		});
		btnDelete.setBounds(238, 440, 89, 23);
		HRManagement.add(btnDelete);
	}
}

