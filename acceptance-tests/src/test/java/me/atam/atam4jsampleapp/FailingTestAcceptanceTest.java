package me.atam.atam4jsampleapp;

import me.atam.atam4j.PollingPredicate;
import me.atam.atam4j.dummytests.FailingTest;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.MAX_ATTEMPTS;
import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.RETRY_POLL_INTERVAL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FailingTestAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenSampleApplicationStartedWithFailingTest_whenHealthCheckCalledAfterTestRun_thenFailuresMessageReceived(){

        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWith(FailingTest.class, 0);

        PollingPredicate<Response> responsePollingPredicate = new PollingPredicate<>(
                MAX_ATTEMPTS,
                RETRY_POLL_INTERVAL,
                response -> response.readEntity(TestsRunResult.class).getStatus().equals(TestsRunResult.Status.FAILURES),
                this::getTestRunResultFromServer);

        assertTrue(responsePollingPredicate.pollUntilPassedOrMaxAttemptsExceeded());
        assertThat(getTestRunResultFromServer().getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
    }
}
