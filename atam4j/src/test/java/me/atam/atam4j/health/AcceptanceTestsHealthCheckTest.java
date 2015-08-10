package me.atam.atam4j.health;

import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AcceptanceTestsHealthCheckTest {

    private static final String DUMMY_FAILING_TEST = "failingTestMethod(me.atam4j.client.acceptancetests.TestClass)";
    private static final String DUMMY_TEST_FAILURE_MESSAGE = "Expected this but got that!";
    public static final int TOTAL_TESTS_RUN = 2;
    public static final int FAILED_TEST_COUNT = 1;
    Result result = mock(Result.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenTestsNotYetRun_whenHealthCheckCalled_thenTooEarlyMessageReceivedAndStatusIsOK()throws Exception{
        HealthCheck.Result healthCheckResult = new AcceptanceTestsHealthCheck(new AcceptanceTestsState()).check();
        assertThat(healthCheckResult.getMessage(), equalTo(AcceptanceTestsHealthCheck.TOO_EARLY_MESSAGE));
        assertThat(healthCheckResult.isHealthy(), equalTo(true));
    }

    @Test
    public void givenTestsRunAndSuccessful_whenHealthCheckCalled_thenSuccessMessageReceived()throws Exception{
        given(result.wasSuccessful()).willReturn(true);
        HealthCheck.Result healthCheckResult = new AcceptanceTestsHealthCheck(getAcceptanceTestsStateWithMockedResult()).check();
        assertThat(healthCheckResult.getMessage(), equalTo(AcceptanceTestsHealthCheck.OK_MESSAGE));
        assertThat(healthCheckResult.isHealthy(), equalTo(true));
    }

    @Test
    public void givenTestRunHasAFailedTest_whenHealthCheckCalled_thenTestRunReportJsonIsReceived()throws Exception{
        prepareResultWithAFailingTest(DUMMY_FAILING_TEST, DUMMY_TEST_FAILURE_MESSAGE);
        HealthCheck.Result healthCheckResult = new AcceptanceTestsHealthCheck(getAcceptanceTestsStateWithMockedResult()).check();
        assertThat(healthCheckResult.isHealthy(), equalTo(false));
        String message = healthCheckResult.getMessage();
        TestRunReport testRunReport = mapper.readValue(message, TestRunReport.class);
        assertTestRunReportIncludesFailingTestDetails(testRunReport);
    }

    private void prepareResultWithAFailingTest(String failingTest, String testFailureMessage) {
        given(result.wasSuccessful()).willReturn(false);
        given(result.getRunCount()).willReturn(TOTAL_TESTS_RUN);
        given(result.getFailureCount()).willReturn(FAILED_TEST_COUNT);

        Failure failure = mock(Failure.class);
        given(failure.getMessage()).willReturn(testFailureMessage);
        given(failure.getTestHeader()).willReturn(failingTest);
        when(result.getFailures()).thenReturn(Collections.singletonList(failure));
    }

    private void assertTestRunReportIncludesFailingTestDetails(TestRunReport testRunReport) {
        assertThat(testRunReport.getTestRunCount(), is(TOTAL_TESTS_RUN));
        assertThat(testRunReport.getTestFailureCount(), is(FAILED_TEST_COUNT));
        assertThat(testRunReport.getFailedTests().size(), is(1));
        FailedTest failedTest = testRunReport.getFailedTests().get(0);
        assertThat(failedTest.getTest(), is(DUMMY_FAILING_TEST));
        assertThat(failedTest.getCause(), is(DUMMY_TEST_FAILURE_MESSAGE));
    }

    private AcceptanceTestsState getAcceptanceTestsStateWithMockedResult() {
        AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
        acceptanceTestsState.setResult(result);
        return acceptanceTestsState;
    }

}
