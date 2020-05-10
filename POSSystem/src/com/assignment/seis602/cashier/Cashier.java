package com.assignment.seis602.cashier;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Cashier implements IfcCashier {



	 private String username;
	 private String password;
	 private String employeeID;

	public Cashier(String username, String password, String employeeID) {
		this.username = username;
		this.password = password;
		this.setEmployeeID(employeeID);
	}
	
	public Cashier() {
		username = "";
		password = "";
		employeeID = "";
	}


	public static Cashier verifyLogin(String username, String password) throws IOException{
			
			//assign text file to path and read file from scanner
			String filepath = "resources/login.txt";
			Path path = Paths.get(filepath);
			Scanner scanner = new Scanner(path);
			
			//create current date and time 
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			//if pass or username not found return false
			boolean found = false;
			
			//Set default values
			String employeeName1 = "";
			String employeePass1 = "";
			String employeeID1 = "";

			
			String employeeName2 = "";
			String employeePass2 = "";
			String employeeID2 = "";
			
			String employeeName3 = " ";
			String employeePass3 = " ";
			String employeeID3 = "";
			
			String employeeName4 = "";
			String employeePass4 = "";
			String employeeID4 = "";
			
			try {
				//read line by line
				while(scanner.hasNext() && !found) {
					
				    //assign line of text (employee name) to variable 
					employeeName1 = scanner.next();
					
				    //assign line of text (employee password) to variable 
					employeePass1 = scanner.next();
					
					employeeID1 = scanner.next();
					
				    //assign line of text (employee name) to variable 
					employeeName2 = scanner.next();
					employeePass2 = scanner.next();
					employeeID2 = scanner.next();

				    //assign line of text (employee name) to variable 
					employeeName3 = scanner.next();
					employeePass3 = scanner.next();
					employeeID3 = scanner.next();
					
					 employeeName4 = scanner.next();
					 employeePass4 = scanner.next();
					 employeeID4 = scanner.next();

				 	//remove spaces and see if the username and password is the same entered
						if(employeeName1.equals(username) && employeePass1.equals(password)){
							
							found = true;
							
							//Print true login is success and log time.
							 System.out.println("Login successful at " + dtf.format(now));
							
							 Cashier employeeOne = new Cashier(employeeName1, employeePass1, employeeID1);
							 
							return employeeOne;
							
						}
						else if(employeeName2.equals(username) && employeePass2.equals(password)){
							
							//create employeeTwo object
							Cashier employeeTwo = new Cashier(employeeName2, employeePass2, employeeID2);
							found = true;
							
							//Print true login is success and log time.
							System.out.println("Login successful at " + dtf.format(now));
							
							//return employee object
							return employeeTwo;
						}
								
						else if(employeeName3.equals(username) && employeePass3.equals(password)) {
							
							Cashier employeeThree = new Cashier(employeeName3, employeePass3, employeeID3);
							found = true;
							
							//Print true login is success and log time.
							System.out.println("Login successful at " + dtf.format(now));
							
							
							return employeeThree;
						}
							
						else if(employeeName4.equals(username) && employeePass4.equals(password)) {
							
							Cashier employeeFour = new Cashier(employeeName4, employeePass4, employeeID4);
							found = true;
							
							System.out.println("Login successful at " + dtf.format(now));
							
							return employeeFour;
						}
							
							
						else 
							// if found is false, login is unsuccessful
							System.out.println("Login unsuccessful");
						
							//close text file
							scanner.close();
						
					}
			}
				catch (Exception e) {
				}
			
			return null;
			}
	 

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the employeeID
	 */
	public String getEmployeeID() {
		return employeeID;
	}


	/**
	 * @param employeeID the employeeID to set
	 */
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	
	public String toString() {
		String text = "";
		text += employeeID + " " + username  ;
	
		return text;
	}

}

