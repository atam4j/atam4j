package me.atam.atam4j.resources;

import me.atam.atam4j.TestRunListener;
import me.atam.atam4jdomain.TestsRunResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/tests")
public class TestStatusResource {

    private TestRunListener testRunListener;

    public TestStatusResource(TestRunListener testRunListener) {
        this.testRunListener = testRunListener;
    }

    @GET
    @Produces("application/json")
    public Response getTestStatus(){
        TestsRunResult testRunResult = testRunListener.getTestsRunResult();
        if (testRunResult.getStatus().equals(TestsRunResult.Status.FAILURES)){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(testRunResult).build();
        }
        return Response.status(Response.Status.OK).entity(testRunResult).build();
    }

    @GET
    @Path("{category}")
    @Produces("application/json")
    public Response getTestStatusForACategory(@PathParam("category") String category) {

        // filter out tests that don't match category
        TestsRunResult categorisedTestsResult = new TestsRunResult(
                testRunListener
                        .getTestsRunResult()
                        .getTests()
                        .parallelStream()
                        .filter(testResult -> testResult.getCategory().equalsIgnoreCase(category))
                        .collect(Collectors.toList()));

        if (categorisedTestsResult.getTests().size() <= 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else if (categorisedTestsResult.getStatus().equals(TestsRunResult.Status.FAILURES)){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(categorisedTestsResult).build();
        } else {
            return Response.status(Response.Status.OK).entity(categorisedTestsResult).build();
        }
    }
}
