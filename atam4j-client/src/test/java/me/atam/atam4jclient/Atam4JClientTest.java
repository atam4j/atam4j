package me.atam.atam4jclient;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import me.atam.atam4jdomain.IndividualTestReport;
import me.atam.atam4jdomain.TestsRunResult;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Atam4JClientTest {


    @Rule
    public WireMockRule wireMockRule = new WireMockRule();
    private Atam4JClient atam4JClient;


    @Before
    public void setup(){
        atam4JClient = new Atam4JClient(JerseyClientBuilder.createClient(), "http://localhost:" + wireMockRule.port());
    }

    @Test
    public void givenPassingTests_whenGetTestRunResultCalled_thenOKResponseReturned() throws IOException {
        //given
        responseFromServerIs("{\"tests\":[{\"testClass\":\"me.atam.atam4j.dummytests.PassingTest\",\"testName\":\"testThatPasses\",\"passed\":true}],\"status\":\"ALL_OK\"}");
        //when
        TestsRunResult testRunResult = atam4JClient.getTestRunResult();
        //then
        assertThat(testRunResult.getStatus(), is(TestsRunResult.Status.ALL_OK));
        assertThat(testRunResult.getTests().size(), is(1));
        IndividualTestReport testReport = testRunResult.getTests().get(0);
        assertThat(testReport.getTestClass(), is("me.atam.atam4j.dummytests.PassingTest"));
        assertThat(testReport.getTestName(), is("testThatPasses"));
        assertTrue(testReport.isPassed());
    }

    @Test
    public void givenFailingTests_whenGetTestRunResultCalled_thenFailuresResponseReturned() throws IOException {
        //given
        responseFromServerIs("{\"tests\":[{\"testClass\":\"me.atam.atam4j.dummytests.FailingTest\",\"testName\":\"testThatFails\",\"passed\":false}],\"status\":\"FAILURES\"}");
        //when
        TestsRunResult testRunResult = atam4JClient.getTestRunResult();
        //then
        assertThat(testRunResult.getStatus(), is(TestsRunResult.Status.FAILURES));
        assertThat(testRunResult.getTests().size(), is(1));
        IndividualTestReport testReport = testRunResult.getTests().get(0);
        assertThat(testReport.getTestClass(), is("me.atam.atam4j.dummytests.FailingTest"));
        assertThat(testReport.getTestName(), is("testThatFails"));
        assertFalse(testReport.isPassed());
    }

    @Test
    public void givenTooEarlyResponse_whenGetTestRunResultCalled_thenTooEarlyResponseReceivedWithNoTests() throws IOException {
        //given
        responseFromServerIs("{\"status\":\"TOO_EARLY\"}");
        //when
        TestsRunResult testRunResult = atam4JClient.getTestRunResult();
        //then
        assertThat(testRunResult.getStatus(), is(TestsRunResult.Status.TOO_EARLY));
        assertNull(testRunResult.getTests());
    }

    private void responseFromServerIs(String body) {
        wireMockRule.stubFor((get(urlEqualTo("/tests"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(body))));
    }
}