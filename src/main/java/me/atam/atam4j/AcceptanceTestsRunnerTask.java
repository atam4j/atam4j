package me.atam.atam4j;

import me.atam.atam4j.configuration.AcceptanceTestsBuildConfiguration;
import me.atam.atam4j.configuration.AcceptanceTestsConfiguration;
import me.atam.atam4j.configuration.ConfigLoader;
import me.atam.atam4j.health.AcceptanceTestsState;
import me.atam.atam4j.tests.HelloWorldTest;
import me.atam.atam4j.tests.RunAllTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class AcceptanceTestsRunnerTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptanceTestsRunnerTask.class);
    private static AcceptanceTestsConfiguration config;
    private static AcceptanceTestsState testsState;

    public AcceptanceTestsRunnerTask(AcceptanceTestsConfiguration config, AcceptanceTestsState testsState) {
        AcceptanceTestsRunnerTask.config = config;
        AcceptanceTestsRunnerTask.testsState = testsState;
    }

    @Override
    public void run() {
        LOGGER.info("Starting tests at {}", new Date());

        // Array of test classes to run. New test classes should be added here.
        Class classes[] = {RunAllTest.class, HelloWorldTest.class};

        Result result = JUnitCore.runClasses(classes);
        testsState.setResult(result);

        LOGGER.info("Tests finishes at {}", new Date());
        LOGGER.info("Report :: total run = {}, failures = {}, in time = {} milliseconds",
                result.getRunCount(),
                result.getFailureCount(),
                result.getRunTime()
        );
    }

    public static AcceptanceTestsConfiguration getConfig() {
        if (AcceptanceTestsRunnerTask.config == null) {
            AcceptanceTestsRunnerTask.config =
                    new ConfigLoader<>(AcceptanceTestsBuildConfiguration.class).getTestConfig();
        }
        return AcceptanceTestsRunnerTask.config;
    }
}
