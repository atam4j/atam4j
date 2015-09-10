package me.atam.atam4jsampleapp;

import io.dropwizard.testing.DropwizardTestSupport;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.After;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AcceptanceTest {

    protected DropwizardTestSupport<ApplicationConfiguration> applicationConfigurationDropwizardTestSupport;


    @After
    public void stopApplication(){
        applicationConfigurationDropwizardTestSupport.after();
    }

    protected void checkResponseIsErrorAndWithMessage(String expectedMessage, Response response) {
        HealthCheckResult healthCheckResult = response.readEntity(HealthCheckResult.class);
        assertThat(response.getStatus(), CoreMatchers.equalTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        assertThat(healthCheckResult.getAcceptanceTestsHealthCheckResult().isHealthy(), is(false));
        assertThat(healthCheckResult.getAcceptanceTestsHealthCheckResult().getMessage(), is(expectedMessage));
    }

    protected void checkResponseIsOKAndWithMessage(String expectedMessage, Response response) {
        HealthCheckResult healthCheckResult = response.readEntity(HealthCheckResult.class);
        assertThat(healthCheckResult.getAcceptanceTestsHealthCheckResult().getMessage(), is(expectedMessage));
        assertThat(response.getStatus(), CoreMatchers.equalTo(Response.Status.OK.getStatusCode()));
        assertThat(healthCheckResult.getAcceptanceTestsHealthCheckResult().isHealthy(), is(true));
    }

    protected Response getResponseFromHealthCheck() {
        return new JerseyClientBuilder().build().target(
                String.format("http://localhost:%d/healthcheck", applicationConfigurationDropwizardTestSupport.getAdminPort()))
                .request()
                .get();
    }

}
