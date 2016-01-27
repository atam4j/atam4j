package me.atam.atam4j;

import me.atam.atam4jdomain.TestsRunResult;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestRunListenerTest {

    private TestRunListener testRunListener = new TestRunListener();

    @Test
    public void givenTestRunNotFinished_whenGetTestRunResultCalled_thenStatusIsTooEarly() throws Exception {
        assertThat(testRunListener.getTestsRunResult().getStatus(), CoreMatchers.is(TestsRunResult.Status.TOO_EARLY));
    }

    @Test
    public void givenFirstTestRunPassesAndSecondTestRunFails_whenGetTestRunResultCalled_thenFailuresReturned()throws Exception{
        //given 1st run
        testRunOf(() -> passingTest(getMockedTestDescription("testThatPasses", "com.blah.MyTest")));
        //given 2nd run
        testRunOf(() -> failingTest(getMockedTestDescription("testThatFails", "com.blah.MyTest")));
        //when
        TestsRunResult secondResult = testRunListener.getTestsRunResult();
        //then
        assertThat(secondResult.getStatus(), CoreMatchers.is(TestsRunResult.Status.FAILURES));
    }

    private void testRunOf(Runnable runnable) throws Exception {
        testRunListener.testRunStarted(null);
        runnable.run();
        testRunListener.testRunFinished(null);
    }

    @Test
    public void givenFirstTestRunFailsAndSecondTestRunPasses_whenGetTestRunResultCalled_thenOKIsReturned()throws Exception{
        //given 1st run
        Description testThatFails = getMockedTestDescription("testThatFails", "com.blah.MyTest");
        testRunOf(() -> failingTest(testThatFails));

        //given 2nd run
        testRunOf(() -> passingTest(getMockedTestDescription("testThatPasses", "com.blah.MyTest")));
        //when
        TestsRunResult secondResult = testRunListener.getTestsRunResult();
        //then
        assertThat(secondResult.getStatus(), CoreMatchers.is(TestsRunResult.Status.ALL_OK));
    }

    @Test
    public void givenOneTestStartedNoFailureEventAndTestRunFinished_whenGetTestRunResultCalled_thenStatusIsAllOKAndTestPresentInList() throws Exception {
        //given
        testRunOf(() -> passingTest(getMockedTestDescription("testThatPasses", "com.blah.MyTest")));
        //when then
        assertThat(testRunListener.getTestsRunResult().getStatus(), CoreMatchers.is(TestsRunResult.Status.ALL_OK));
        assertThat(testRunListener.getTestsRunResult().getTests().size(), CoreMatchers.is(1));
    }

    @Test
    public void givenOneTestStartedAFailureEventOccursAndTestRunFinished_whenGetTestRunResultCalled_thenStatusIsFailuresAndTestPresentInList() throws Exception {
        //given
        Description testThatFails = getMockedTestDescription("testThatFails", "com.blah.MyTest");
        //when
        testRunOf(() -> failingTest(testThatFails));
        //then
        assertThat(testRunListener.getTestsRunResult().getStatus(), CoreMatchers.is(TestsRunResult.Status.FAILURES));
        assertThat(testRunListener.getTestsRunResult().getTests().size(), CoreMatchers.is(1));
    }

    @Test
    public void givenTwoTestsStartedAFailureEventOccursAndTestRunFinished_whenGetTestRunResultCalled_thenStatusIsFailuresAndTestPresentInList() throws Exception {
        //given
        Description testThatPasses = getMockedTestDescription("testThatPasses", "com.blah.MyTest");
        Description testThatFails = getMockedTestDescription("testThatFails", "com.blah.MyTest");

        //when
        testRunOf(() -> {
            failingTest(testThatFails);
            passingTest(testThatPasses);
        });

        //then
        assertThat(testRunListener.getTestsRunResult().getStatus(), CoreMatchers.is(TestsRunResult.Status.FAILURES));
        assertThat(testRunListener.getTestsRunResult().getTests().size(), CoreMatchers.is(2));
    }

    private Failure mockedFailureOf(Description testThatFails) {
        Failure failure = mock(Failure.class);
        when(failure.getDescription()).thenReturn(testThatFails);
        return failure;
    }

    private Description getMockedTestDescription(String testThatPasses, String t) {
        Description description = mock(Description.class);
        when(description.getMethodName()).thenReturn(testThatPasses);
        when(description.getClassName()).thenReturn(t);
        return description;
    }


    private void passingTest(Description testThatPasses) {
        try {
            testRunListener.testStarted(testThatPasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void failingTest(Description testThatFails) {
        try {
            testRunListener.testStarted(testThatFails);
            testRunListener.testFailure(mockedFailureOf(testThatFails));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
