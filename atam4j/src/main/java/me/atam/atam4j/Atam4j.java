package me.atam.atam4j;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import io.prometheus.client.exporter.MetricsServlet;
import me.atam.atam4j.exceptions.NoTestClassFoundException;
import me.atam.atam4j.health.AcceptanceTestsState;
import me.atam.atam4j.resources.TestStatusResource;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.runner.notification.RunListener;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Atam4j implements Managed {

    private final AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
    private final AcceptanceTestsRunnerTaskScheduler acceptanceTestsRunnerTaskScheduler;
    private final TestRunListener testRunListener;
    private final JerseyEnvironment jerseyEnvironment;

    public Atam4j(JerseyEnvironment jerseyEnvironment,
                  TestRunListener testRunListener,
                  AcceptanceTestsRunnerTaskScheduler acceptanceTestsRunnerTaskScheduler) {

        this.jerseyEnvironment = jerseyEnvironment;
        this.testRunListener = testRunListener;
        this.acceptanceTestsRunnerTaskScheduler = acceptanceTestsRunnerTaskScheduler;
    }


    @Override
    public void start() {
        acceptanceTestsRunnerTaskScheduler.scheduleAcceptanceTestsRunnerTask(acceptanceTestsState);
        jerseyEnvironment.register(new TestStatusResource(testRunListener));
    }

    @Override
    public void stop() {
        acceptanceTestsRunnerTaskScheduler.stop();
    }

    public static class Atam4jBuilder {

        private Optional<Class[]> testClasses = Optional.empty();
        private long initialDelay = 60;
        private long period = 300;
        private TimeUnit unit = TimeUnit.SECONDS;
        private Environment environment;
        private boolean metrics = false;

        private final List<RunListener> runListeners = new ArrayList<>();


        public Atam4jBuilder(Environment environment) {
            this.environment = environment;
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

        public Atam4jBuilder withListener(RunListener listener) {
            this.runListeners.add(listener);
            return this;
        }


        public Atam4jBuilder withTestMetrics(){
            this.metrics = true;
            return this;
        }

        public Atam4j build() {

            if (metrics){
                ServletHolder servletHolder = new ServletHolder(new MetricsServlet());
                environment.getApplicationContext().addServlet(servletHolder, "/metrics");
            }

            TestRunListener testRunListener = new TestRunListener();
            List<RunListener> runListenersWithAtam4jListener = new ArrayList<>();
            runListenersWithAtam4jListener.add(testRunListener);
            runListenersWithAtam4jListener.addAll(this.runListeners);
            return new Atam4j(environment.jersey(), testRunListener,
                    new AcceptanceTestsRunnerTaskScheduler(
                        findTestClasses(),
                        initialDelay,
                        period,
                        unit,
                        runListenersWithAtam4jListener));
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