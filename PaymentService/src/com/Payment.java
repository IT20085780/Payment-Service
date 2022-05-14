package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



//data base connection

public class Payment {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/powereg", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//insert

	public String insertPayment( String HolderName, String Type, String CardNo, String CVV, String Month, String Year, String Total, String Date) { 
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for inserting."; } 
	 
			// create a prepared statement 
			String query = " insert into Payment (`pay_id`, `holder_name`,`ctype`,`card_no`,`cvv`,`exp_month`,`exp_year`,`total`, `pay_date`)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
	 
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, HolderName); 
			preparedStmt.setString(3, Type); 
			preparedStmt.setString(4, CardNo); 
			preparedStmt.setString(5, CVV); 
			preparedStmt.setString(6, Month); 
			preparedStmt.setString(7, Year); 
			preparedStmt.setString(8, Total);
			preparedStmt.setString(9, Date);
	 
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newPayment = readPayment(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Innovator.\"}";  
			System.err.println(e.getMessage());   
		} 
		
	  return output;  
	    } 
	}
	
	