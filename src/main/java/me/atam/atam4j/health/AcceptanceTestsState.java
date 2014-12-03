package me.atam.atam4j.health;

import org.junit.runner.Result;

import java.util.Optional;

public class AcceptanceTestsState {

    private boolean running;

    private Optional<Result> result = Optional.empty();

    public Optional<Result> getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = Optional.of(result);
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }
}