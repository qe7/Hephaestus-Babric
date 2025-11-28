package io.github.qe7.events.render;

import io.github.qe7.core.bus.CancellableEvent;
import lombok.Getter;
import net.minecraft.src.Block;

@Getter
public final class RenderBlockSideEvent implements CancellableEvent {

    private final Block block;

    private boolean cancelled;

    public RenderBlockSideEvent(Block block) {
        this.block = block;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
