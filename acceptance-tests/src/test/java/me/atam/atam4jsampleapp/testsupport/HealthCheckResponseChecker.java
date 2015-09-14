package me.atam.atam4jsampleapp.testsupport;

import org.hamcrest.CoreMatchers;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HealthCheckResponseChecker {

    private Response response;
    private HealthCheckResult healthCheckResult;

    public HealthCheckResponseChecker(Response response) {
        this.response = response;
        this.healthCheckResult = this.response.readEntity(HealthCheckResult.class);
    }

    public void checkResponseIsErrorAndWithMessage(String expectedMessage) {
        assertThat(response.getStatus(), CoreMatchers.equalTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        assertThat(healthCheckResult.getAcceptanceTestsHealthCheckResult().isHealthy(), is(false));
        assertThat(healthCheckResult.getAcceptanceTestsHealthCheckResult().getMessage(), is(expectedMessage));
    }

    public void checkResponseIsOKAndWithMessage(String expectedMessage) {
        assertThat(healthCheckResult.getAcceptanceTestsHealthCheckResult().getMessage(), is(expectedMessage));
        assertThat(response.getStatus(), CoreMatchers.equalTo(Response.Status.OK.getStatusCode()));
        assertThat(healthCheckResult.getAcceptanceTestsHealthCheckResult().isHealthy(), is(true));
    }

}
