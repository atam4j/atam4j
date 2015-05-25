package me.atam.atam4j;

import io.dropwizard.setup.Environment;
import me.atam.atam4j.health.AcceptanceTestsState;

import java.util.concurrent.TimeUnit;

public class AcceptanceTestHealthCheckManager {

    private AcceptanceTestsRunnerTaskScheduler acceptanceTestsRunnerTaskScheduler;
    private AcceptanceTestsHealthCheckInitializer acceptanceTestsHealthCheckInitializer;

    public AcceptanceTestHealthCheckManager(AcceptanceTestsRunnerTaskScheduler acceptanceTestsRunnerTaskScheduler, AcceptanceTestsHealthCheckInitializer acceptanceTestsHealthCheckInitializer) {
        this.acceptanceTestsRunnerTaskScheduler = acceptanceTestsRunnerTaskScheduler;
        this.acceptanceTestsHealthCheckInitializer = acceptanceTestsHealthCheckInitializer;
    }

    public void initialise() {
        acceptanceTestsRunnerTaskScheduler.scheduleAcceptanceTestsRunnerTask();
        acceptanceTestsHealthCheckInitializer.initialize();
    }

    public static class AcceptanceTestsRunnerTaskSchedulerBuilder{

        private AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
        private Environment environment = null;
        private Class testClasses[] = null;
        private long initialDelay = 0;
        private long period = 1;
        private TimeUnit unit = TimeUnit.SECONDS;


        public AcceptanceTestsRunnerTaskSchedulerBuilder withAcceptanceTestsState(AcceptanceTestsState acceptanceTestsState) {
            this.acceptanceTestsState = acceptanceTestsState;
            return this;
        }

        public AcceptanceTestsRunnerTaskSchedulerBuilder withEnvironment(Environment environment) {
            this.environment = environment;
            return this;
        }

        public AcceptanceTestsRunnerTaskSchedulerBuilder withTestClasses(Class[] testClasses) {
            this.testClasses = testClasses;
            return this;
        }

        public AcceptanceTestsRunnerTaskSchedulerBuilder withInitialDelay(long initialDelay) {
            this.initialDelay = initialDelay;
            return this;
        }

        public AcceptanceTestsRunnerTaskSchedulerBuilder withPeriod(long period) {
            this.period = period;
            return this;
        }

        public AcceptanceTestsRunnerTaskSchedulerBuilder withUnit(TimeUnit unit) {
            this.unit = unit;
            return this;
        }

        public AcceptanceTestHealthCheckManager build(){

            if (environment == null){
                throw new IllegalStateException("No Environment specified");
            }

            if (testClasses == null){
                throw new IllegalStateException("No test classes specified");
            }


            return new AcceptanceTestHealthCheckManager(
                    new AcceptanceTestsRunnerTaskScheduler(
                        environment,
                        testClasses,
                        acceptanceTestsState,
                        initialDelay,
                        period,
                        unit),
                    new AcceptanceTestsHealthCheckInitializer(
                        acceptanceTestsState,
                        environment));
        }
    }



}