package me.atam.atam4jdomain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TestsRunResult {

    @JsonProperty
    private List<IndividualTestReport> tests;
    @JsonProperty
    private Status status;


    public TestsRunResult() {
    }

    public TestsRunResult(List<IndividualTestReport> tests, Status status) {
        this.tests = tests;
        this.status = status;
    }

    public List<IndividualTestReport> getTests() {
        return tests;
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
