package me.atam.atam4j;

import me.atam.atam4j.health.AcceptanceTestsState;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class AcceptanceTestsRunnerTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcceptanceTestsRunnerTask.class);
    private static AcceptanceTestsState testsState;

    private final Class[] testClasses;

    AcceptanceTestsRunnerTask(final AcceptanceTestsState testsState, final Optional<Class[]> testClasses) {
        AcceptanceTestsRunnerTask.testsState = testsState;
        this.testClasses = getTestClasses(testClasses);
    }

    @Override
    public void run() {
        LOGGER.info("Starting tests at {}", new Date());

        Result result = JUnitCore.runClasses(testClasses);
        testsState.setResult(result);

        LOGGER.info("Tests finishes at {}", new Date());
        LOGGER.info("Report :: total run = {}, failures = {}, in time = {} milliseconds",
                result.getRunCount(),
                result.getFailureCount(),
                result.getRunTime()
        );

        for (Failure failure: result.getFailures()) {
            LOGGER.error(failure.getDescription().toString(), failure.getException());
        }
    }

    private Class<?>[] getTestClasses(final Optional<Class[]> testClasses) {
        return testClasses.orElseGet(() -> {
            final Set<Class<?>> monitors = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forJavaClassPath())
                    .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()))
                    .getTypesAnnotatedWith(Monitor.class);
            return monitors.toArray(new Class[monitors.size()]);
        });
    }
}
