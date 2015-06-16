package me.atam.atam4j.ignore;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestThatFailsOnInitialisation {

    @Before
    public void setUp() throws Exception {
        throw new RuntimeException("Nasty Exception on setUp()");
    }

    @Test
    public void testThatWouldPassIfRun(){
        assertTrue(true);
    }
}
