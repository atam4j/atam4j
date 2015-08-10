package me.atam.atam4j.health;

import org.junit.runner.Result;

import java.util.Optional;

public class AcceptanceTestsState {

    private Optional<Result> result = Optional.empty();

    public Optional<Result> getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = Optional.of(result);
    }

}