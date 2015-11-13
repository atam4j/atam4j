package me.atam.atam4j.resources;

import me.atam.atam4j.TestRunListener;
import me.atam.atam4jdomain.TestsRunResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/tests")
public class TestStatusResource {

    private TestRunListener testRunListener;

    public TestStatusResource(TestRunListener testRunListener) {
        this.testRunListener = testRunListener;
    }

    @GET
    @Produces("application/json")
    public Response getTestStatus(){
        TestsRunResult testRunResult = testRunListener.getTestRunResult();
        if (testRunResult.getStatus().equals(TestsRunResult.Status.FAILURES)){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(testRunResult).build();
        }
        return Response.status(Response.Status.OK).entity(testRunResult).build();
    }
}
