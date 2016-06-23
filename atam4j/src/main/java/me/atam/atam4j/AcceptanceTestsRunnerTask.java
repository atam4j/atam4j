package me.atam.atam4j;

import me.atam.atam4j.health.AcceptanceTestsState;
import me.atam.atam4j.logging.LoggingListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import java.util.List;

public class AcceptanceTestsRunnerTask implements Runnable {

    private final AcceptanceTestsState testsState;
    private final Class[] testClasses;
    private List<RunListener> runListeners;

    AcceptanceTestsRunnerTask(final AcceptanceTestsState testsState,
                              final List<RunListener> runListeners,
                              final Class... testClasses) {
        this.testsState = testsState;
        this.testClasses = testClasses;
        this.runListeners = runListeners;
    }

    @Override
    public void run() {
        JUnitCore jUnitCore = new JUnitCore();

        jUnitCore.addListener(new LoggingListener());

        for (RunListener runListener : runListeners) {
            jUnitCore.addListener(runListener);
        }

        final Result result = jUnitCore.run(testClasses);
        testsState.setResult(result);
    }
}