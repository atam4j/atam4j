package me.atam.atam4jdomain;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndividualTestResult that = (IndividualTestResult) o;
        return Objects.equals(passed, that.passed) &&
                Objects.equals(testClass, that.testClass) &&
                Objects.equals(testName, that.testName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testClass, testName, passed);
    }

    @Override
    public String toString() {
        return "IndividualTestResult{" +
                "testClass='" + testClass + '\'' +
                ", testName='" + testName + '\'' +
                ", passed=" + passed +
                '}';
    }
}
