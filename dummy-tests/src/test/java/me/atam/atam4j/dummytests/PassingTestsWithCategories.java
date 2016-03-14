package me.atam.atam4j.dummytests;

import me.atam.atam4j.Monitor;
import me.atam.atam4j.MonitorCategory;
import org.junit.Assert;
import org.junit.Test;

@Monitor
public class PassingTestsWithCategories {

    @Test
    @MonitorCategory(name="A")
    public void testThatPassesWithCategoryA(){
        Assert.assertTrue(true);
    }


    @Test
    @MonitorCategory(name="B")
    public void testThatPassesWithCategoryB(){
        Assert.assertTrue(true);
    }

}
