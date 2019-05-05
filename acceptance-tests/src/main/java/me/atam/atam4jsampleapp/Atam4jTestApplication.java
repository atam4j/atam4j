package me.atam.atam4jsampleapp;

import com.google.common.io.Resources;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import me.atam.atam4j.Atam4j;
import me.atam.atam4jsampleapp.resources.CustomListenerStatusResource;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Atam4jTestApplication extends Application<ApplicationConfiguration> {

    public static void main(String[] args) throws Exception {
        if (args == null || args.length == 0) {
            args = new String[]{"server", new File(Resources.getResource("atam4j-config.yml").toURI()).getAbsolutePath()};
        }

        new Atam4jTestApplication().run(args);
    }

    @Override
    public void run(final ApplicationConfiguration configuration, final Environment environment) throws Exception {
        CustomListenerStatus customListenerStatus = new CustomListenerStatus();
        Atam4j atam4j = new Atam4j.Atam4jBuilder(environment)
                .withUnit(TimeUnit.MILLISECONDS)
                .withInitialDelay(configuration.getInitialDelayInMillis())
                .withPeriod(configuration.getPeriodInMillis())
                .withTestClasses(configuration.getTestClasses())
                .withListener(new CustomListener(customListenerStatus))
                .withTestMetrics()
                .build();

        environment.lifecycle().manage(atam4j);
        environment.jersey().register(new CustomListenerStatusResource(customListenerStatus));
    }
}
