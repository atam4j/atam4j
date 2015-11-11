package me.atam.atam4jsampleapp;

import jdk.nashorn.internal.ir.annotations.Ignore;
import me.atam.atam4j.PollingPredicate;
import me.atam.atam4j.dummytests.FailingTest;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import me.atam.atam4jsampleapp.testsupport.HealthCheckResponseChecker;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.MAX_ATTEMPTS;
import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.RETRY_POLL_INTERVAL;
import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.TEN_SECONDS_IN_MILLIS;

public class FailingTestAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenSampleApplicationStartedWithPassingTest_whenHealthCheckCalledAfterTestRUn_thenOKMessageReceived(){

        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWith(FailingTest.class, 0);

        PollingPredicate<TestsRunResult> responsePollingPredicate = new PollingPredicate<>(
                MAX_ATTEMPTS,
                RETRY_POLL_INTERVAL,
                response -> response.getStatus().equals(TestsRunResult.Status.FAILURES),
                this::getTestRunResultFromServer);

        assertTrue(responsePollingPredicate.pollUntilPassedOrMaxAttemptsExceeded());
    }
}
