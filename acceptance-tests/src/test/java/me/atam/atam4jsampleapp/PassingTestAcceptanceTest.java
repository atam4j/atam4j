package me.atam.atam4jsampleapp;

import me.atam.atam4j.PollingPredicate;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PassingTestAcceptanceTest extends AcceptanceTest {

    public static final int MAX_ATTEMPTS = 2000;
    public static final int RETRY_POLL_INTERVAL = 1;
    public static final int TEN_SECONDS_IN_MILLIS = 10000;

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
