package me.atam.atam4jsampleapp.testsupport;

import io.dropwizard.testing.DropwizardTestSupport;
import me.atam.atam4j.PollingPredicate;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.ApplicationConfiguration;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.After;

import javax.ws.rs.core.Response;

import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.MAX_ATTEMPTS;
import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.RETRY_POLL_INTERVAL;
import static org.junit.Assert.assertTrue;

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

    public Response getTestRunResultFromServerWithCategory(String category){
        return new JerseyClientBuilder().build().target(
                String.format("http://localhost:%d/tests/%s", dropwizardTestSupportAppConfig.getLocalPort(), category))
                .request()
                .get();
    }

    public Response getResponseFromTestsEndpointOnceAllOKResponseReceived() {
        return getResponseFromTestsEndpointOnceResponseIsOfType(TestsRunResult.Status.ALL_OK);
    }

    public Response getResponseFromTestsEndpointFailedResponseReceived() {
        return getResponseFromTestsEndpointOnceResponseIsOfType(TestsRunResult.Status.FAILURES);
    }

    private Response getResponseFromTestsEndpointOnceResponseIsOfType(TestsRunResult.Status status) {
        PollingPredicate<Response> responsePollingPredicate = new PollingPredicate<>(
                MAX_ATTEMPTS,
                RETRY_POLL_INTERVAL,
                response -> response.readEntity(TestsRunResult.class).getStatus().equals(status),
                this::getTestRunResultFromServer);

        assertTrue(responsePollingPredicate.pollUntilPassedOrMaxAttemptsExceeded());
        return getTestRunResultFromServer();
    }

}
