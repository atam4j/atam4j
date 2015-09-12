package me.atam.atam4jsampleapp;

import me.atam.atam4j.PollingPredicate;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class PassingTestAcceptanceTest extends AcceptanceTest{

    public static final int MAX_ATTEMPTS = 2000;
    public static final int RETRY_POLL_INTERVAL = 1;

    @Test
    public void givenSampleApplicationStartedWithPassingTest_whenHealthCheckCalledBeforeTestRun_thenTooEarlyMessageReceived(){
        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWith(PassingTest.class, 1000);
        checkResponseIsOKAndWithMessage(AcceptanceTestsHealthCheck.TOO_EARLY_MESSAGE, getResponseFromHealthCheck());
    }

    @Test
    public void givenSampleApplicationStartedWithPassingTest_whenHealthCheckCalledAfterTestRUn_thenOKMessageReceived(){
        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWith(PassingTest.class, 0);

        PollingPredicate<Response> responsePollingPredicate = new PollingPredicate<Response>(MAX_ATTEMPTS, RETRY_POLL_INTERVAL,
                response -> response.readEntity(HealthCheckResult.class).getAcceptanceTestsHealthCheckResult().getMessage().equals(AcceptanceTestsHealthCheck.OK_MESSAGE),
                () -> getResponseFromHealthCheck());
        responsePollingPredicate.pollUntilPassedOrMaxAttemptsExceeded();
        checkResponseIsOKAndWithMessage(AcceptanceTestsHealthCheck.OK_MESSAGE, getResponseFromHealthCheck());
    }

}
