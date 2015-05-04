package me.atam.atam4j;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import me.atam.atam4j.ignore.PassingTest;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AcceptanceTestHealthCheckManagerIntegrationTest {


    private Environment environment = mock(Environment.class);
    private LifecycleEnvironment lifeCycleEnvironment = new LifecycleEnvironment();
    private HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();


    @Before
    public void setUp() throws Exception {
        when(environment.lifecycle()).thenReturn(lifeCycleEnvironment);
        when(environment.healthChecks()).thenReturn(healthCheckRegistry);
    }

    @Test
    public void givenHealthCheckManagerWithPassingTest_whenInitialized_thenTestsAreHealthy() throws Exception{

        new AcceptanceTestHealthCheckManager.AcceptanceTestsRunnerTaskSchedulerBuilder()
                .withTestClasses(new Class[]{PassingTest.class})
                .withEnvironment(environment)
                .build()
                .initialise();

        //nasty - this is so that the scheduler has run by the time we call the healthcheck
        Thread.sleep(100);

        assertThat(getHealthCheckResult().getMessage(), CoreMatchers.equalTo(AcceptanceTestsHealthCheck.OK_MESSAGE));
        assertThat(getHealthCheckResult().isHealthy(), CoreMatchers.is(true));
    }

    private HealthCheck.Result getHealthCheckResult() {
        return environment.healthChecks().runHealthCheck(AcceptanceTestsHealthCheck.NAME);
    }
}
