package me.atam.atam4j.resources;

import me.atam.atam4j.TestRunListener;
import me.atam.atam4jdomain.TestsRunResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/tests")
@Produces("application/json")
public class TestStatusResource {

    private final TestRunListener testRunListener;

    public TestStatusResource(TestRunListener testRunListener) {
        this.testRunListener = testRunListener;
    }

    @GET
    public Response getTestStatus(){
        return buildResponse(testRunListener.getTestsRunResult());
    }

    @GET
    @Path("{category}")
    public Response getTestStatusForACategory(@PathParam("category") String category) {
        return buildResponse(testRunListener.getTestsRunResult(category));
    }

    private Response buildResponse(TestsRunResult testRunResult) {
        if (testRunResult.getStatus().equals(TestsRunResult.Status.FAILURES)) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(testRunResult)
                           .build();
        } else if (testRunResult.getStatus().equals(TestsRunResult.Status.CATEGORY_NOT_FOUND)) {
            return Response.status(Response.Status.NOT_FOUND)
                           .build();
        } else {
            return Response.status(Response.Status.OK)
                           .entity(testRunResult)
                           .build();
        }
    }
}