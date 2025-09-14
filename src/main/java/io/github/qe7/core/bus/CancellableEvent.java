package io.github.qe7.core.bus;

public interface CancellableEvent extends Event {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}