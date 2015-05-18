package me.atam.atam4j.health;

import com.codahale.metrics.health.HealthCheck;
import org.junit.runner.notification.Failure;

public class AcceptanceTestsHealthCheck extends HealthCheck {

    public static final String TOO_EARLY_MESSAGE = "Too early to tell - tests not complete yet";
    public static final String OK_MESSAGE = "All is A OK!";

    private static AcceptanceTestsState testsState;

    public static final String NAME = "Acceptance Tests HealthCheck";

    public AcceptanceTestsHealthCheck(AcceptanceTestsState acceptanceTestsState) {
        testsState = acceptanceTestsState;
    }

    @Override
    protected Result check() throws Exception {
        if (testsState.getResult().isPresent()){
            if (testsState.getResult().get().wasSuccessful()) {
                return AcceptanceTestsHealthCheck.Result.healthy(OK_MESSAGE);
            } else {
                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append(String.format("Number of failures %d", testsState.getResult().get().getFailureCount()));
                for (Failure failure : testsState.getResult().get().getFailures()) {
                    messageBuilder.append(" ");
                    messageBuilder.append(failure.getMessage());
                }
                return AcceptanceTestsHealthCheck.Result.unhealthy(messageBuilder.toString());
            }
        } else {
            return AcceptanceTestsHealthCheck.Result.healthy(TOO_EARLY_MESSAGE);
        }
    }
}