package me.atam.atam4jsampleapp.testsupport;

import io.dropwizard.testing.DropwizardTestSupport;
import me.atam.atam4jsampleapp.ApplicationConfiguration;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;

import javax.ws.rs.core.Response;

public abstract class AcceptanceTest {

    protected DropwizardTestSupport<ApplicationConfiguration> dropwizardTestSupportAppConfig;


    @After
    public void stopApplication() {
        dropwizardTestSupportAppConfig.after();
    }

    public Response getTestRunResultFromServer(){
        return new JerseyClientBuilder().build().target(
                String.format("http://localhost:%d/tests", dropwizardTestSupportAppConfig.getLocalPort()))
                .request()
                .get();
    }

}
