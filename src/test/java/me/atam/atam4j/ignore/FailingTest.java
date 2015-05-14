package me.atam.atam4j.ignore;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FailingTest {


    @Test
    public void testThatFails(){
        assertTrue("Was expecting false to be true",false);
    }

}
