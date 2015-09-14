package me.atam.atam4j.dummytests;

import org.junit.Assert;
import org.junit.Test;


public class FailingTest {

    @Test
    public void testThatFails(){
        Assert.assertTrue("Was expecting false to be true", false);
    }
}
