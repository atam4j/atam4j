package me.atam.atam4j.ignore;

import me.atam.atam4j.Monitor;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@Monitor
public class PassingTest {

    @Test
    public void testThatPasses(){
        assertTrue(true);
    }
}
