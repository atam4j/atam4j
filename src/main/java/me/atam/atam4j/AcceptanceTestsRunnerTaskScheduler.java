package me.atam.atam4j;

import io.dropwizard.setup.Environment;
import me.atam.atam4j.health.AcceptanceTestsState;

import java.util.concurrent.TimeUnit;

public class AcceptanceTestsRunnerTaskScheduler {

    private Environment environment;
    private Class testClasses[];
    private AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
    private long initialDelay;
    private long period;
    private TimeUnit unit;

    public AcceptanceTestsRunnerTaskScheduler(Environment environment, Class[] testClasses, AcceptanceTestsState acceptanceTestsState, long initialDelay, long period, TimeUnit unit) {
        this.environment = environment;
        this.testClasses = testClasses;
        this.acceptanceTestsState = acceptanceTestsState;
        this.initialDelay = initialDelay;
        this.period = period;
        this.unit = unit;
    }

    public void scheduleAcceptanceTestsRunnerTask() {
        AcceptanceTestsRunnerTask acceptanceTestsRunnerTask = new AcceptanceTestsRunnerTask(acceptanceTestsState, testClasses);
        environment.lifecycle().
                scheduledExecutorService("acceptance-tests-runner").build().scheduleAtFixedRate(
                acceptanceTestsRunnerTask,
                initialDelay,
                period,
                unit);
    }

}
