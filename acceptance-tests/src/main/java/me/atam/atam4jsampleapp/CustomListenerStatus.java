package me.atam.atam4jsampleapp;

public class CustomListenerStatus {
    private boolean started;

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public CustomListenerStatus() {
        this.started = false;
    }
}
