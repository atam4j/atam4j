package me.atam.atam4j.dummytests;

import me.atam.atam4j.Monitor;
import me.atam.atam4j.MonitorCategory;
import org.junit.Assert;
import org.junit.Test;


@Monitor
public class PassingTestWithCategory {

    @MonitorCategory(name = "priority-1")
    @Test
    public void testThatPasses(){
        Assert.assertTrue(true);
    }
}
