package io.github.qe7.core.bus;

public interface Handler {
    default boolean listening() {
        return true;
    }
}
