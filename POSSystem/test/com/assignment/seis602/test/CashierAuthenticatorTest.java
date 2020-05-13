package com.assignment.seis602.test;

import com.assignment.seis602.cashier.Cashier;
import com.assignment.seis602.cashier.CashierAuthenticator;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;

public class CashierAuthenticatorTest {

    @Test
    public void testValidLogin() throws FileNotFoundException {
        Assert.assertEquals(Cashier.class, CashierAuthenticator.checkCredentials("Ryan", "789").getClass());
    }


    @Test
    public void userInputRequiredTest() throws FileNotFoundException {
        CashierAuthenticator.conductUserLogin();
    }
}
