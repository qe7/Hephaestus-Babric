package io.github.qe7.events;

import io.github.qe7.core.bus.Event;
import lombok.Data;
import net.minecraft.src.ScaledResolution;

@Data
public final class ScreenEvent implements Event {

    private final ScaledResolution scaledResolution;
}
