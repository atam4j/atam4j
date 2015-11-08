package me.atam.atam4jsampleapp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TestsStatusResult {

    @JsonProperty
    private List<IndividualTestReport> testReports;
    @JsonProperty
    private Status status;

    public TestsStatusResult(List<IndividualTestReport> testReports, Status status) {
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
