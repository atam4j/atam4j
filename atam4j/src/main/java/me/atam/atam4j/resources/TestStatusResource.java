package me.atam.atam4j.resources;

import junit.framework.TestResult;
import me.atam.atam4jdomain.TestsRunResult;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import  javax.ws.rs.core.Response;

@Path("/tests")
public class TestStatusResource {


    @GET
    @Produces("application/json")
    public TestsRunResult getTestStatus(){
        TestsRunResult testsRunResult = new TestsRunResult(null, TestsRunResult.Status.TOO_EARLY);
        return testsRunResult;
    }

}
