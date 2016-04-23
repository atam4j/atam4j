package me.atam.atam4j.dummytests;


import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertTrue;


public class TestThatKnowsIfItsBeingRun {

    private static final int DELAY = 10;
    public static final int NUMBER_OF_TESTS = 10;

    private static AtomicInteger testCounter = new AtomicInteger(0);

    public static boolean testInProgress(){
        return testCounter.get() % NUMBER_OF_TESTS != 0;
    }

    @Test
    public void test1() throws InterruptedException {
        sleepForAPeriodOfTimeAndIncrementTestCounter();
        assertTrue(true);
    }

    @Test
    public void test2() throws InterruptedException {
        sleepForAPeriodOfTimeAndIncrementTestCounter();
        assertTrue(true);
    }

    @Test
    public void test3() throws InterruptedException {
        sleepForAPeriodOfTimeAndIncrementTestCounter();
        assertTrue(true);
    }

    @Test
    public void test4() throws InterruptedException {
        sleepForAPeriodOfTimeAndIncrementTestCounter();
        assertTrue(true);
    }

    @Test
    public void test5() throws InterruptedException {
        sleepForAPeriodOfTimeAndIncrementTestCounter();
        assertTrue(true);
    }

    @Test
    public void test6() throws InterruptedException {
        sleepForAPeriodOfTimeAndIncrementTestCounter();
        assertTrue(true);
    }

    @Test
    public void test7() throws InterruptedException {
        sleepForAPeriodOfTimeAndIncrementTestCounter();
        assertTrue(true);
    }

    @Test
    public void test8() throws InterruptedException {
        sleepForAPeriodOfTimeAndIncrementTestCounter();
        assertTrue(true);
    }

    @Test
    public void test9() throws InterruptedException {
        sleepForAPeriodOfTimeAndIncrementTestCounter();
        assertTrue(true);
    }

    @Test
    public void test10() throws InterruptedException {
        sleepForAPeriodOfTimeAndIncrementTestCounter();
        assertTrue(true);
    }



    private void sleepForAPeriodOfTimeAndIncrementTestCounter() throws InterruptedException {
        testCounter.incrementAndGet();
        Thread.sleep(DELAY);
    }
}
