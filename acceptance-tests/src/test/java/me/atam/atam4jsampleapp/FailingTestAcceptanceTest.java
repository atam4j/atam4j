package me.atam.atam4jsampleapp;

import me.atam.atam4j.PollingPredicate;
import me.atam.atam4j.dummytests.FailingTest;
import me.atam.atam4j.dummytests.PassingTest;
import me.atam.atam4jdomain.IndividualTestResult;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.MAX_ATTEMPTS;
import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.RETRY_POLL_INTERVAL;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FailingTestAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenSampleApplicationStartedWithFailingTest_whenHealthCheckCalledAfterTestRun_thenFailuresMessageReceived(){

        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWith(0, FailingTest.class);

        PollingPredicate<Response> responsePollingPredicate = new PollingPredicate<>(
                MAX_ATTEMPTS,
                RETRY_POLL_INTERVAL,
                response -> response.readEntity(TestsRunResult.class).getStatus().equals(TestsRunResult.Status.FAILURES),
                this::getTestRunResultFromServer);

        assertTrue(responsePollingPredicate.pollUntilPassedOrMaxAttemptsExceeded());
        Response response = getTestRunResultFromServer();
        assertThat(response.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(testRunResult.getTests().size(), is(1));
        assertThat(testRunResult.getTests(), contains(new IndividualTestResult(FailingTest.class.getName(), "testThatFails", false)));
    }

    @Test
    public void givenSampleApplicationStartedWithPassingAndFailingTest_whenHealthCheckCalledAfterTestRun_thenFailuresMessageReceived(){

        applicationConfigurationDropwizardTestSupport = Atam4jApplicationStarter.startApplicationWithPassingAndFailingTest(0);

        PollingPredicate<Response> responsePollingPredicate = new PollingPredicate<>(
                MAX_ATTEMPTS,
                RETRY_POLL_INTERVAL,
                response -> response.readEntity(TestsRunResult.class).getStatus().equals(TestsRunResult.Status.FAILURES),
                this::getTestRunResultFromServer);

        assertTrue(responsePollingPredicate.pollUntilPassedOrMaxAttemptsExceeded());
        Response response = getTestRunResultFromServer();
        assertThat(response.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);

        assertThat(testRunResult.getTests().size(), is(2));
        assertThat(testRunResult.getTests(), hasItem(new IndividualTestResult(FailingTest.class.getName(), "testThatFails", false)));
        assertThat(testRunResult.getTests(), hasItem(new IndividualTestResult(PassingTest.class.getName(), "testThatPasses", true)));

    }




}
