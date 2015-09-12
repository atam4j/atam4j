package me.atam.atam4j;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.google.common.base.Preconditions;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import me.atam.atam4j.health.AcceptanceTestsState;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Atam4j {

    private final HealthCheckRegistry healthCheckRegistry;
    private final AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
    private final AcceptanceTestsRunnerTaskScheduler acceptanceTestsRunnerTaskScheduler;

    public Atam4j(final AcceptanceTestsRunnerTaskScheduler acceptanceTestsRunnerTaskScheduler,
                  final HealthCheckRegistry healthCheckRegistry) {
        this.acceptanceTestsRunnerTaskScheduler = acceptanceTestsRunnerTaskScheduler;
        this.healthCheckRegistry = healthCheckRegistry;
    }

    public void initialise() {
        acceptanceTestsRunnerTaskScheduler.scheduleAcceptanceTestsRunnerTask(acceptanceTestsState);
        AcceptanceTestsHealthCheck healthCheck = new AcceptanceTestsHealthCheck(acceptanceTestsState);
        healthCheckRegistry.register(AcceptanceTestsHealthCheck.NAME, healthCheck);
    }

    public static class Atam4jBuilder {

        private HealthCheckRegistry healthCheckRegistry;
        private Optional<Class[]> testClasses = Optional.empty();
        private long initialDelay = 60;
        private long period = 300;
        private TimeUnit unit = TimeUnit.SECONDS;

        public Atam4jBuilder(HealthCheckRegistry healthCheckRegistry) {
            this.healthCheckRegistry = Preconditions.checkNotNull(healthCheckRegistry);
        }

        public Atam4jBuilder() {
        }

        public Atam4jBuilder withTestClasses(Class... testClasses) {
            this.testClasses = Optional.of(testClasses);
            return this;
        }

        public Atam4jBuilder withInitialDelay(long initialDelay) {
            this.initialDelay = initialDelay;
            return this;
        }

        public Atam4jBuilder withPeriod(long period) {
            this.period = period;
            return this;
        }

        public Atam4jBuilder withUnit(TimeUnit unit) {
            this.unit = unit;
            return this;
        }

        public Atam4j build() {
            return new Atam4j(
                    new AcceptanceTestsRunnerTaskScheduler(
                        findTestClasses(),
                        initialDelay,
                        period,
                        unit),
                    healthCheckRegistry);
        }

        private Class[] findTestClasses() {
            final Class[] classes = testClasses.orElseGet(() ->
                    new Reflections(new ConfigurationBuilder()
                            .setUrls(ClasspathHelper.forJavaClassPath())
                            .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()))
                            .getTypesAnnotatedWith(Monitor.class)
                            .stream()
                            .toArray(Class[]::new));
            if(classes.length == 0) {
                throw new NoTestClassFoundException("Could not find any annotated test classes and no classes were provided via the Atam4jBuilder.");
            }
            return classes;
        }
    }
}