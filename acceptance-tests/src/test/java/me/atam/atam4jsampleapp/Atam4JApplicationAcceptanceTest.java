package me.atam.atam4jsampleapp;

import io.dropwizard.Configuration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response;

public class Atam4JApplicationAcceptanceTest {

    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE =
            new DropwizardAppRule<>(Atam4JApplication.class, ResourceHelpers.resourceFilePath("app-config.yml"));

    @Test
    public void givenSampleApplicationStarted_whenHealthCheckCalled_thenTooEarlyMessageReceived(){
        Response response = new JerseyClientBuilder().build().target(
                String.format("http://localhost:%d/healthcheck", RULE.getAdminPort()))
                .request()
                .get();

        assertThat(response.getStatus(), CoreMatchers.equalTo(Response.Status.OK.getStatusCode()));
        HealthCheckResult healthCheckResult = response.readEntity(HealthCheckResult.class);
        assertThat(healthCheckResult.getAcceptanceTestsHealthCheckResult().isHealthy(), is(true));
        assertThat(healthCheckResult.getAcceptanceTestsHealthCheckResult().getMessage(), is(AcceptanceTestsHealthCheck.OK_MESSAGE));
    }


}
