import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.*;
import javax.swing.*;

import javax.swing.JOptionPane;

public class EmployeeData {
	public static Connection ConnectDB()
	{
		
		try
		{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection
					("jdbc:sqlite:employee.db" );
					//C:\\Users\\hn\\Desktop\\Project\\Programiranje\\Eclipse\\HRResources
					//JOptionPane.showMessageDialog(null, "Connection Made");
					return conn;
			}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Connection Error");
			
			return null;
			}
		}
	}

