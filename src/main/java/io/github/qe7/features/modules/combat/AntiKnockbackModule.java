package io.github.qe7.features.modules.combat;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.events.PacketEvent;
import net.minecraft.src.Packet28EntityVelocity;

public final class AntiKnockbackModule extends AbstractModule {

    public AntiKnockbackModule() {
        super("Anti Knockback", "Removes knock back given to the player", ModuleCategory.COMBAT);
    }

    @SubscribeEvent
    private final Listener<PacketEvent> packetEventListener = event -> {
        if (mc.thePlayer == null) {
            return;
        }

        if (!event.isIncoming()) {
            return;
        }

        if (!(event.getPacket() instanceof Packet28EntityVelocity)) return;

        final Packet28EntityVelocity packet = (Packet28EntityVelocity) event.getPacket();

        if (packet.entityId != mc.thePlayer.entityId) {
            return;
        }

        event.setCancelled(true);
    };
}
