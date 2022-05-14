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
	
	//read
	
	public String readPayment()  
	{   
		String output = ""; 
	
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border='1'> <tr>"
					+ "<th>Holder Name</th>"
					+ "<th>Card Type</th>"
					+ "<th>Card No</th>"
					+ "<th>CVV</th>"
					+ "<th>Exp_Month</th>"
					+ "<th>Exp_Year</th>"
					+ "<th>Total</th>"
					+ "<th>Paid Date</th> <th>Update</th> <th>Delete</th> </tr>"; 
			
			String query = "select * from Payment"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				String pay_id = Integer.toString(rs.getInt("pay_id")); 
				String holder_name = rs.getString("holder_name"); 
				String ctype = rs.getString("ctype"); 
				String card_no = rs.getString("card_no"); 
				String cvv = rs.getString("cvv"); 
				String exp_month = rs.getString("exp_month"); 
				String exp_year = rs.getString("exp_year"); 
				String total = rs.getString("total"); 
				String pay_date = rs.getString("pay_date"); 
		 
			
			
	 
				// Add into the html table 
				
				output += "<tr><td><input id=\'hidpay_idUpdate\' name=\'hidpay_idUpdate\' type=\'hidden\' value=\'" + pay_id + "'>"
				            + holder_name + "</td>"; 
				output += "<td>" + ctype + "</td>";
				output += "<td>" + card_no + "</td>";
				output += "<td>" + cvv + "</td>";
				output += "<td>" + exp_month + "</td>";
				output += "<td>" + exp_year + "</td>";
				output += "<td>" + total + "</td>";
				output += "<td>" + pay_date + "</td>";
				
//				output += "<tr><td>" + pay_id + "</td>";
//				output += "<td>" + holder_name + "</td>";
//				output += "<td>" + ctype + "</td>"; 
//				output += "<td>" + card_no + "</td>"; 
//				output += "<td>" + cvv + "</td>"; 
//				output += "<td>" + exp_month + "</td>"; 
//				output += "<td>" + exp_year + "</td>"; 
//				output += "<td>" + total + "</td>"; 
//				output += "<td>" + pay_date + "</td>"; 
				

			  
 
			// buttons     
			output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
					+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-iid='" + pay_id + "'>" + "</td></tr>"; 
		 		 
			}
			con.close(); 
	 
			// Complete the html table    
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the Payment.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
	//update
	
	public String updatePayment(String PaymentID, String HolderName, String Type, String CardNo, String CVV, String Month, String Year, String Total, String Date) { 
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{return "Error while connecting to the database for updating."; } 
	 
			// create a prepared statement    
			 String query = "UPDATE Payment SET holder_name=?, ctype=?, card_no=?, cvv=?, exp_month=?, exp_year=?, total=?, pay_date=? WHERE pay_id=?";
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			 preparedStmt.setString(1, HolderName); 
			 preparedStmt.setString(2, Type); 
			 preparedStmt.setString(3, CardNo); 
			 preparedStmt.setString(4, CVV); 
			 preparedStmt.setString(5, Month); 
			 preparedStmt.setString(6, Year); 
			 preparedStmt.setString(7, Total);
			 preparedStmt.setString(8, Date);
			 preparedStmt.setInt(9, Integer.parseInt(PaymentID));
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newPayment = readPayment();    
			output = "{\"status\":\"success\", \"data\": \"" + newPayment + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}";   
			System.err.println(e.getMessage());   
		} 
	 
	  return output;  
	    } 
	}
	
	//delete
	
	public String deletePayment(String pay_id)   
	{   
		String output = ""; 
	 
		try   
		{    
			Connection con = connect(); 
	 
			if (con == null)    
			{
				return "Error while connecting to the database for deleting."; 
				
			} 
	 
			// create a prepared statement    
			String query = "delete from payment where pay_id=?";  
	 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values    
			preparedStmt.setInt(1, Integer.parseInt(pay_id)); 
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newPayment = readPayment();  
			    
			output = "{\"status\":\"success\", \"data\": \"" +  newPayment + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the Payment.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	
}
