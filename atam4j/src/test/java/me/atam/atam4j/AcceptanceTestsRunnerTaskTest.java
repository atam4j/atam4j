package me.atam.atam4j;

import me.atam.atam4j.health.AcceptanceTestsState;
import me.atam.atam4j.ignore.FailingTest;
import me.atam.atam4j.ignore.PassingTest;

import me.atam.atam4j.ignore.TestThatFailsOnInitialisation;
import org.junit.Test;
import org.junit.runner.Result;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AcceptanceTestsRunnerTaskTest {

    AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
    TestLogger logger = TestLoggerFactory.getTestLogger(AcceptanceTestsRunnerTask.class);

    @Test
    public void testPassingTestsRun(){
        assertTrue(runTestsAndGetResult(PassingTest.class).wasSuccessful());
    }

    @Test
    public void testFailingTestFailsAndErrorIsLogged(){
        assertFalse(runTestsAndGetResult(FailingTest.class).wasSuccessful());
        assertThat(logger.getLoggingEvents(), hasItem(LoggingEventWithThrowableMatcher.hasThrowableThatContainsString("Was expecting false to be true")));
    }

    @Test
    public void testPassingAndFailingTestReportsFailure() {
        assertFalse(runTestsAndGetResult(FailingTest.class, PassingTest.class).wasSuccessful());
    }

    @Test
    public void testThatExceptionFromTestsGetsLogged() {
        assertFalse(runTestsAndGetResult(TestThatFailsOnInitialisation.class).wasSuccessful());
        assertThat(logger.getLoggingEvents(), hasItem(LoggingEventWithThrowableMatcher.hasThrowableThatContainsString("Nasty Exception")));
    }

    private Result runTestsAndGetResult(Class... passingTests) {
        new AcceptanceTestsRunnerTask(acceptanceTestsState, passingTests).run();
        return acceptanceTestsState.getResult().get();
    }
}
