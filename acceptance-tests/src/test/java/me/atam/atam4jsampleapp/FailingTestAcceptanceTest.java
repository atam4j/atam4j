package me.atam.atam4jsampleapp;

import me.atam.atam4j.dummytests.FailingTest;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

public class FailingTestAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenSampleApplicationStartedWithFailingTest_whenHealthCheckCalledBeforeTestRun_thenFailureMessageReceived() {
        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWith(FailingTest.class);
        checkResponseIsErrorAndWithMessage(AcceptanceTestsHealthCheck.FAILURE_MESSAGE + " 1. Was expecting false to be true", getResponseFromHealthCheck());
    }
}
