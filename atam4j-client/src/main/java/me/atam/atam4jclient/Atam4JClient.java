package me.atam.atam4jclient;


import me.atam.atam4jdomain.TestsRunResult;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

public class Atam4JClient {

    private Client client;
    private String baseURI;

    public Atam4JClient(Client client, String baseURI) {
        this.client = client;
        this.baseURI = baseURI;
    }

    public TestsRunResult getTestRunResult(){
        return client.target(baseURI + "/tests").request().get(TestsRunResult.class);
    }


}
