package me.atam.atam4jsampleapp;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;

public class Atam4JApplicationStarter {

    public static DropwizardTestSupport<ApplicationConfiguration> startApplicationWith(Class testClass) {
        return startApplicationWith(testClass, 1);
    }

    public static DropwizardTestSupport<ApplicationConfiguration> startApplicationWith(Class testClass, int initialDelayInMillis) {
        DropwizardTestSupport<ApplicationConfiguration> applicationConfigurationDropwizardTestSupport =
                new DropwizardTestSupport<>(Atam4JTestApplication.class, ResourceHelpers.resourceFilePath("app-config.yml"),
                        ConfigOverride.config("testClasses", testClass.getName()),
                        ConfigOverride.config("initialDelayInMillis", initialDelayInMillis + ""));
        applicationConfigurationDropwizardTestSupport.before();
        return applicationConfigurationDropwizardTestSupport;
    }

}
