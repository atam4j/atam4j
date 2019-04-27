package me.atam.atam4jdomain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestsRunResultTest {

    @Test
    public void givenTestRunResultCreatedWithOnlyPassingTests_whenGetStatusCalled_thenALLOKStatusReturned(){
        //given
        List<IndividualTestResult> listOfPassingTests = getIndividualTestResults(new IndividualTestResult("com.blah", "method", "default", true, null), new IndividualTestResult("com.blah", "method2", "default", true, null));
        //when
        TestsRunResult testsRunResult = new TestsRunResult(listOfPassingTests);
        //then
        assertThat(testsRunResult.getStatus(), is(TestsRunResult.Status.ALL_OK));
        assertThat(testsRunResult.getTests().size(), is(2));
    }

    @Test
    public void givenTestRunResultCreatedWithPassingAndFailingTests_whenGetStatusCalled_thenFailuresStatusReturned(){
        //given
        List<IndividualTestResult> listOfPassingAndFailingTests = getIndividualTestResults(new IndividualTestResult("com.blah", "method", "default", false, null), new IndividualTestResult("com.blah", "method2", "default", true, null));
        //when
        TestsRunResult testsRunResult = new TestsRunResult(listOfPassingAndFailingTests);
        //then
        assertThat(testsRunResult.getStatus(), is(TestsRunResult.Status.FAILURES));
        assertThat(testsRunResult.getTests().size(), is(2));
    }

    @Test
    public void givenTestRunResultCreatedWithOnlyFailingTests_whenGetStatusCalled_thenFailuresStatusReturned(){
        //given
        List<IndividualTestResult> listOfPassingAndFailingTests = getIndividualTestResults(new IndividualTestResult("com.blah", "method", "default", false, null), new IndividualTestResult("com.blah", "method2", "default", false, null));
        //when
        TestsRunResult testsRunResult = new TestsRunResult(listOfPassingAndFailingTests);
        //then
        assertThat(testsRunResult.getStatus(), is(TestsRunResult.Status.FAILURES));
        assertThat(testsRunResult.getTests().size(), is(2));
    }

    @Test
    public void givenTestRunResultWithMultipleTests_whenGetTestByNameIsCalled_thenCorrectTestIsReturned() {
        //given
        List<IndividualTestResult> listOfPassingAndFailingTests = getIndividualTestResults(
                new IndividualTestResult("com.blah", "method", "default", false, null),
                new IndividualTestResult("com.blah", "method2", "default", false, null));
        //when
        TestsRunResult testsRunResult = new TestsRunResult(listOfPassingAndFailingTests);
        //then
        assertThat(testsRunResult.getTestByClassAndName("com.blah", "method"),
                is(Optional.of(new IndividualTestResult("com.blah", "method", "default", false, null))));
    }

    @Test
    public void givenTestRunResultWithMultipleTests_whenGetTestByNameIsCalled_thenMissingIsReturned() {
        //given
        List<IndividualTestResult> listOfPassingAndFailingTests = getIndividualTestResults(
                new IndividualTestResult("com.blah", "method", "default", false, null),
                new IndividualTestResult("com.blah", "method2", "default", false, null));
        //when
        TestsRunResult testsRunResult = new TestsRunResult(listOfPassingAndFailingTests);
        //then
        assertThat(testsRunResult.getTestByClassAndName("abcdef", "NotPresent"),
                is(Optional.empty()));
    }

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(TestsRunResult.class).verify();
    }



    private List<IndividualTestResult> getIndividualTestResults(IndividualTestResult... method) {
        List<IndividualTestResult> tests = new ArrayList<>();
        Collections.addAll(tests, method);
        return tests;
    }



}
