package me.atam.atam4j;

import io.dropwizard.setup.Environment;
import me.atam.atam4j.health.AcceptanceTestsHealthCheck;
import me.atam.atam4j.health.AcceptanceTestsState;

public class AcceptanceTestsHealthCheckInitializer {

    private AcceptanceTestsState acceptanceTestsState = new AcceptanceTestsState();
    private Environment environment;

    public AcceptanceTestsHealthCheckInitializer(AcceptanceTestsState acceptanceTestsState, Environment environment) {
        this.acceptanceTestsState = acceptanceTestsState;
        this.environment = environment;
    }

    public void initialize(){
        AcceptanceTestsHealthCheck healthCheck = new AcceptanceTestsHealthCheck(acceptanceTestsState);
        environment.healthChecks().register(AcceptanceTestsHealthCheck.NAME, healthCheck);
    }

}
