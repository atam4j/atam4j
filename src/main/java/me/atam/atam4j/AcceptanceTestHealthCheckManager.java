package me.atam.atam4j;

import io.dropwizard.lifecycle.setup.ScheduledExecutorServiceBuilder;
import io.dropwizard.setup.Environment;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import me.atam.atam4j.health.AcceptanceTestsState;

import java.util.concurrent.TimeUnit;

public class AcceptanceTestHealthCheckManager {

    public static final int TEN_MINUTES_IN_SECONDS = 60 * 10;

    private Environment environment;
    private Class testClasses[];
    private int testsInterval;

    public AcceptanceTestHealthCheckManager(Environment environment, Class ... testClasses) {
        this.environment = environment;
        this.testClasses = testClasses;
        this.testsInterval = TEN_MINUTES_IN_SECONDS;
    }

    public AcceptanceTestHealthCheckManager(Environment environment, int testsInterval, Class ... testClasses) {
        this.testsInterval = testsInterval;
        this.environment = environment;
        this.testClasses = testClasses;
    }

    public void initialise() {
        AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();

        ScheduledExecutorServiceBuilder executorServiceBuilder = environment.lifecycle().
                scheduledExecutorService("acceptance-tests-runner");

        executorServiceBuilder.build().scheduleAtFixedRate(
                new AcceptanceTestsRunnerTask(acceptanceTestsState, testClasses),
                2,
                testsInterval,
                TimeUnit.SECONDS);

        environment.healthChecks().register("Track Acceptance Tests HealthCheck", new AcceptanceTestsHealthCheck(acceptanceTestsState));
    }
}
