package me.atam.atam4jsampleapp;

import me.atam.atam4j.dummytests.PassingAndFailingTestsWithCategories;
import me.atam.atam4jdomain.IndividualTestResult;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PassingAndFailingTestsByCategoryAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenPassingAndFailingTests_whenPassngTestsByCategoryEndpointCalledAfterTestRun_thenOKMessageReceivedForOneTestOnly(){
        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, PassingAndFailingTestsWithCategories.class);
        //when
        Response response = getResponseFromTestsWithCategoryOnceTestRunHasCompleted("A");
        //then
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
        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, PassingAndFailingTestsWithCategories.class);
        //when
        Response response = getResponseFromTestsEndpointOnceTestsRunHasCompleted();
        //then
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
