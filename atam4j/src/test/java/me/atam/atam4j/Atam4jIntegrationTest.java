package me.atam.atam4j;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.*;

public class Atam4jIntegrationTest {

    private HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();

    @Test
    public void givenHealthCheckManagerWithPassingTest_whenInitialized_thenTestsAreHealthy() throws Exception{

        new Atam4j.Atam4jBuilder(healthCheckRegistry)
                .withTestClasses(PassingTest.class)
                .withInitialDelay(0)
                .build()
                .initialise();

        //nasty - this is so that the scheduler has run by the time we call the healthcheck
        Thread.sleep(100);

        assertThat(getHealthCheckResult().getMessage(), CoreMatchers.equalTo(AcceptanceTestsHealthCheck.OK_MESSAGE));
        assertThat(getHealthCheckResult().isHealthy(), CoreMatchers.is(true));
    }

    @Test
    public void givenHealthCheckManagerUsingAnnotationScanning_whenInitialized_thenTestsAreHealthy() throws Exception{

        new Atam4j.Atam4jBuilder(healthCheckRegistry)
                .withInitialDelay(0)
                .build()
                .initialise();

        //nasty - this is so that the scheduler has run by the time we call the healthcheck
        Thread.sleep(100);

        assertThat(getHealthCheckResult().getMessage(), CoreMatchers.equalTo(AcceptanceTestsHealthCheck.OK_MESSAGE));
        assertThat(getHealthCheckResult().isHealthy(), CoreMatchers.is(true));
    }

    private HealthCheck.Result getHealthCheckResult() {
        return healthCheckRegistry.runHealthCheck(AcceptanceTestsHealthCheck.NAME);
    }
}
