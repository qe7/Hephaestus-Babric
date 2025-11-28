package io.github.qe7.events.game;

import io.github.qe7.core.bus.Event;
import lombok.Data;

@Data
public final class GuiMouseReleasedEvent implements Event {

    private final int mouseX;
    private final int mouseY;
    private final int mouseButton;
}
