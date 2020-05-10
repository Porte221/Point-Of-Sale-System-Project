package com.assignment.seis602.cashier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class CashierAuthenticator {

    private static final String FILE_PATH = "resources/login.txt";

    public static Cashier conductUserLogin() {

        boolean awaitingLogin = true;
        String inputUsername = "";
        String inputPassword = "";


        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {

            while (true) {
                System.out.println("Please Enter Username:");
                inputUsername = reader.readLine().trim();


                System.out.println("Please Enter Password:");
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
                System.out.println("Login Successful");
                return new Cashier(inputUsername, inputPassword, fileScanner.next());
            }
            fileScanner.nextLine();

        }

        System.out.println("Invalid username/password please try again");
        return null;
    }
}
