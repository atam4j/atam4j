package me.atam.atam4jsampleapp.testsupport;

import io.dropwizard.testing.DropwizardTestSupport;
import me.atam.atam4jsampleapp.ApplicationConfiguration;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

public abstract class AcceptanceTest {

    private static final Client client = new JerseyClientBuilder().build();
    protected DropwizardTestSupport<ApplicationConfiguration> dropwizardTestSupportAppConfig;

    @After
    public void stopApplication() {
        dropwizardTestSupportAppConfig.after();
    }

    public Response getTestRunResultFromServer() {
        return client.target(String.format("http://localhost:%d/tests", dropwizardTestSupportAppConfig.getLocalPort()))
                     .request()
                     .get();
    }

    public Response getCriticalTestRunResultFromServer() {
        return client.target(String.format("http://localhost:%d/tests/priority-1", dropwizardTestSupportAppConfig.getLocalPort()))
                .request()
                .get();
    }
}