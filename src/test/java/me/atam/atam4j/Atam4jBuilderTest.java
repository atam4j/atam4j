package me.atam.atam4j;

import io.dropwizard.setup.Environment;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class Atam4jBuilderTest {

    @Test
    public void givenBuilderConstructedWithValidEnvironment_whenBuildCalled_thenManagerReturned() {
        Atam4j.Atam4jBuilder builder = new Atam4j.Atam4jBuilder(mock(Environment.class));
        Assert.assertNotNull(builder.build());
    }

    @Test(expected = NullPointerException.class)
    public void givenBuilderConstructedWithNullArg_whenBuildCalled_thenNullPointerIsThrown() {
        Atam4j.Atam4jBuilder builder = new Atam4j.Atam4jBuilder(null);
        builder.build();
    }

}
