package com.assignment.seis602.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegisterLog extends PoSLogger {
    private static final Logger LOGGER = LogManager.getLogger(RegisterLog.class);
    private ConcurrentLinkedQueue<String> logQueue = new ConcurrentLinkedQueue();
    private int queueThreshhold = 5;
    private final String REGISTER_LOG_LOCATION = "resources/register.log";

    public RegisterLog() {
    }

    public RegisterLog(int queueThreshold) {
        this.queueThreshhold = queueThreshold;
    }

    public void generateReport() {
        String fileName = REGISTER_LOG_LOCATION;
        List<String> list = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {


            stream.forEach(line -> list.add(line));

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("--REGISTER LOG DUMP--");

        list.forEach(System.out::println);
    }

    public void generateDetailedReport(Object identifier) {
        String fileName = REGISTER_LOG_LOCATION;
        List<String> list = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            list = stream
                    .filter(line -> line.contains(identifier.toString()))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("--DETAILED REGISTER LOG REPORT--");
        list.forEach(System.out::println);
    }


    public void log(String message) {
        logQueue.add(message);
        if (logQueue.size() >= queueThreshhold) {
            for (String msg : logQueue) {
                LOGGER.error(msg);
            }
            logQueue.clear();
        }
    }
}
