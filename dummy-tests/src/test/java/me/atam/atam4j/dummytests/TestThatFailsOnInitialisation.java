package me.atam.atam4j.dummytests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestThatFailsOnInitialisation {

    @Before
    public void setUp() throws Exception {
        throw new RuntimeException("Nasty Exception on setUp()");
    }

    @Test
    public void testThatWouldPassIfRun(){
        Assert.assertTrue(true);
    }
}
