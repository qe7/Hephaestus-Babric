package io.github.qe7.events.render;

import io.github.qe7.core.bus.Event;
import lombok.Data;
import net.minecraft.src.ScaledResolution;

@Data
public final class RenderScreenEvent implements Event {

    private final ScaledResolution scaledResolution;
}
