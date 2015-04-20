package me.atam.atam4j;


import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;
import uk.org.lidalia.slf4jtest.LoggingEvent;

public class LoggingEventWithThrowableMatcher extends TypeSafeMatcher<LoggingEvent> {

    private String messageContains;

    public LoggingEventWithThrowableMatcher(String messageContains) {
        this.messageContains = messageContains;
    }

    @Override
    public boolean matchesSafely(LoggingEvent loggingEvent) {
        return loggingEvent.getThrowable().toString().contains(messageContains);
    }

    public void describeTo(Description description) {
        description.appendText("a logging event with a a throwable that contains the following string: " + messageContains );
    }

    @Factory
    public static LoggingEventWithThrowableMatcher hasThrowableThatContainsString(String message) {
        return new LoggingEventWithThrowableMatcher(message);
    }




}