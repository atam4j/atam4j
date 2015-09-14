package me.atam.atam4jsampleapp;

import me.atam.atam4j.PollingPredicate;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import me.atam.atam4jsampleapp.testsupport.HealthCheckResponseChecker;
import me.atam.atam4jsampleapp.testsupport.HealthCheckResult;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class PassingTestAcceptanceTest extends AcceptanceTest {

    public static final int MAX_ATTEMPTS = 2000;
    public static final int RETRY_POLL_INTERVAL = 1;
    public static final int TEN_SECONDS_IN_MILLIS = 10000;

    @Test
    public void givenSampleApplicationStartedWithPassingTest_whenHealthCheckCalledBeforeTestRun_thenTooEarlyMessageReceived(){

        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWith(PassingTest.class, TEN_SECONDS_IN_MILLIS);
        new HealthCheckResponseChecker(getResponseFromHealthCheck()).checkResponseIsOKAndWithMessage(AcceptanceTestsHealthCheck.TOO_EARLY_MESSAGE);
    }

    @Test
    public void givenSampleApplicationStartedWithPassingTest_whenHealthCheckCalledAfterTestRUn_thenOKMessageReceived(){

        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWith(PassingTest.class, 0);

        PollingPredicate<Response> responsePollingPredicate = new PollingPredicate<>(
                MAX_ATTEMPTS,
                RETRY_POLL_INTERVAL,
                response -> response.readEntity(HealthCheckResult.class)
                        .getAcceptanceTestsHealthCheckResult()
                        .getMessage()
                        .equals(AcceptanceTestsHealthCheck.OK_MESSAGE),
                this::getResponseFromHealthCheck);

        responsePollingPredicate.pollUntilPassedOrMaxAttemptsExceeded();
        new HealthCheckResponseChecker(getResponseFromHealthCheck()).checkResponseIsOKAndWithMessage(AcceptanceTestsHealthCheck.OK_MESSAGE);
    }

}
