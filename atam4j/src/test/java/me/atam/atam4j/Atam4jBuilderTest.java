package me.atam.atam4j;

import com.codahale.metrics.health.HealthCheckRegistry;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import me.atam.atam4j.dummytests.PassingTest;
import org.junit.Assert;
import org.junit.Test;

public class Atam4jBuilderTest {

    @Test
    public void givenBuilderConstructedWithHealthCheckRegistry_whenBuildCalled_thenManagerReturned() {
        Atam4j.Atam4jBuilder builder = new Atam4j.Atam4jBuilder(new JerseyEnvironment(null,null)).withTestClasses(PassingTest.class);
        Assert.assertNotNull(builder.build());
    }

}
