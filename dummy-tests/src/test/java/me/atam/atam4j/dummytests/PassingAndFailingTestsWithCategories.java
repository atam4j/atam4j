package me.atam.atam4j.dummytests;

import me.atam.atam4j.MonitorCategory;
import org.junit.Assert;
import org.junit.Test;

public class PassingAndFailingTestsWithCategories {

    @Test
    @MonitorCategory(name="A")
    public void testThatPassesWithCategoryA(){
        Assert.assertTrue(true);
    }


    @Test
    @MonitorCategory(name="B")
    public void testThatFailsWithCategoryB(){
        Assert.assertTrue(false);
    }

}
