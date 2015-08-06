package me.atam.atam4j.health;

import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class AcceptanceTestsHealthCheck extends HealthCheck {

    public static final String TOO_EARLY_MESSAGE = "Too early to tell - tests not complete yet";
    public static final String OK_MESSAGE = "All is A OK!";

    private static AcceptanceTestsState testsState;

    public static final String NAME = "Acceptance Tests HealthCheck";

    private final ObjectMapper mapper = new ObjectMapper();

    public AcceptanceTestsHealthCheck(AcceptanceTestsState acceptanceTestsState) {
        testsState = acceptanceTestsState;
    }

    @Override
    protected Result check() throws Exception {
        if (testsState.getResult().isPresent()){
            if (testsState.getResult().get().wasSuccessful()) {
                return AcceptanceTestsHealthCheck.Result.healthy(OK_MESSAGE);
            } else {
                return AcceptanceTestsHealthCheck.Result.unhealthy(getTestRunReport());
            }
        } else {
            return AcceptanceTestsHealthCheck.Result.healthy(TOO_EARLY_MESSAGE);
        }
    }


    private String getTestRunReport() {
        Optional<org.junit.runner.Result> testResultOpt = testsState.getResult();
        if(testResultOpt.isPresent()) {
            TestRunReport testRunReport = new TestRunReport(
                    testResultOpt.get().getRunCount(),
                    testResultOpt.get().getFailureCount(),
                    testResultOpt.get().getFailures()
            );
            try {
                return mapper.writeValueAsString(testRunReport);
            } catch (JsonProcessingException e) {
                return "Exception in generating test run report" + e.getMessage();
            }
        }
        return "Test Result is empty";
    }
}