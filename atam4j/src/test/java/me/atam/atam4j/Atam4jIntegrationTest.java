package me.atam.atam4j;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4j.resources.TestStatusResource;
import me.atam.atam4jdomain.TestsRunResult;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class Atam4jIntegrationTest {

    @Test
    public void givenHealthCheckManagerWithPassingTest_whenInitialized_thenTestsAreHealthy() throws Exception{

        JerseyEnvironment jerseyEnvironment = mock(JerseyEnvironment.class);

        ArgumentCaptor<TestStatusResource> argumentCaptor = ArgumentCaptor.forClass(TestStatusResource.class);

        new Atam4j.Atam4jBuilder(jerseyEnvironment)
                .withTestClasses(PassingTest.class)
                .withInitialDelay(0)
                .build()
                .initialise();
        verify(jerseyEnvironment).register(argumentCaptor.capture());

        TestStatusResource value = argumentCaptor.getValue();
        assertNotNull(value);

        checkThatWeEventuallyGetSuccess(value);
    }

    @Test
    public void givenHealthCheckManagerUsingAnnotationScanning_whenInitialized_thenTestsAreHealthy() throws Exception{
        JerseyEnvironment jerseyEnvironment = mock(JerseyEnvironment.class);

        ArgumentCaptor<TestStatusResource> argumentCaptor = ArgumentCaptor.forClass(TestStatusResource.class);

        new Atam4j.Atam4jBuilder(jerseyEnvironment)
                .withInitialDelay(0)
                .build()
                .initialise();
        verify(jerseyEnvironment).register(argumentCaptor.capture());

        TestStatusResource value = argumentCaptor.getValue();
        assertNotNull(value);

        checkThatWeEventuallyGetSuccess(value);
    }


    private void checkThatWeEventuallyGetSuccess(TestStatusResource resource) {
        PollingPredicate<TestsRunResult> resultPollingPredicate = new PollingPredicate<>(UnitTestTimeouts.MAX_ATTEMPTS, UnitTestTimeouts.RETRY_POLL_INTERVAL_IN_MILLIS,
                o -> o.getStatus().equals(TestsRunResult.Status.ALL_OK),
                resource::getTestStatus);

        assertThat(resultPollingPredicate.pollUntilPassedOrMaxAttemptsExceeded(), CoreMatchers.is(true));
    }

}
