package io.github.qe7.events;

import io.github.qe7.core.bus.Event;
import lombok.Data;

@Data
public final class KeyPressEvent implements Event {

    private final int keyCode;
}
