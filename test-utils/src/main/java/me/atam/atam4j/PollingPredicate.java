package me.atam.atam4j;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class PollingPredicate<T> {

    private int maxAttempts;
    private int retryPollInterval;
    private Predicate<T> predicate;
    private Supplier<T> supplier;

    public PollingPredicate(int maxAttempts, int retryPollInterval, Predicate<T> predicate, Supplier<T> supplier) {
        this.maxAttempts = maxAttempts;
        this.retryPollInterval = retryPollInterval;
        this.predicate = predicate;
        this.supplier = supplier;
    }

    public boolean pollUntilPassedOrMaxAttemptsExceeded() {
        return eventOccurs();
    }

    public void pollUntilPassedOrFail(String errorMessage) {
        if (!eventOccurs()) {
            throw new RuntimeException(errorMessage);
        }
    }

    private boolean eventOccurs() {
        for (int i=0; i< maxAttempts; i++){
            if (predicate.test(supplier.get())){
                return true;
            }
            try {
                Thread.sleep(retryPollInterval);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
