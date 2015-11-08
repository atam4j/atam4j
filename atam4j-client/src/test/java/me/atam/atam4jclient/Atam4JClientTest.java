package me.atam.atam4jclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import me.atam.atam4jdomain.TestsRunResult;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertNotNull;

public class Atam4JClientTest {


    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void givenPassingTests_whenGetTestRunResultCalled_thenOKResponseReturned() throws IOException {
        System.out.println(JsonProperty.class);
        String body = "{\"tests\":[{\"testClass\":\"me.atam.atam4j.dummytests.PassingTest\",\"testName\":\"testThatPasses\",\"passed\":true}],\"status\":\"ALL_OK\"}";
        wireMockRule.stubFor((get(urlEqualTo("/tests"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(body))));
        Response testRunResult = new Atam4JClient(JerseyClientBuilder.createClient(), "http://localhost:" + wireMockRule.port()).getTestRunResult();
        TestsRunResult returnedBodyAsString = testRunResult.readEntity(TestsRunResult.class);


        ObjectMapper mapper = new ObjectMapper();
        //TestsRunResult s = mapper.readValue(returnedBodyAsString, TestsRunResult.class);
//        try {
//            ArrayList<IndividualTestReport> testReports = new ArrayList<>();
//            testReports.add(new IndividualTestReport("me.atam.atam4j.dummytests.PassingTest", "testThatPasses", true));
//            TestsRunResult value = new TestsRunResult(testReports, TestsRunResult.Status.ALL_OK);
//
//            System.out.println(mapper.writeValueAsString(value));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }


        assertNotNull(returnedBodyAsString);

    }

}
