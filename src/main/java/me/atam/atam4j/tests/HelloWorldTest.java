package me.atam.atam4j.tests;

import me.atam.atam4j.configuration.ConfigLoader;
import me.atam.atam4j.configuration.TestConfiguration;
import org.junit.Assert;
import org.junit.Test;

public class HelloWorldTest {

    TestConfiguration testConfig = new ConfigLoader<>(TestConfiguration.class).getTestConfig();

    @Test
    public void testHelloWorld() throws Exception {
        System.out.println("Hello World Test!");
        Assert.assertEquals("bar", testConfig.getFoo());
    }
}
