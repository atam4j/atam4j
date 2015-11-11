package me.atam.atam4jdomain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.List;

public class TestsRunResult {

    @JsonProperty
    private Collection<IndividualTestResult> tests;
    @JsonProperty
    private Status status = Status.TOO_EARLY;


    //for jackson
    public TestsRunResult() {
    }

    public TestsRunResult(Status status) {
        this.status = status;
    }

    public TestsRunResult(Collection<IndividualTestResult> tests) {
        this.tests = tests;
        if (tests.parallelStream().filter(testReport -> !testReport.isPassed()).findAny().isPresent()){
            this.status = Status.FAILURES;
        }
        else{
            this.status = Status.ALL_OK;
        }
    }

    public Collection<IndividualTestResult> getTests() {
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
