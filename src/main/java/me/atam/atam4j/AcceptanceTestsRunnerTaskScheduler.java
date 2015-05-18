package me.atam.atam4j;

import io.dropwizard.setup.Environment;
import me.atam.atam4j.health.AcceptanceTestsState;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class AcceptanceTestsRunnerTaskScheduler {

    private final Environment environment;
    private final Optional<Class[]> testClasses;
    private final long initialDelay;
    private final long period;
    private final TimeUnit unit;

    public AcceptanceTestsRunnerTaskScheduler(final Environment environment,
                                              final Optional<Class[]> testClasses,
                                              final long initialDelay,
                                              final long period,
                                              final TimeUnit unit) {
        this.environment = environment;
        this.testClasses = testClasses;
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
    }

    public void scheduleAcceptanceTestsRunnerTask(final AcceptanceTestsState acceptanceTestsState) {
        environment.lifecycle().
                scheduledExecutorService("acceptance-tests-runner").build().scheduleAtFixedRate(
                new AcceptanceTestsRunnerTask(acceptanceTestsState, testClasses),
                initialDelay,
                period,
                unit);
    }
}
