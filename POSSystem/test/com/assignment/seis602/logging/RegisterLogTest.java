package com.assignment.seis602.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class RegisterLogTest {
    private static final Logger LOGGER = LogManager.getLogger(RegisterLogTest.class);

    @After
    public void reset() {
        try {
            File file = new File("resources/register.log");
            file.delete();
        } catch (Exception e) {}
    }

    @Test
    public void testDefaultLog() {
        RegisterLog rLog = new RegisterLog(1);
        rLog.log("test");
        rLog.log("tes2");
        rLog.log("tes3");
        rLog.log("tes4");

        rLog.generateReport();

        rLog.generateDetailedReport("tes2");


    }

}
