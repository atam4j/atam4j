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
        assertThat(testRunListener.getTestRunResult().getStatus(), CoreMatchers.is(TestsRunResult.Status.TOO_EARLY));
    }

    @Test
    public void givenOneTestStartedNoFailureEventAndTestRunFinished_whenGetTestRunResultCalled_thenStatusIsAllOKAndTestPresentInList() throws Exception {
        //given
        testRunListener.testStarted(getMockedTestDescription("testThatPasses", "com.blah.MyTest"));
        testRunListener.testRunFinished(null);
        //when then
        assertThat(testRunListener.getTestRunResult().getStatus(), CoreMatchers.is(TestsRunResult.Status.ALL_OK));
        assertThat(testRunListener.getTestRunResult().getTests().size(), CoreMatchers.is(1));
    }

    @Test
    public void givenOneTestStartedAFailureEventOccursAndTestRunFinished_whenGetTestRunResultCalled_thenStatusIsFailuresAndTestPresentInList() throws Exception {
        //given
        Description testThatFails = getMockedTestDescription("testThatFails", "com.blah.MyTest");
        //when
        testRunListener.testStarted(testThatFails);
        testRunListener.testFailure(mockedFailureOf(testThatFails));
        testRunListener.testRunFinished(null);
        //then
        assertThat(testRunListener.getTestRunResult().getStatus(), CoreMatchers.is(TestsRunResult.Status.FAILURES));
        assertThat(testRunListener.getTestRunResult().getTests().size(), CoreMatchers.is(1));
    }

    @Test
    public void givenTwoTestsStartedAFailureEventOccursAndTestRunFinished_whenGetTestRunResultCalled_thenStatusIsFailuresAndTestPresentInList() throws Exception {
        //given
        Description testThatPasses = getMockedTestDescription("testThatPasses", "com.blah.MyTest");
        Description testThatFails = getMockedTestDescription("testThatFails", "com.blah.MyTest");

        //when
        testRunListener.testStarted(testThatFails);
        testRunListener.testStarted(testThatPasses);
        testRunListener.testFailure(mockedFailureOf(testThatFails));
        testRunListener.testRunFinished(null);

        //then
        assertThat(testRunListener.getTestRunResult().getStatus(), CoreMatchers.is(TestsRunResult.Status.FAILURES));
        assertThat(testRunListener.getTestRunResult().getTests().size(), CoreMatchers.is(2));
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
}
