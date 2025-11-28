package io.github.qe7.events.player;

import io.github.qe7.core.bus.CancellableEvent;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.src.Packet;

@Getter
@Setter
public final class PacketEvent implements CancellableEvent {

    private final Type type;

    private final Packet packet;

    private boolean cancelled;

    public PacketEvent(Type type, Packet packet) {
        this.type = type;
        this.packet = packet;
    }

    public boolean isIncoming() {
        return type == Type.INCOMING;
    }

    public boolean isOutgoing() {
        return type == Type.OUTGOING;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum Type {
        INCOMING, OUTGOING
    }
}
