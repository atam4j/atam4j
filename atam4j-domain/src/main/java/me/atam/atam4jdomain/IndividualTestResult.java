package me.atam.atam4jdomain;


public class IndividualTestResult {
    private String testClass;
    private String testName;
    private boolean passed;

    public IndividualTestResult() {
    }

    public IndividualTestResult(String testClass, String testName, boolean passed) {
        this.testClass = testClass;
        this.testName = testName;
        this.passed = passed;
    }

    public String getTestClass() {
        return testClass;
    }

    public String getTestName() {
        return testName;
    }

    public boolean isPassed() {
        return passed;
    }
}
