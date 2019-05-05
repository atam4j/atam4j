package me.atam.atam4j;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import me.atam.atam4j.dummytests.PassingTestWithNoCategory;
import me.atam.atam4j.resources.TestStatusResource;
import me.atam.atam4jdomain.TestsRunResult;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class Atam4jIntegrationTest {

    Atam4j atam4j = null;

    @Test
    public void givenHealthCheckManagerWithPassingTest_whenInitialized_thenTestsAreHealthy() {

        Environment environment = mock(Environment.class);
        JerseyEnvironment jerseyEnvironment = mock(JerseyEnvironment.class);
        when(environment.jersey()).thenReturn(jerseyEnvironment);

        ArgumentCaptor<TestStatusResource> argumentCaptor = ArgumentCaptor.forClass(TestStatusResource.class);

        atam4j = new Atam4j.Atam4jBuilder(environment)
                .withTestClasses(PassingTestWithNoCategory.class)
                .withInitialDelay(0)
                .build();
        atam4j.start();

        verify(jerseyEnvironment).register(argumentCaptor.capture());

        TestStatusResource value = argumentCaptor.getValue();
        assertNotNull(value);

        checkThatWeEventuallyGetSuccess(value);
    }

    @Test
    public void givenHealthCheckManagerUsingAnnotationScanning_whenInitialized_thenTestsAreHealthy() {
        Environment environment = mock(Environment.class);
        JerseyEnvironment jerseyEnvironment = mock(JerseyEnvironment.class);
        when(environment.jersey()).thenReturn(jerseyEnvironment);

        ArgumentCaptor<TestStatusResource> argumentCaptor = ArgumentCaptor.forClass(TestStatusResource.class);

        atam4j = new Atam4j.Atam4jBuilder(environment)
                .withInitialDelay(0)
                .build();
        atam4j.start();
        verify(jerseyEnvironment).register(argumentCaptor.capture());

        TestStatusResource value = argumentCaptor.getValue();
        assertNotNull(value);

        checkThatWeEventuallyGetSuccess(value);
    }

    @After
    public void tearDown()  {
        atam4j.stop();
    }

    private void checkThatWeEventuallyGetSuccess(TestStatusResource resource) {
        PollingPredicate<TestsRunResult> resultPollingPredicate = new PollingPredicate<>(UnitTestTimeouts.MAX_ATTEMPTS, UnitTestTimeouts.RETRY_POLL_INTERVAL_IN_MILLIS,
                testsRunResult -> testsRunResult.getStatus().equals(TestsRunResult.Status.ALL_OK),
                () -> (TestsRunResult) resource.getTestStatus().getEntity());

        assertThat(resultPollingPredicate.pollUntilPassedOrMaxAttemptsExceeded(), CoreMatchers.is(true));
    }

}
