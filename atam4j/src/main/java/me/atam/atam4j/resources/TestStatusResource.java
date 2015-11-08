package me.atam.atam4j.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import  javax.ws.rs.core.Response;

@Path("/tests")
public class TestStatusResource {


    @GET
    public Response getTestStatus(){
        return Response.status(200).build();
    }

}
