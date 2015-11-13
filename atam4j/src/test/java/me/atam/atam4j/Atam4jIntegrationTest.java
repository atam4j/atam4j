package me.atam.atam4j;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import jdk.nashorn.internal.ir.annotations.Ignore;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import me.atam.atam4j.resources.TestStatusResource;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class Atam4jIntegrationTest {

    public static final int RETRY_POLL_INTERVAL_IN_MILLIS = 2;
    public static final int MAX_ATTEMPTS = 1000; //bit excessive but who knows!

    @Test
    @Ignore
    public void givenHealthCheckManagerWithPassingTest_whenInitialized_thenTestsAreHealthy() throws Exception{

        JerseyEnvironment jerseyEnvironment = mock(JerseyEnvironment.class);
        //jerseyEnvironment.

        ArgumentCaptor<TestStatusResource> argumentCaptor = ArgumentCaptor.forClass(TestStatusResource.class);
        verify(jerseyEnvironment).register(argumentCaptor.capture());

        new Atam4j.Atam4jBuilder(jerseyEnvironment)
                .withTestClasses(PassingTest.class)
                .withInitialDelay(0)
                .build()
                .initialise();

        TestStatusResource value = argumentCaptor.getValue();
        assertNotNull(value);

        //checkThatWeEventuallyGetSuccess();
    }

    @Test
    @Ignore
    public void givenHealthCheckManagerUsingAnnotationScanning_whenInitialized_thenTestsAreHealthy() throws Exception{

        new Atam4j.Atam4jBuilder(null)
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
        return null;//healthCheckRegistry.runHealthCheck(AcceptanceTestsHealthCheck.NAME);
    }
}
