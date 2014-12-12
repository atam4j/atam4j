package me.atam.atam4j;

import com.google.common.io.Resources;
import io.dropwizard.Application;
import io.dropwizard.lifecycle.setup.ScheduledExecutorServiceBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.atam.atam4j.configuration.AcceptanceTestsAppConfiguration;
import me.atam.atam4j.configuration.ConfigLoader;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import me.atam.atam4j.health.AcceptanceTestsState;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class AcceptanceTestsMonitor extends Application<AcceptanceTestsAppConfiguration> {

    public static final int TEN_MINUTES_IN_SECONDS = 60 * 10;

    public static void main(String[] args) throws Exception {
        if (args == null || args.length==0) {
            args = new String[]{"server", new File(Resources.getResource("app-config.yml").toURI()).getAbsolutePath()};
        }

        new AcceptanceTestsMonitor().run(args);
    }

    @Override
    public void initialize(final Bootstrap bootstrap) {

    }

    @Override
    public void run(final AcceptanceTestsAppConfiguration configuration, final Environment environment) throws Exception {

        // enable starting dw app without any resources defined
        environment.jersey().disable();

        // scheduling acceptance tests
        AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();

        ScheduledExecutorServiceBuilder executorServiceBuilder = environment.
                lifecycle().
                scheduledExecutorService("acceptance-tests-runner");

        executorServiceBuilder.build().scheduleAtFixedRate(
                new AcceptanceTestsRunnerTask(configuration, acceptanceTestsState),
                2,
                TEN_MINUTES_IN_SECONDS,
                TimeUnit.SECONDS);

        environment.healthChecks().register("Track Acceptance Tests HealthCheck", new AcceptanceTestsHealthCheck(acceptanceTestsState));
    }
}
