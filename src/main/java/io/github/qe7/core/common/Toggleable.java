package io.github.qe7.core.common;

public interface Toggleable {

    void setEnabled(boolean enabled);

    boolean isEnabled();

    void toggle();

    default void onEnable() {}

    default void onDisable() {}
}
