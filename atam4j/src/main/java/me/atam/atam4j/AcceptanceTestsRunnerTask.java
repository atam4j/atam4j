package me.atam.atam4j;

import me.atam.atam4j.health.AcceptanceTestsState;
import me.atam.atam4j.logging.LoggingListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcceptanceTestsRunnerTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptanceTestsRunnerTask.class);

    private final AcceptanceTestsState testsState;
    private final Class[] testClasses;
    private TestRunListener testRunListener;

    AcceptanceTestsRunnerTask(final AcceptanceTestsState testsState,
                              final TestRunListener testRunListener,
                              final Class... testClasses) {
        this.testsState = testsState;
        this.testClasses = testClasses;
        this.testRunListener = testRunListener;
    }

    @Override
    public void run() {
        JUnitCore jUnitCore = new JUnitCore();

        jUnitCore.addListener(new LoggingListener());

        jUnitCore.addListener(testRunListener);

        final Result result = jUnitCore.run(testClasses);
        testsState.setResult(result);
    }
}
