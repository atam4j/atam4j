package me.atam.atam4jsampleapp.testsupport;

import io.dropwizard.testing.DropwizardTestSupport;
import me.atam.atam4jclient.Atam4JClient;
import me.atam.atam4jsampleapp.ApplicationConfiguration;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;

import javax.ws.rs.core.Response;

public abstract class AcceptanceTest {

    protected DropwizardTestSupport<ApplicationConfiguration> applicationConfigurationDropwizardTestSupport;


    @After
    public void stopApplication() {
        applicationConfigurationDropwizardTestSupport.after();
    }

    public Response getTestRunResultFromServer(){
        return new Atam4JClient(new JerseyClientBuilder().build(), String.format("http://localhost:%d", applicationConfigurationDropwizardTestSupport.getLocalPort())).getTestRunResult();
    }

}
