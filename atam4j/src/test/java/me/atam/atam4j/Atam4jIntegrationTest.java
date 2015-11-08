package me.atam.atam4j;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class Atam4jIntegrationTest {

    public static final int RETRY_POLL_INTERVAL_IN_MILLIS = 2;
    public static final int MAX_ATTEMPTS = 1000; //bit excessive but who knows!
    private HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

    @Test
    public void givenHealthCheckManagerWithPassingTest_whenInitialized_thenTestsAreHealthy() throws Exception{

        new Atam4j.Atam4jBuilder(healthCheckRegistry, null)
                .withTestClasses(PassingTest.class)
                .withInitialDelay(0)
                .build()
                .initialise();

        checkThatWeEventuallyGetSuccess();
    }

    @Test
    public void givenHealthCheckManagerUsingAnnotationScanning_whenInitialized_thenTestsAreHealthy() throws Exception{

        new Atam4j.Atam4jBuilder(healthCheckRegistry, null)
                .withInitialDelay(0)
                .build()
                .initialise();

        checkThatWeEventuallyGetSuccess();
    }


    private void checkThatWeEventuallyGetSuccess() {
        PollingPredicate<Result> resultPollingPredicate = new PollingPredicate<>(MAX_ATTEMPTS, RETRY_POLL_INTERVAL_IN_MILLIS,
                (r) -> r.getMessage().equals(AcceptanceTestsHealthCheck.OK_MESSAGE),
                this::getHealthCheckResult);

        assertThat(resultPollingPredicate.pollUntilPassedOrMaxAttemptsExceeded(), CoreMatchers.is(true));
    }

    private Result getHealthCheckResult() {
        return healthCheckRegistry.runHealthCheck(AcceptanceTestsHealthCheck.NAME);
    }
}
