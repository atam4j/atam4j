package me.atam.atam4j.health;

import com.codahale.metrics.health.HealthCheck;
import org.junit.runner.notification.Failure;

public class AcceptanceTestsHealthCheck extends HealthCheck {

    private static AcceptanceTestsState testsState;

    public AcceptanceTestsHealthCheck(AcceptanceTestsState acceptanceTestsState) {
        testsState = acceptanceTestsState;
    }

    @Override
    protected Result check() throws Exception {
        if (testsState.getResult().isPresent()){
            if (testsState.getResult().get().wasSuccessful()) {
                return AcceptanceTestsHealthCheck.Result.healthy("All is A OK!");
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
            return AcceptanceTestsHealthCheck.Result.healthy("Too early to tell - tests not complete yet");
        }
    }
}