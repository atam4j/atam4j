package me.atam.atam4jsampleapp;

import me.atam.atam4j.dummytests.FailingTest;
import me.atam.atam4j.dummytests.PassingAndFailingTests;
import me.atam.atam4jdomain.IndividualTestResult;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class FailingTestAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenSampleApplicationStartedWithFailingTest_whenHealthCheckCalledAfterTestRun_thenFailuresMessageReceived(){
        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, FailingTest.class, 10000);
        //when
        Response response = getResponseFromTestsEndpointOnceTestsRunHasCompleted();
        //then
        assertThat(response.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));

        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(testRunResult.getTests().size(), is(1));

        IndividualTestResult individualTestResult = testRunResult.getTests().stream().findFirst().get();
        assertThat(individualTestResult.getTestName(), is("testThatFails"));
        assertThat(individualTestResult.getCategory(), is("default"));
        assertThat(individualTestResult.isPassed(), is(false));
        assertThat(individualTestResult.getException().getMessage(), is("Was expecting false to be true"));

    }

    @Test
    public void givenSampleApplicationStartedWithPassingAndFailingTest_whenHealthCheckCalledAfterTestRun_thenFailuresMessageReceived(){
        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, PassingAndFailingTests.class, 1);
        //when
        Response response = getResponseFromTestsEndpointOnceTestsRunHasCompleted();
        //then
        assertThat(response.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));

        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(testRunResult.getTests().size(), is(2));

        IndividualTestResult failingTest = testRunResult.getTestByClassAndName("me.atam.atam4j.dummytests.PassingAndFailingTests", "testThatFails").get();

        assertThat(failingTest.getTestName(), is("testThatFails"));
        assertThat(failingTest.getCategory(), is("default"));
        assertThat(failingTest.isPassed(), is(false));
        assertThat(failingTest.getException().getMessage(), is("Was expecting false to be true"));

        IndividualTestResult passingTest = testRunResult.getTestByClassAndName("me.atam.atam4j.dummytests.PassingAndFailingTests", "testThatPasses").get();

        assertThat(passingTest.getTestName(), is("testThatPasses"));
        assertThat(passingTest.getCategory(), is("default"));
        assertThat(passingTest.isPassed(), is(true));
        assertThat(passingTest.getException(), is(nullValue()));


    }
}
