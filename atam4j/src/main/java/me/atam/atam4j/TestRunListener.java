package me.atam.atam4j;

import me.atam.atam4jdomain.IndividualTestResult;
import me.atam.atam4jdomain.TestsRunResult;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.*;

public class TestRunListener extends RunListener{

    private Map<TestIdentifier, IndividualTestResult> individualTestReportMap = new HashMap<>();
    private boolean testsFinished = false;


    @Override
    public void testStarted(Description description) throws Exception {
        individualTestReportMap.put(new TestIdentifier(description), new IndividualTestResult(description.getClassName(), description.getMethodName(), true));
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        //TODO what does this mean?
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        IndividualTestResult individualTestResult = new IndividualTestResult(failure.getDescription().getClassName(), failure.getDescription().getMethodName(), false);
        TestIdentifier k = new TestIdentifier(failure.getDescription());
        IndividualTestResult put = individualTestReportMap.put(k, individualTestResult);
    }

    public TestsRunResult getTestRunResult(){
        if (!testsFinished){
            return new TestsRunResult(TestsRunResult.Status.TOO_EARLY);
        }
        return new TestsRunResult(individualTestReportMap.values());
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        this.testsFinished = true;            ;
    }

    private static class TestIdentifier{
        private Class testClass;
        private String testName;

        public TestIdentifier(Description description) {
            this.testClass = description.getTestClass();
            this.testName = description.getMethodName();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestIdentifier that = (TestIdentifier) o;
            return Objects.equals(testClass, that.testClass) &&
                    Objects.equals(testName, that.testName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(testClass, testName);
        }
    }
}
