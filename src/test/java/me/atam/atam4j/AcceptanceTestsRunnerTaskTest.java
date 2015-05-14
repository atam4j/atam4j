package me.atam.atam4j;

import me.atam.atam4j.health.AcceptanceTestsState;
import me.atam.atam4j.ignore.FailingTest;
import me.atam.atam4j.ignore.PassingTest;
import org.junit.Test;
import org.junit.runner.Result;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AcceptanceTestsRunnerTaskTest {

    AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();

    @Test
    public void testPassingTestsRun(){
        Class[] passingTests = {PassingTest.class};
        assertTrue(runTestsAndGetResult(passingTests).wasSuccessful());
    }

    @Test
    public void testFailingTestFails(){
        Class[] passingTests = {FailingTest.class};
        assertFalse(runTestsAndGetResult(passingTests).wasSuccessful());
    }

    @Test
    public void testPassingAndFailingTestReportsFailure(){
        Class[] passingTests = {FailingTest.class, PassingTest.class};
        assertFalse(runTestsAndGetResult(passingTests).wasSuccessful());
    }

    private Result runTestsAndGetResult(Class[] passingTests) {
        new AcceptanceTestsRunnerTask(acceptanceTestsState, passingTests).run();
        return acceptanceTestsState.getResult().get();
    }

}
