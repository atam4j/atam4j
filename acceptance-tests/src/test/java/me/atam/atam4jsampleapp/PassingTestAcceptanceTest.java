package me.atam.atam4jsampleapp;

import me.atam.atam4j.PollingPredicate;
import me.atam.atam4j.dummytests.PassingTestWithNoCategory;
import me.atam.atam4j.dummytests.TestThatKnowsIfItsBeingRun;
import me.atam.atam4jdomain.IndividualTestResult;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.TEN_SECONDS_IN_MILLIS;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PassingTestAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenPassingTest_whenTestsEndpointCalledBeforeTestRun_thenTooEarlyMessageReceived() {
        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter
                .startApplicationWith(TEN_SECONDS_IN_MILLIS, PassingTestWithNoCategory.class, 1);
        //when
        Response testRunResultFromServer = getTestRunResultFromServer(getTestsURI());
        //then
        assertThat(testRunResultFromServer.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(
                testRunResultFromServer.readEntity(TestsRunResult.class).getStatus(),
                is(TestsRunResult.Status.TOO_EARLY)
        );
    }

    @Test
    public void givenPassingTest_whenTestsEndpointCalledAfterTestRun_thenOKMessageReceived() {
        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter
                .startApplicationWith(0, PassingTestWithNoCategory.class, 1);
        //when
        Response response = getResponseFromTestsEndpointOnceTestsRunHasCompleted();
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        //then
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(testRunResult.getTests().size(), is(1));
        assertThat(
                testRunResult.getTests(),
                hasItem(new IndividualTestResult(PassingTestWithNoCategory.class.getName(), "testThatPasses", true))
        );
    }

    /*
        TODO - Currently this test relies on a junit test that "knows" if it is being run or not.  Once/if we build
        support for exposing the if a test run is in progress then this test's implementation can change.
    */
    @Test
    public void givenPassingTest_whenTestsEndpointCalledDuringTestRun_thenStatusOfLastRunReturned()
            throws InterruptedException {
        //given test run completed
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter
                .startApplicationWith(0, TestThatKnowsIfItsBeingRun.class, 1000);
        TestsRunResult firstTestRunResult = getResponseFromTestsEndpointOnceTestsRunHasCompleted()
                .readEntity(TestsRunResult.class);

        //when tests are in progress
        new PollingPredicate<>(100, 10, o -> TestThatKnowsIfItsBeingRun.testInProgress(), () -> null)
                .pollUntilPassedOrFail("Tests were never seen to be In Progress");
        TestsRunResult testRunResultWhenTestsInProgress = getTestRunResultFromServer(getTestsURI())
                .readEntity(TestsRunResult.class);
        //check they are still in progress once the status endpoint has been called.
        assertTrue(TestThatKnowsIfItsBeingRun.testInProgress());

        //then number of tests run are the same
        assertThat(testRunResultWhenTestsInProgress.getStatus(), is(equalTo(firstTestRunResult.getStatus())));
        assertThat(testRunResultWhenTestsInProgress.getTests().size(),
                is(equalTo(firstTestRunResult.getTests().size())));
    }
}
