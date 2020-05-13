package com.assignment.seis602.logging;


public interface ILogger {
    static void logToConsole(String message) {
        System.out.println(message);
    }
}
