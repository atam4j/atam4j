package me.atam.atam4jsampleapp.resources;

import me.atam.atam4jsampleapp.CustomListenerStatus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/customlistenerstatus")
@Produces("application/json")
public class CustomListenerStatusResource {
    private final CustomListenerStatus customListenerStatus;

    public CustomListenerStatusResource(CustomListenerStatus customListenerStatus) { this.customListenerStatus = customListenerStatus; }

    @GET
    public CustomListenerStatus getAcceptanceTestRunListenerStatus() { return customListenerStatus; }
}
