package me.atam.atam4j.configuration;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigLoaderTest {

    @Test
    public void givenAValidYAMLConfigFile_whenGetConfigInvoked_thenValidPOJOReturned(){
        ConfigLoader<DummyConfiguration> dummyConfigurationConfigLoader = new ConfigLoader<>(DummyConfiguration.class, "config-loader-test.yml");
        assertThat(dummyConfigurationConfigLoader.getTestConfig().getSomeDummyAttribute(), CoreMatchers.is("someValue"));
    }

}
