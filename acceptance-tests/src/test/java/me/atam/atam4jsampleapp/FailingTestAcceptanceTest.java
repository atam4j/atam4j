package me.atam.atam4jsampleapp;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import me.atam.atam4j.dummytests.FailingTest;
import me.atam.atam4j.dummytests.PassingAndFailingTests;
import me.atam.atam4j.logging.LoggingListener;
import me.atam.atam4jdomain.IndividualTestResult;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FailingTestAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenSampleApplicationStartedWithFailingTest_whenHealthCheckCalledAfterTestRun_thenFailuresMessageReceived(){

        printLoggingInfo("BEFORE START APP");




        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, FailingTest.class, 1);
        printLoggingInfo("AFTER START APP");
        //when
        Response response = getResponseFromTestsEndpointOnceTestsRunHasCompleted();
        //then
        assertThat(response.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(testRunResult.getTests().size(), is(1));
        assertThat(
                testRunResult.getTests(),
                hasItem(new IndividualTestResult(FailingTest.class.getName(), "testThatFails", "default", false))
        );
    }

    public static void printLoggingInfo(String suffix) {
        Logger logger = LoggerFactory.getLogger(LoggingListener.class);

        ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) logger;
        System.out.println(suffix + "  effecitveLevel: " + logbackLogger.getEffectiveLevel());
        System.out.println(suffix + "  appender: " + logbackLogger.getAppender("STDOUT"));
        System.out.println(suffix + "  isInfoEnabled: " + logbackLogger.isInfoEnabled());
        System.out.println(suffix + "  loggerCOntext: " + logbackLogger.getLoggerContext());
    }

    @Test
    public void givenSampleApplicationStartedWithPassingAndFailingTest_whenHealthCheckCalledAfterTestRun_thenFailuresMessageReceived(){
        //given
        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, PassingAndFailingTests.class, 1);
        //when
        Response response = getResponseFromTestsEndpointOnceTestsRunHasCompleted();
        //then
        assertThat(response.getStatus(), is(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()));
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(testRunResult.getTests().size(), is(2));
        assertThat(
                testRunResult.getTests(),
                hasItem(new IndividualTestResult(PassingAndFailingTests.class.getName(), "testThatFails", "default", false))
        );
        assertThat(
                testRunResult.getTests(),
                hasItem(new IndividualTestResult(PassingAndFailingTests.class.getName(), "testThatPasses", "default", true))
        );

    }
}
