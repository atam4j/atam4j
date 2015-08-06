package me.atam.atam4j.health;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.runner.notification.Failure;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class TestRunReport {

    @JsonProperty("testRunCount")
    private int testRunCount;
    @JsonProperty("testFailureCount")
    private int testFailureCount;
    @JsonProperty("failedTests")
    private List<FailedTest> failedTests;

    public TestRunReport(int testRunCount, int testFailureCount, List<Failure> failures) {
        this.testRunCount = testRunCount;
        this.testFailureCount = testFailureCount;

        this.failedTests = failures.stream()
                .map(failure -> new FailedTest(failure.getTestHeader(), failure.getMessage()))
                .collect(Collectors.toList());
    }
    //needed by jackson for unmarshalling
    protected TestRunReport() {
    }

    public int getTestRunCount() {
        return testRunCount;
    }

    public int getTestFailureCount() {
        return testFailureCount;
    }

    public List<FailedTest> getFailedTests() {
        return failedTests;
    }
}
