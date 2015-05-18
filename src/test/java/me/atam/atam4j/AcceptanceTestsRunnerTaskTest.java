package me.atam.atam4j;

import me.atam.atam4j.health.AcceptanceTestsState;
import me.atam.atam4j.ignore.FailingTest;
import me.atam.atam4j.ignore.PassingTest;

import me.atam.atam4j.ignore.TestThatFailsOnInitialisation;
import org.junit.Test;
import org.junit.runner.Result;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AcceptanceTestsRunnerTaskTest {

    AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
    TestLogger logger = TestLoggerFactory.getTestLogger(AcceptanceTestsRunnerTask.class);

    @Test
    public void testPassingTestsRun(){
        Class[] passingTests = {PassingTest.class};
        assertTrue(runTestsAndGetResult(passingTests).wasSuccessful());
    }

    @Test
    public void testPassingTestRunsUsingAnnotation(){
        assertTrue(runTestsAndGetResult().wasSuccessful());
    }

    @Test
    public void testFailingTestFailsAndErrorIsLogged(){
        Class[] failingTest = {FailingTest.class};
        assertFalse(runTestsAndGetResult(failingTest).wasSuccessful());
        assertThat(logger.getLoggingEvents(), hasItem(LoggingEventWithThrowableMatcher.hasThrowableThatContainsString("Was expecting false to be true")));
    }

    @Test
    public void testPassingAndFailingTestReportsFailure() {
        Class[] passingAndFailingTest = {FailingTest.class, PassingTest.class};
        assertFalse(runTestsAndGetResult(passingAndFailingTest).wasSuccessful());
    }

    @Test
    public void testThatExceptionFromTestsGetsLogged() {
        Class[] testThatFailsToBeInitialised = {TestThatFailsOnInitialisation.class};
        assertFalse(runTestsAndGetResult(testThatFailsToBeInitialised ).wasSuccessful());
        assertThat(logger.getLoggingEvents(), hasItem(LoggingEventWithThrowableMatcher.hasThrowableThatContainsString("Nasty Exception")));
    }

    private Result runTestsAndGetResult(Class[] passingTests) {
        new AcceptanceTestsRunnerTask(acceptanceTestsState, Optional.of(passingTests)).run();
        return acceptanceTestsState.getResult().get();
    }

    private Result runTestsAndGetResult() {
        new AcceptanceTestsRunnerTask(acceptanceTestsState, empty()).run();
        return acceptanceTestsState.getResult().get();
    }
}
