package me.atam.atam4jdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class TestsRunResult {

    private final Collection<IndividualTestResult> tests;
    private final Status status;

    public TestsRunResult(final @JsonProperty("tests")  Collection<IndividualTestResult> tests,
                          final @JsonProperty("status") Status status) {
        this.tests = tests;
        this.status = status;
    }

    public TestsRunResult(final Collection<IndividualTestResult> tests) {
        this.tests = tests;
        this.status = tests.stream()
                           .filter(testReport -> !testReport.isPassed())
                           .findAny()
                           .map(failures -> Status.FAILURES)
                           .orElse(Status.ALL_OK);
    }

    public Collection<IndividualTestResult> getTests() {
        return tests;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        TOO_EARLY("Too early to tell - tests not complete yet"),
        ALL_OK("All is A OK!"),
        FAILURES("Failues");

        private final String message;

        Status(String message) {
            this.message = message;
        }
    }
}