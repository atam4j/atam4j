package me.atam.atam4jsampleapp;

import java.util.List;

public class TestReport {

    private List<IndividualTestReport> testReports;
    private Status status;

    public TestReport(List<IndividualTestReport> testReports, Status status) {
        this.testReports = testReports;
        this.status = status;
    }

    public List<IndividualTestReport> getTestReports() {
        return testReports;
    }

    public Status getStatus() {
        return status;
    }

    public static enum Status{
        TOO_EARLY("Too early to tell - tests not complete yet"), ALL_OK("All is A OK!"), FAILURES("Failues");

        private String message;

        Status(String message) {
            this.message = message;
        }
    }


}
