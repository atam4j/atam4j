package me.atam.atam4jsampleapp;

import me.atam.atam4j.PollingPredicate;
import me.atam.atam4j.dummytests.PassingAndFailingTestsWithCategories;
import me.atam.atam4j.dummytests.PassingTestsWithCategories;
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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PassingAndFailingTestsByCategoryAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenPassingAndFailingTests_whenPassngTestsByCategoryEndpointCalledAfterTestRun_thenOKMessageReceivedForOneTestOnly(){

        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, PassingAndFailingTestsWithCategories.class);

        PollingPredicate<Response> responsePollingPredicate = new PollingPredicate<>(
                MAX_ATTEMPTS,
                RETRY_POLL_INTERVAL,
                response -> response.readEntity(TestsRunResult.class).getStatus().equals(TestsRunResult.Status.ALL_OK),
                () -> getTestRunResultFromServerWithCategory("A"));

        assertTrue(responsePollingPredicate.pollUntilPassedOrMaxAttemptsExceeded());
        Response response = getTestRunResultFromServerWithCategory("A");
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(testRunResult.getTests().size(), is(1));
        assertThat(
                testRunResult.getTests(),
                hasItem(new IndividualTestResult(PassingAndFailingTestsWithCategories.class.getName(), "testThatPassesWithCategoryA", "A", true))
        );
    }

    @Test
    public void givenPassingAndFailingTests_whenTestsEndpointCalledAfterTestRun_thenErrorMessageReceivedForTwoTests(){

        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, PassingAndFailingTestsWithCategories.class);

        Response response = getResponseFromTestsEndpointFailedResponseReceived();
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(response.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        assertThat(testRunResult.getTests().size(), is(2));
        assertThat(testRunResult.getTests(),
                hasItem(new IndividualTestResult(PassingAndFailingTestsWithCategories.class.getName(), "testThatPassesWithCategoryA", "A", true))
        );
        assertThat(testRunResult.getTests(),
                hasItem(new IndividualTestResult(PassingAndFailingTestsWithCategories.class.getName(), "testThatFailsWithCategoryB", "B", false))
        );
    }
}
