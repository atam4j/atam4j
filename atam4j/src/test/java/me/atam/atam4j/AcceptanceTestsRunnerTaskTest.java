package me.atam.atam4j;

import me.atam.atam4j.dummytests.FailingTest;
import me.atam.atam4j.dummytests.PassingTestWithNoCategory;
import me.atam.atam4j.dummytests.TestThatFailsOnInitialisation;
import me.atam.atam4j.health.AcceptanceTestsState;
import me.atam.atam4j.logging.LoggingListener;
import org.junit.Test;
import org.junit.runner.Result;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

public class AcceptanceTestsRunnerTaskTest {

    AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
    TestLogger logger = TestLoggerFactory.getTestLogger(LoggingListener.class);

    @Test
    public void testPassingTestsRun(){
        assertTrue(runTestsAndGetResult(PassingTestWithNoCategory.class).wasSuccessful());
    }

    @Test
    public void testFailingTestFailsAndErrorIsLogged(){
        assertFalse(runTestsAndGetResult(FailingTest.class).wasSuccessful());
        assertThat(logger.getLoggingEvents(), hasItem(LoggingEventWithThrowableMatcher.hasThrowableThatContainsString("Was expecting false to be true")));
    }

    @Test
    public void testPassingAndFailingTestReportsFailure() {
        assertFalse(runTestsAndGetResult(FailingTest.class, PassingTestWithNoCategory.class).wasSuccessful());
    }

    @Test
    public void testThatExceptionFromTestsGetsLogged() {
        assertFalse(runTestsAndGetResult(TestThatFailsOnInitialisation.class).wasSuccessful());
        assertThat(logger.getLoggingEvents(), hasItem(LoggingEventWithThrowableMatcher.hasThrowableThatContainsString("Nasty Exception")));
    }

    private Result runTestsAndGetResult(Class... passingTests) {
        new AcceptanceTestsRunnerTask(acceptanceTestsState, asList(new TestRunListener()), passingTests).run();
        return acceptanceTestsState.getResult().get();
    }
}
