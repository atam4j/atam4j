package me.atam.atam4j.metrics;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import me.atam.atam4j.logging.LoggingListener;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MetricsListener extends RunListener {
    private static Logger LOGGER = LoggerFactory.getLogger(LoggingListener.class);


    static Counter FAILED_TESTS = Counter.build()
            .name("failed_tests_total").labelNames("test_name").help("Total number of tests that have failed.").register();


    static final Counter PASSED_TESTS = Counter.build().labelNames()
            .name("passed_tests_total").help("Total number of tests that have passed.").register();

    static final Gauge TESTS_IN_PROGRESS = Gauge.build().name("tests_in_progress").help("Number of tests currently running").register();


    @Override
    public void testRunStarted(Description description) throws Exception {


    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        int totalRunCount = result.getRunCount();
        int failureCount = result.getFailureCount();
        int passedCount = totalRunCount - failureCount;
        PASSED_TESTS.inc(passedCount);

    }

    @Override
    public void testStarted(Description description) throws Exception {
        TESTS_IN_PROGRESS.inc();
    }

    private String getTestLabelNameFromDescription(Description description) {
        String testNameWithDots = description.getTestClass().getSimpleName() + "." + description.getMethodName();
        return testNameWithDots.replace('.', '_');
    }

    @Override
    public void testFinished(Description description) throws Exception {
        TESTS_IN_PROGRESS.dec();
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        if (FAILED_TESTS != null){

            FAILED_TESTS.labels(getTestLabelNameFromDescription(failure.getDescription())).inc();
        }
    }



    @Override
    public void testAssumptionFailure(Failure failure) {
        FAILED_TESTS.inc();
    }

    @Override
    public void testIgnored(Description description) throws Exception {

    }

}
