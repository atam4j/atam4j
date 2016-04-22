package me.atam.atam4j.dummytests;


import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static junit.framework.TestCase.assertTrue;

public class TestThatCanBePaused {

    public static Lock lock = new ReentrantLock();

    @Test
    public void passingTestThatCanBePaused(){
        try{
            lock.lock();
            assertTrue(true);
        }
        finally {
            lock.unlock();
        }

    }
}
