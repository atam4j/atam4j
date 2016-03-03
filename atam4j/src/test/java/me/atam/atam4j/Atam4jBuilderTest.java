package me.atam.atam4j;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import me.atam.atam4j.dummytests.PassingTestWithNoCategory;
import org.junit.Assert;
import org.junit.Test;

public class Atam4jBuilderTest {

    @Test
    public void givenBuilderConstructedWithHealthCheckRegistry_whenBuildCalled_thenManagerReturned() {
        Atam4j.Atam4jBuilder builder = new Atam4j.Atam4jBuilder(
                new JerseyEnvironment(null,null)).withTestClasses(PassingTestWithNoCategory.class);
        Assert.assertNotNull(builder.build());
    }

}
