package me.atam.atam4jsampleapp;

import me.atam.atam4j.PollingPredicate;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.MAX_ATTEMPTS;
import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.RETRY_POLL_INTERVAL;
import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.TEN_SECONDS_IN_MILLIS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PassingTestAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenSampleApplicationStartedWithPassingTest_whenHealthCheckCalledBeforeTestRun_thenTooEarlyMessageReceived(){
        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWith(PassingTest.class, TEN_SECONDS_IN_MILLIS);
        TestsRunResult testRunResultFromServer = getTestRunResultFromServer();
        assertThat(testRunResultFromServer.getStatus(), is(TestsRunResult.Status.TOO_EARLY));
    }

    @Test
    public void givenSampleApplicationStartedWithPassingTest_whenHealthCheckCalledAfterTestRUn_thenOKMessageReceived(){

        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWith(PassingTest.class, 0);

        PollingPredicate<TestsRunResult> responsePollingPredicate = new PollingPredicate<>(
                MAX_ATTEMPTS,
                RETRY_POLL_INTERVAL,
                response -> response.getStatus().equals(TestsRunResult.Status.ALL_OK),
                this::getTestRunResultFromServer);

        assertTrue(responsePollingPredicate.pollUntilPassedOrMaxAttemptsExceeded());
    }
}
