package me.atam.atam4j.dummytests;

import me.atam.atam4j.Monitor;
import org.junit.Assert;
import org.junit.Test;


@Monitor
public class PassingTest {

    @Test
    public void testThatPasses(){
        Assert.assertTrue(true);
    }
}
