package com.assignment.seis602.driver;

import com.assignment.seis602.logging.ILogger;
import com.assignment.seis602.register.PoSRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleUIDriver {

    private static PoSRegister register;

    public static void main(String[] args) {
        ILogger.logToConsole("---Welcome to the Point of Sale System---");
        loadSystemDependencies();
        register.startup();
    }


    private static void loadSystemDependencies() {
        register = new PoSRegister();
    }


}
