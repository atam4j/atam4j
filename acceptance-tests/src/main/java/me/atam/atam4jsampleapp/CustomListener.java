package me.atam.atam4jsampleapp;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;

public class CustomListener extends RunListener {

    private CustomListenerStatus customListenerStatus;

    public CustomListener(CustomListenerStatus customListenerStatus) {
        this.customListenerStatus = customListenerStatus;
    }

    @Override
    public void testRunStarted(Description description) throws Exception {
        super.testRunStarted(description);
        customListenerStatus.setStarted(true);
    }
}
