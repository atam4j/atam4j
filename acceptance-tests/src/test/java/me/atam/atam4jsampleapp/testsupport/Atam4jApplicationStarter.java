package me.atam.atam4jsampleapp.testsupport;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;
import me.atam.atam4jsampleapp.ApplicationConfiguration;
import me.atam.atam4jsampleapp.Atam4jTestApplication;

public class Atam4jApplicationStarter {

    public static DropwizardTestSupport<ApplicationConfiguration> startApplicationWith(Class testClass) {
        return startApplicationWith(testClass, 1);
    }

    public static DropwizardTestSupport<ApplicationConfiguration> startApplicationWith(Class testClass, int initialDelayInMillis) {
        DropwizardTestSupport<ApplicationConfiguration> applicationConfigurationDropwizardTestSupport =
                new DropwizardTestSupport<>(Atam4jTestApplication.class,
                        ResourceHelpers.resourceFilePath("app-config.yml"),
                        ConfigOverride.config("testClasses", testClass.getName()),
                        ConfigOverride.config("initialDelayInMillis", initialDelayInMillis + ""));
        applicationConfigurationDropwizardTestSupport.before();
        return applicationConfigurationDropwizardTestSupport;
    }

}
