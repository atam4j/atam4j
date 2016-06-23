package me.atam.atam4j;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import me.atam.atam4j.health.AcceptanceTestsState;
import org.junit.runner.notification.RunListener;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AcceptanceTestsRunnerTaskScheduler {

    private final Class[] testClasses;
    private final long initialDelay;
    private final long period;
    private final TimeUnit unit;
    private final ScheduledExecutorService scheduler;
    private final List<RunListener> runListeners;

    public AcceptanceTestsRunnerTaskScheduler(final Class[] testClasses,
                                              final long initialDelay,
                                              final long period,
                                              final TimeUnit unit,
                                              final List<RunListener> runListeners) {
        this.testClasses = testClasses;
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
        this.runListeners = runListeners;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactoryBuilder()
                        .setNameFormat("acceptance-tests-runner")
                        .setDaemon(false)
                        .build());
    }

    public void scheduleAcceptanceTestsRunnerTask(final AcceptanceTestsState acceptanceTestsState) {
        scheduler.scheduleAtFixedRate(
                new AcceptanceTestsRunnerTask(acceptanceTestsState, runListeners, testClasses),
                initialDelay,
                period,
                unit);
    }
}
