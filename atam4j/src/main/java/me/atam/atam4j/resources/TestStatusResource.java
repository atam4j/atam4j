package me.atam.atam4j.resources;

import me.atam.atam4j.TestRunListener;
import me.atam.atam4jdomain.TestsRunResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/tests")
public class TestStatusResource {

    private TestRunListener testRunListener;

    public TestStatusResource(TestRunListener testRunListener) {
        this.testRunListener = testRunListener;
    }

    @GET
    @Produces("application/json")
    public TestsRunResult getTestStatus(){

        return testRunListener.getTestRunResult();

    }

}
