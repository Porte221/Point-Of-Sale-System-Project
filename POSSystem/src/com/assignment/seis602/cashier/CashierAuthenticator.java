package com.assignment.seis602.cashier;

import com.assignment.seis602.logging.ILogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @Authors Ryan Poorman and Xavier Porter
 * @Description CashierAuthenticator is used to validate the username and password provided through the console
 * with the credentials stored in the associated txt file
 */
public class CashierAuthenticator {

    private static final String FILE_PATH = "resources/login.txt";

    /**
     * Returns an Cashier object that will be associated with the PoSRegister until
     * that Cashier logs off
     *
     * @return      the Cashier mapped to the credentials file
     * @see         Cashier
     */
    public static Cashier conductUserLogin() {

        String inputUsername = "";
        String inputPassword = "";


        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {

            while (true) {
                ILogger.logToConsole("Please Enter Username:");
                inputUsername = reader.readLine().trim();


                ILogger.logToConsole("Please Enter Password:");
                inputPassword = reader.readLine().trim();

                Cashier cashier = checkCredentials(inputUsername, inputPassword);
                if (cashier != null) {
                    return cashier;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cashier checkCredentials(String inputUsername, String inputPassword) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(FILE_PATH));
        String tmpUsername = "";
        String tmpPassword = "";

        while (fileScanner.hasNext()) {
            tmpUsername = fileScanner.next();
            tmpPassword = fileScanner.next();
            if (inputUsername.equals(tmpUsername) && inputPassword.equals(tmpPassword)) {
                ILogger.logToConsole("Login Successful");
                return new Cashier(inputUsername, inputPassword, fileScanner.next());
            }
            fileScanner.nextLine();

        }

        ILogger.logToConsole("Invalid username/password please try again");
        return null;
    }
}
