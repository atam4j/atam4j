package me.atam.atam4jsampleapp;

import me.atam.atam4j.dummytests.PassingAndFailingTestsWithCategories;
import me.atam.atam4jdomain.IndividualTestResult;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PassingAndFailingTestsByCategoryAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenPassingAndFailingTests_whenPassngTestsByCategoryEndpointCalledAfterTestRun_thenOKMessageReceivedForOneTestOnly(){
        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, PassingAndFailingTestsWithCategories.class, 10000);
        //when
        Response response = getResponseFromTestsWithCategoryOnceTestRunHasCompleted("A");
        //then
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));


        assertThat(testRunResult.getTests().size(), is(1));


        IndividualTestResult passingTest = testRunResult.getTestByClassAndName("me.atam.atam4j.dummytests.PassingAndFailingTestsWithCategories", "testThatPassesWithCategoryA").get();

        assertThat(passingTest.getTestName(), is("testThatPassesWithCategoryA"));
        assertThat(passingTest.getCategory(), is("A"));
        assertThat(passingTest.isPassed(), is(true));
        assertThat(passingTest.getException(), is(nullValue()));

    }

    @Test
    public void givenPassingAndFailingTests_whenTestsEndpointCalledAfterTestRun_thenErrorMessageReceivedForTwoTests(){
        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, PassingAndFailingTestsWithCategories.class, 10000);
        //when
        Response response = getResponseFromTestsEndpointOnceTestsRunHasCompleted();
        //then
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(response.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        assertThat(testRunResult.getTests().size(), is(2));


        IndividualTestResult passingTest = testRunResult.getTestByClassAndName("me.atam.atam4j.dummytests.PassingAndFailingTestsWithCategories", "testThatPassesWithCategoryA").get();

        assertThat(passingTest.getTestName(), is("testThatPassesWithCategoryA"));
        assertThat(passingTest.getCategory(), is("A"));
        assertThat(passingTest.isPassed(), is(true));
        assertThat(passingTest.getException(), is(nullValue()));


        IndividualTestResult failingTest = testRunResult.getTestByClassAndName("me.atam.atam4j.dummytests.PassingAndFailingTestsWithCategories", "testThatFailsWithCategoryB").get();

        assertThat(failingTest.getTestName(), is("testThatFailsWithCategoryB"));
        assertThat(failingTest.getCategory(), is("B"));
        assertThat(failingTest.isPassed(), is(false));
        assertThat(failingTest.getException().getMessage(), is("Was expecting false to be true"));

    }
}
