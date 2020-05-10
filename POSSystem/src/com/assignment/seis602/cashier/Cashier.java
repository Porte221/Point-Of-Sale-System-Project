package com.assignment.seis602.cashier;

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
        text += employeeID + " " + username;

        return text;
    }

}

