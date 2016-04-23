package me.atam.atam4j.dummytests;


import org.junit.Test;
import sun.swing.DefaultLayoutStyle;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static junit.framework.TestCase.assertTrue;

public class TestsThatTakeTimeAndCanPassOrFail {

    public static volatile boolean SHOULD_PASS = true;
    private static volatile int DELAY = 0;


    public synchronized static void setDelay(int delay){
        DELAY = delay;
    }


    public synchronized static int getDelay(){
        return DELAY;
    }



    @Test
    public void test1() throws InterruptedException {
        sleepForAPeriodOfTime();
        assertTrue(SHOULD_PASS);
    }

    @Test
    public void test2() throws InterruptedException {
        sleepForAPeriodOfTime();
        assertTrue(SHOULD_PASS);
    }

    @Test
    public void test3() throws InterruptedException {
        sleepForAPeriodOfTime();
        assertTrue(SHOULD_PASS);
    }

    @Test
    public void test4() throws InterruptedException {
        sleepForAPeriodOfTime();
        assertTrue(SHOULD_PASS);
    }

    @Test
    public void test5() throws InterruptedException {
        sleepForAPeriodOfTime();
        assertTrue(SHOULD_PASS);
    }

    @Test
    public void test6() throws InterruptedException {
        sleepForAPeriodOfTime();
        assertTrue(SHOULD_PASS);
    }

    private void sleepForAPeriodOfTime() throws InterruptedException {
        Thread.sleep(getDelay());
    }
}
