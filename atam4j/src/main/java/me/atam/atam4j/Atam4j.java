package me.atam.atam4j;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import me.atam.atam4j.health.AcceptanceTestsState;
import me.atam.atam4j.resources.TestStatusResource;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Atam4j {

    private final AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
    private final AcceptanceTestsRunnerTaskScheduler acceptanceTestsRunnerTaskScheduler;
    private final TestRunListener testRunListener;
    private final JerseyEnvironment jerseyEnvironment;

    public Atam4j(JerseyEnvironment jerseyEnvironment, TestRunListener testRunListener, AcceptanceTestsRunnerTaskScheduler acceptanceTestsRunnerTaskScheduler) {
        this.jerseyEnvironment = jerseyEnvironment;
        this.testRunListener = testRunListener;
        this.acceptanceTestsRunnerTaskScheduler = acceptanceTestsRunnerTaskScheduler;
    }

    public void initialise() {
        acceptanceTestsRunnerTaskScheduler.scheduleAcceptanceTestsRunnerTask(acceptanceTestsState);
        jerseyEnvironment.register(new TestStatusResource(testRunListener));
    }

    public static class Atam4jBuilder {

        private Optional<Class[]> testClasses = Optional.empty();
        private long initialDelay = 60;
        private long period = 300;
        private TimeUnit unit = TimeUnit.SECONDS;
        private JerseyEnvironment jerseyEnvironment;

        public Atam4jBuilder(JerseyEnvironment jerseyEnvironment) {
            this.jerseyEnvironment = jerseyEnvironment;
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
            TestRunListener testRunListener = new TestRunListener();
            return new Atam4j(jerseyEnvironment, testRunListener,
                    new AcceptanceTestsRunnerTaskScheduler(
                        findTestClasses(),
                        initialDelay,
                        period,
                        unit,
                        testRunListener));
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