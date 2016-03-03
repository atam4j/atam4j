package me.atam.atam4j.dummytests;

import me.atam.atam4j.Monitor;
import org.junit.Assert;
import org.junit.Test;


@Monitor
public class PassingTestWithNoCategory {

    @Test
    public void testThatPasses(){
        Assert.assertTrue(true);
    }
}
