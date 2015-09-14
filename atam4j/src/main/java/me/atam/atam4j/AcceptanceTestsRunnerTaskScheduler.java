package me.atam.atam4j;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import me.atam.atam4j.health.AcceptanceTestsState;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AcceptanceTestsRunnerTaskScheduler {

    private final Class[] testClasses;
    private final long initialDelay;
    private final long period;
    private final TimeUnit unit;
    private final ScheduledExecutorService scheduler;

    public AcceptanceTestsRunnerTaskScheduler(final Class[] testClasses,
                                              final long initialDelay,
                                              final long period,
                                              final TimeUnit unit) {
        this.testClasses = testClasses;
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(
                new ThreadFactoryBuilder()
                        .setNameFormat("acceptance-tests-runner")
                        .setDaemon(false)
                        .build());
    }

    public void scheduleAcceptanceTestsRunnerTask(final AcceptanceTestsState acceptanceTestsState) {
        scheduler.scheduleAtFixedRate(
                new AcceptanceTestsRunnerTask(acceptanceTestsState, testClasses),
                initialDelay,
                period,
                unit);
    }
}
