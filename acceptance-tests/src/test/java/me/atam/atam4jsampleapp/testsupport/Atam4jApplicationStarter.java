package me.atam.atam4jsampleapp.testsupport;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;
import me.atam.atam4jsampleapp.ApplicationConfiguration;
import me.atam.atam4jsampleapp.Atam4jTestApplication;

public class Atam4jApplicationStarter {

    public static DropwizardTestSupport<ApplicationConfiguration> startApplicationWith(int initialDelayInMillis, Class testClass) {
        DropwizardTestSupport<ApplicationConfiguration> applicationConfigurationDropwizardTestSupport =
                new DropwizardTestSupport<>(Atam4jTestApplication.class,
                        ResourceHelpers.resourceFilePath("app-config-with-one-test.yml"),
                        ConfigOverride.config("testClasses", testClass.getName()),
                        ConfigOverride.config("initialDelayInMillis", String.valueOf(initialDelayInMillis)));
        applicationConfigurationDropwizardTestSupport.before();
        return applicationConfigurationDropwizardTestSupport;
    }

    public static DropwizardTestSupport<ApplicationConfiguration> startApplicationWithPassingAndFailingTest(int initialDelayInMillis) {
        DropwizardTestSupport<ApplicationConfiguration> applicationConfigurationDropwizardTestSupport =
                new DropwizardTestSupport<>(Atam4jTestApplication.class,
                        ResourceHelpers.resourceFilePath("app-config-with-passing-and-failing-test.yml"),
                        ConfigOverride.config("initialDelayInMillis", String.valueOf(initialDelayInMillis)));
        applicationConfigurationDropwizardTestSupport.before();
        return applicationConfigurationDropwizardTestSupport;
    }
}
