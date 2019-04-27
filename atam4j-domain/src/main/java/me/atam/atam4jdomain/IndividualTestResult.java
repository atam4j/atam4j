package me.atam.atam4jdomain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;
public class IndividualTestResult {
    private String testClass;
    private String testName;
    private String category;
    private boolean passed;
    private Throwable exception;

    public IndividualTestResult(){

    }

    public IndividualTestResult(String testClass, String testName, boolean passed) {
        this.testClass = testClass;
        this.testName = testName;
        this.passed = passed;
        this.category = "default";
    }

    public IndividualTestResult(String testClass, String testName, String category, boolean passed, Throwable exception) {
        this.testClass = testClass;
        this.testName = testName;
        this.category = category;
        this.passed = passed;
        this.exception = exception;

    }

    public Throwable getException() {
        return exception;
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

    public void setPassed(final boolean passed) {
        this.passed = passed;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndividualTestResult that = (IndividualTestResult) o;
        return passed == that.passed &&
                Objects.equals(testClass, that.testClass) &&
                Objects.equals(testName, that.testName) &&
                Objects.equals(category, that.category) &&
                Objects.equals(exception, that.exception);
    }

    @Override
    public int hashCode() {

        return Objects.hash(testClass, testName, category, passed, exception);
    }

    @Override
    public String toString() {
        return "IndividualTestResult{" +
                "testClass='" + testClass + '\'' +
                ", testName='" + testName + '\'' +
                ", category='" + category + '\'' +
                ", passed=" + passed +
                ", exception=" + exception +
                '}';
    }
}
