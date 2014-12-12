package me.atam.atam4j;

import com.google.common.io.Resources;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.atam.atam4j.tests.HelloWorldTest;

import java.io.File;

public class SampleApplication extends Application<Configuration> {


    public static void main(String[] args) throws Exception {
        if (args == null || args.length==0) {
            args = new String[]{"server", new File(Resources.getResource("app-config.yml").toURI()).getAbsolutePath()};
        }

        new SampleApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap bootstrap) {

    }

    @Override
    public void run(final Configuration configuration, final Environment environment) throws Exception {

        // enable starting dw app without any resources defined
        environment.jersey().disable();

        new AcceptanceTestHealthCheckManager(environment, HelloWorldTest.class).initialise();

    }


}
