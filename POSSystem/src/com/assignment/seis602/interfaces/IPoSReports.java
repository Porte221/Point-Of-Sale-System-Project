package com.assignment.seis602.interfaces;

public interface IPoSReports {
    void generateInventoryReport();

    void generateReport();

    void generateDetailedReport(String identifier);
}
