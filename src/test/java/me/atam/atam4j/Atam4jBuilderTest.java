package me.atam.atam4j;

import io.dropwizard.setup.Environment;
import me.atam.atam4j.ignore.PassingTest;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class Atam4jBuilderTest {

    Atam4j.Atam4jBuilder builder = new Atam4j.Atam4jBuilder();

    @Test
    public void givenBuilderNotSuppliedWithEnvironment_whenBuildCalled_thenExceptionThrown(){
        buildShouldThrowExceptionWithMessage("No Environment specified");
    }

    @Test
    public void givenBuilderNotSuppliedWithTestClasses_whenBuildCalled_thenExceptionThrown(){
        builder.withEnvironment(mock(Environment.class));
        buildShouldThrowExceptionWithMessage("No test classes specified");
    }

    @Test
    public void givenBuilderSuppliedWithAllMandatoryParams_whenBuildCalled_thenManagerReturned(){
        builder.withEnvironment(mock(Environment.class));
        builder.withTestClasses(new Class[]{PassingTest.class});
        Assert.assertNotNull(builder.build());
    }

    private void buildShouldThrowExceptionWithMessage(String operand) {
        try {
            builder.build();
            Assert.fail("Should have failed with exception");
        }
        catch (IllegalStateException e){
            Assert.assertThat(e.getMessage(), CoreMatchers.equalTo(operand));
        }
    }

}
