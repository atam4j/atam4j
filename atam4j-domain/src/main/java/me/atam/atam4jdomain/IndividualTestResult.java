package me.atam.atam4jdomain;


public class IndividualTestResult {
    private String testClass;
    private String testName;
    private String category;
    private boolean passed;

    public IndividualTestResult() {
    }

    public IndividualTestResult(String testClass, String testName, String category, boolean passed) {
        this.testClass = testClass;
        this.testName = testName;
        this.category = category;
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

    public void setPassed(final boolean passed) {
        this.passed = passed;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndividualTestResult that = (IndividualTestResult) o;

        if (passed != that.passed) return false;
        if (testClass != null ? !testClass.equals(that.testClass) : that.testClass != null) return false;
        if (testName != null ? !testName.equals(that.testName) : that.testName != null) return false;
        return category != null ? category.equals(that.category) : that.category == null;

    }

    @Override
    public int hashCode() {
        int result = testClass != null ? testClass.hashCode() : 0;
        result = 31 * result + (testName != null ? testName.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (passed ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IndividualTestResult{" +
                "testClass='" + testClass + '\'' +
                ", testName='" + testName + '\'' +
                ", category='" + category + '\'' +
                ", passed=" + passed +
                '}';
    }
}
