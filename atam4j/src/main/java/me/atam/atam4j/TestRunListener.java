package me.atam.atam4j;

import me.atam.atam4jdomain.IndividualTestResult;
import me.atam.atam4jdomain.TestsRunResult;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestRunListener extends RunListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunListener.class);

    private volatile Map<TestIdentifier, IndividualTestResult> inProgressTestResults;
    private volatile Map<TestIdentifier, IndividualTestResult> completedTestResults;
    private volatile boolean testsFinished = false;

    @Override
    public void testRunStarted(Description description) throws Exception {
        inProgressTestResults = new HashMap<>();
    }

    @Override
    public void testStarted(Description description) throws Exception {
        inProgressTestResults.put(
                new TestIdentifier(description),
                new IndividualTestResult(
                        description.getClassName(),
                        description.getMethodName(),
                        getCategoryName(description),
                        true)
        );
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        IndividualTestResult individualTestResult = inProgressTestResults.get(new TestIdentifier(failure.getDescription()));
        individualTestResult.setPassed(false);
    }

    public TestsRunResult getTestsRunResult() {
        if (testsFinished) {
            return new TestsRunResult(completedTestResults.values());
        }
        return new TestsRunResult(Collections.emptyList(), TestsRunResult.Status.TOO_EARLY);
    }

    public TestsRunResult getTestsRunResult(String category) {
        if (testsFinished) {
            // filter out tests that don't match category
            return new TestsRunResult(completedTestResults
                    .values()
                    .stream()
                    .filter(testResult -> testResult.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList())
            );
        }
        return new TestsRunResult(Collections.emptyList(), TestsRunResult.Status.TOO_EARLY);
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        testsFinished = true;
        completedTestResults = inProgressTestResults;
    }

    private String getCategoryName(Description description) {
        String category = "default";

        MonitorCategory monitorCategoryAnnotation = description.getAnnotation(MonitorCategory.class);
        if (monitorCategoryAnnotation != null) {
            category = monitorCategoryAnnotation.name();
        }
        return category;
    }

    private static class TestIdentifier {
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
