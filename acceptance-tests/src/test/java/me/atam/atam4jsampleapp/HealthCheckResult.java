package me.atam.atam4jsampleapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HealthCheckResult {
    @JsonProperty(value = "Acceptance Tests HealthCheck")
    private AcceptanceTestsHealthCheckResult acceptanceTestsHealthCheckResult;

    public AcceptanceTestsHealthCheckResult getAcceptanceTestsHealthCheckResult() {
        return acceptanceTestsHealthCheckResult;
    }

    public class AcceptanceTestsHealthCheckResult{

        private boolean healthy;

        private String message;

        public boolean isHealthy() {
            return healthy;
        }

        public void setHealthy(boolean healthy) {
            this.healthy = healthy;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
