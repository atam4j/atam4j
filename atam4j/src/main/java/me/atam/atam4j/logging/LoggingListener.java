package me.atam.atam4j.logging;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class LoggingListener extends RunListener {
    private static Logger LOGGER = LoggerFactory.getLogger(LoggingListener.class);

    @Override
    public void testRunStarted(Description description) throws Exception {
        LOGGER.info("Starting tests at {}", new Date());
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        LOGGER.info("Tests finishes at {}", new Date());
        LOGGER.info("Report :: total run = {}, failures = {}, in time = {} milliseconds",
                result.getRunCount(),
                result.getFailureCount(),
                result.getRunTime()
        );
    }

    @Override
    public void testStarted(Description description) throws Exception {
        LOGGER.debug("Starting {}", description.getDisplayName());
    }

    @Override
    public void testFinished(Description description) throws Exception {
        LOGGER.debug("Finished {}", description.getDisplayName());
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        LOGGER.error(
            String.format("Test %s failed: %s",
                failure.getTestHeader(),
                failure.getDescription()
            ),
            failure.getException()
        );
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        LOGGER.error(
            String.format("Test %s assumption failed: %s",
                    failure.getTestHeader(),
                    failure.getDescription()
            ),
            failure.getException()
        );
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        LOGGER.debug("Test {} ignored: ", description.getDisplayName());
    }
}
