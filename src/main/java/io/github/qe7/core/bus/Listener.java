package io.github.qe7.core.bus;

public interface Listener<T extends Event> {
    void call(T event);
}
