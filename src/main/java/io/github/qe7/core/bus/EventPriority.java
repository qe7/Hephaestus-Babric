package io.github.qe7.core.bus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventPriority {
    LOWEST(0), LOW(1), NORMAL(2), HIGH(3), HIGHEST(4);

    private final int value;
}
