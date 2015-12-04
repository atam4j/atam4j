package me.atam.atam4jdomain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestsRunResultTest {

    @Test
    public void givenTestRunResultCreatedWithOnlyPassingTests_whenGetStatusCalled_thenALLOKStatusReturned(){
        //given
        List<IndividualTestResult> listOfPassingTests = getIndividualTestResults(new IndividualTestResult("com.blah", "method", true), new IndividualTestResult("com.blah", "method2", true));
        //when
        TestsRunResult testsRunResult = new TestsRunResult(listOfPassingTests);
        //then
        assertThat(testsRunResult.getStatus(), is(TestsRunResult.Status.ALL_OK));
        assertThat(testsRunResult.getTests().size(), is(2));
    }

    @Test
    public void givenTestRunResultCreatedWithPassingAndFailingTests_whenGetStatusCalled_thenFailuresStatusReturned(){
        //given
        List<IndividualTestResult> listOfPassingAndFailingTests = getIndividualTestResults(new IndividualTestResult("com.blah", "method", false), new IndividualTestResult("com.blah", "method2", true));
        //when
        TestsRunResult testsRunResult = new TestsRunResult(listOfPassingAndFailingTests);
        //then
        assertThat(testsRunResult.getStatus(), is(TestsRunResult.Status.FAILURES));
        assertThat(testsRunResult.getTests().size(), is(2));
    }

    @Test
    public void givenTestRunResultCreatedWithOnlyFailingTests_whenGetStatusCalled_thenFailuresStatusReturned(){
        //given
        List<IndividualTestResult> listOfPassingAndFailingTests = getIndividualTestResults(new IndividualTestResult("com.blah", "method", false), new IndividualTestResult("com.blah", "method2", false));
        //when
        TestsRunResult testsRunResult = new TestsRunResult(listOfPassingAndFailingTests);
        //then
        assertThat(testsRunResult.getStatus(), is(TestsRunResult.Status.FAILURES));
        assertThat(testsRunResult.getTests().size(), is(2));
    }





    private List<IndividualTestResult> getIndividualTestResults(IndividualTestResult... method) {
        List<IndividualTestResult> tests = new ArrayList<>();
        Collections.addAll(tests, method);
        return tests;
    }

    //TODO test for immutability


}
