package me.atam.atam4j.configuration;

import org.apache.commons.lang3.text.StrLookup;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigLoaderTest {

    private static final String CONFIG_FILE = "config-loader-test.yml";

    @Test
    public void givenAValidYAMLConfigFile_whenGetConfigInvoked_thenValidPOJOReturned() {
        ConfigLoader<DummyConfiguration> dummyConfigurationConfigLoader = new ConfigLoader<>(
                DummyConfiguration.class,
                CONFIG_FILE
        );
        assertThat(dummyConfigurationConfigLoader.getTestConfig().getSomeDummyAttribute(), CoreMatchers.is("someValue"));
    }

    @Test
    public void givenAValidYAMLConfigFileContainingEnvVar_whenGetConfigInvoked_thenValidPOJOReturned() {
        ConfigLoader<DummyConfiguration> dummyConfigurationConfigLoader = new ConfigLoader<>(
                DummyConfiguration.class,
                CONFIG_FILE,
                varFound
        );
        assertThat(dummyConfigurationConfigLoader.getTestConfig().getSomeDummyEnvAttribute(), CoreMatchers.is("envValue"));
    }

    @Test
    public void givenAValidYAMLConfigFileContainingEnvVarThatCannotBeFound_whenGetConfigInvoked_thenValidPOJOReturnedWithDefault() {
        ConfigLoader<DummyConfiguration> dummyConfigurationConfigLoader = new ConfigLoader<>(
                DummyConfiguration.class,
                CONFIG_FILE,
                varNotFound
        );
        assertThat(dummyConfigurationConfigLoader.getTestConfig().getSomeDummyEnvAttribute(), CoreMatchers.is("defaultValue"));
    }

    private final StrLookup<Object> varFound = new StrLookup<Object>() {
        @Override
        public String lookup(String key) {
            return "envValue";
        }
    };

    private final StrLookup<Object> varNotFound = new StrLookup<Object>() {
        @Override
        public String lookup(String key) {
            return null;
        }
    };
}
