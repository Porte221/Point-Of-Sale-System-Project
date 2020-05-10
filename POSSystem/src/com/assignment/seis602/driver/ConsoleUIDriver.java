package com.assignment.seis602.driver;

import com.assignment.seis602.cashier.Cashier;
import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.item.Item;
import com.assignment.seis602.register.PoSRegister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleUIDriver {

    private static PoSRegister register;

    public static void main(String[] args) {
        System.out.println("---Welcome to the Point of Sale System---");
        loadSystemDependencies();
        startListener();
    }



    private static void loadSystemDependencies() {
//        new Item().StartCreateItems();
//        new Inventory().generateInventoryItems();
        register = new PoSRegister();
    }

    private static String displayUIOptions() throws IOException {
        System.out.println("Select one of the following options: (Enter associated numeric value)");
        System.out.println("1. New Sale");
        System.out.println("2. Sign Out");
        System.out.println("3. Shutdown");
        System.out.println("4. Print Register Report");
        System.out.println("5. Print Detailed Register Report");


        Scanner in = new Scanner(System.in);


        return in.nextLine();
    }

    private static void startListener() {
        String response = "";

        try {
            do {
                response = displayUIOptions();
                System.out.println(response.equals("3"));

                if (response.equals("1")) {
                    register.startOrder();
                } else if (response.equals("2")) {
                    register.authenticate();
                } else if (response.equals("3")) {
                    System.exit(1);
                } else if (response.equals("4")) {
                    register.generateReport();
                } else if (response.equals("5")) {
                    System.out.println("Please enter the username or other identifier to generate detailed report.");
                    register.generateDetailedReport(new BufferedReader(new InputStreamReader(System.in)).readLine());
                }
            } while (true);
        } catch (Exception e) {

        }
    }
}
