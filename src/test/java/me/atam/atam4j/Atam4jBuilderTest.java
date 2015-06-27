package me.atam.atam4j;

import com.codahale.metrics.health.HealthCheckRegistry;
import me.atam.atam4j.ignore.PassingTest;
import org.junit.Assert;
import org.junit.Test;

public class Atam4jBuilderTest {

    @Test
    public void givenBuilderConstructedWithHealthCheckRegistry_whenBuildCalled_thenManagerReturned() {
        Atam4j.Atam4jBuilder builder = new Atam4j.Atam4jBuilder(new HealthCheckRegistry()).withTestClasses(PassingTest.class);
        Assert.assertNotNull(builder.build());
    }

    @Test(expected = NullPointerException.class)
    public void givenBuilderConstructedWithNullArg_whenBuildCalled_thenNullPointerIsThrown() {
        Atam4j.Atam4jBuilder builder = new Atam4j.Atam4jBuilder(null);
        builder.build();
    }
}
