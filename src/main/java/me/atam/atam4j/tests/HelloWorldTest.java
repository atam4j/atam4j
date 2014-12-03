package me.atam.atam4j.tests;

import org.junit.Assert;
import org.junit.Test;

public class HelloWorldTest {

    @Test
    public void testHelloWorld() throws Exception {
        System.out.println("Hello World Test!");
        Assert.assertEquals(true, true);
    }
}
