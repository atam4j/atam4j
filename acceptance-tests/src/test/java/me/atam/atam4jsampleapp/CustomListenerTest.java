package me.atam.atam4jsampleapp;

import me.atam.atam4j.dummytests.PassingTestWithNoCategory;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static me.atam.atam4jsampleapp.testsupport.AcceptanceTestTimeouts.TEN_SECONDS_IN_MILLIS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomListenerTest extends AcceptanceTest {
    @Test
    public void givenAConfiguredCustomLister_whenTestsAreRun_thenListenerCallbacksWillBeCalled() {
        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter
                .startApplicationWith(TEN_SECONDS_IN_MILLIS, PassingTestWithNoCategory.class, 1);
        //when
        Response customListenerStatusResponse = getCustomListenerStatusOnceTestsRunHasCompleted();

        //then
        assertThat(customListenerStatusResponse.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(customListenerStatusResponse.readEntity(CustomListenerStatus.class).isStarted(), is(true));
    }
}
