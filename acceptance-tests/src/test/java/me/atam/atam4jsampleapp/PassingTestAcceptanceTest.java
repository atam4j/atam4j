package me.atam.atam4jsampleapp;

import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Test;

import javax.ws.rs.core.Response;

public class PassingTestAcceptanceTest extends AcceptanceTest{


    @Test
    public void givenSampleApplicationStartedWithPassingTest_whenHealthCheckCalledBeforeTestRun_thenTooEarlyMessageReceived(){
        applicationConfigurationDropwizardTestSupport = Atam4JApplicationStarter.startApplicationWith(me.atam.atam4j.dummytests.PassingTest.class, 1000);
        checkResponseIsOKAndWithMessage(AcceptanceTestsHealthCheck.TOO_EARLY_MESSAGE, getResponseFromHealthCheck());
    }


    @Test
    public void givenSampleApplicationStartedWithPassingTest_whenHealthCheckCalledAfterTestRUn_thenOKMessageReceived(){
        applicationConfigurationDropwizardTestSupport = Atam4JApplicationStarter.startApplicationWith(me.atam.atam4j.dummytests.PassingTest.class);
        waitForTwoMillis();
        checkResponseIsOKAndWithMessage(AcceptanceTestsHealthCheck.OK_MESSAGE, getResponseFromHealthCheck());
    }

    private void waitForTwoMillis() {
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
