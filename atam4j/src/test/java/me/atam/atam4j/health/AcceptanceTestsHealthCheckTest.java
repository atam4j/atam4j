package me.atam.atam4j.health;

import com.codahale.metrics.health.HealthCheck;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AcceptanceTestsHealthCheckTest {


    private static final String DUMMY_TEST_NAME = "dummyTestMethod(dummy.test.Class)";
    private static final String DUMMY_TEST_FAILURE_MESSAGE = "Expected this but got that!";
    Result result = mock(Result.class);

    @Test
    public void givenTestsNotYetRun_whenHealthCheckCalled_thenTooEarlyMessageReceivedAndStatusIsOK()throws Exception{
        HealthCheck.Result healthCheckResult = new AcceptanceTestsHealthCheck(new AcceptanceTestsState()).check();
        assertThat(healthCheckResult.getMessage(), CoreMatchers.equalTo(AcceptanceTestsHealthCheck.TOO_EARLY_MESSAGE));
        assertThat(healthCheckResult.isHealthy(), CoreMatchers.equalTo(true));
    }

    @Test
    public void givenTestsRunAndSuccessful_whenHealthCheckCalled_thenSuccessMessageReceived()throws Exception{
        when(result.wasSuccessful()).thenReturn(true);
        HealthCheck.Result healthCheckResult = new AcceptanceTestsHealthCheck(getAcceptanceTestsStateWithMockedResult()).check();
        assertThat(healthCheckResult.getMessage(), CoreMatchers.equalTo(AcceptanceTestsHealthCheck.OK_MESSAGE));
        assertThat(healthCheckResult.isHealthy(), CoreMatchers.equalTo(true));
    }

    @Test
    public void givenTestsFailed_whenHealthCheckCalled_thenFailureMessageReceived()throws Exception{
        when(result.wasSuccessful()).thenReturn(false);
        prepareResultWithFailure(DUMMY_TEST_NAME, DUMMY_TEST_FAILURE_MESSAGE);
        HealthCheck.Result healthCheckResult = new AcceptanceTestsHealthCheck(getAcceptanceTestsStateWithMockedResult()).check();
        assertThat(
                healthCheckResult.getMessage(),
                CoreMatchers.equalTo(
                        AcceptanceTestsHealthCheck.FAILURE_MESSAGE + " 1. ["
                        + DUMMY_TEST_NAME + " failed:\"" + DUMMY_TEST_FAILURE_MESSAGE + "\"]"
                )
        );
        assertThat(healthCheckResult.isHealthy(), CoreMatchers.equalTo(false));
    }

    private void prepareResultWithFailure(String testName, String testFailureMessage) {
        ArrayList<Failure> value = new ArrayList<>();
        Failure failure = mock(Failure.class);
        when(failure.getMessage()).thenReturn(testFailureMessage);
        when(failure.getTestHeader()).thenReturn(testName);
        value.add(failure);
        when(result.getFailureCount()).thenReturn(1);
        when(result.getFailures()).thenReturn(value);
    }

    private AcceptanceTestsState getAcceptanceTestsStateWithMockedResult() {
        AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
        acceptanceTestsState.setResult(result);
        return acceptanceTestsState;
    }

}
