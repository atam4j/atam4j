package me.atam.atam4j;

import me.atam.atam4j.health.AcceptanceTestsState;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class AcceptanceTestsRunnerTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptanceTestsRunnerTask.class);
    private static AcceptanceTestsState testsState;

    Class testClasses[];

    public AcceptanceTestsRunnerTask(AcceptanceTestsState testsState, Class[] testClasses) {
        this.testClasses = testClasses;
        AcceptanceTestsRunnerTask.testsState = testsState;
    }

    @Override
    public void run() {
        LOGGER.info("Starting tests at {}", new Date());

        Result result = JUnitCore.runClasses(testClasses);
        testsState.setResult(result);

        LOGGER.info("Tests finishes at {}", new Date());
        LOGGER.info("Report :: total run = {}, failures = {}, in time = {} milliseconds",
                result.getRunCount(),
                result.getFailureCount(),
                result.getRunTime()
        );
    }


}
