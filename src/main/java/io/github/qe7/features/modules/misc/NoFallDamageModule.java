package io.github.qe7.features.modules.misc;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.events.UpdateEvent;
import io.github.qe7.toolbox.PacketUtil;
import io.github.qe7.toolbox.mixin.IEntityMixin;
import net.minecraft.src.Packet10Flying;

/**
 * TODO: Improve NoFallDamageModule
 *  to work better by making it spoof onGround state instead of sending a new one
 *  which will typically cause timer related rubberbanding. - Shae
 */
public final class NoFallDamageModule extends AbstractModule {

    public NoFallDamageModule() {
        super("No Fall Damage", "Reduces/removes fall damage... duh", ModuleCategory.MISC);
    }

    @SubscribeEvent
    private final Listener<UpdateEvent> updateEventListener = event -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (((IEntityMixin) mc.thePlayer).hephaestus_Babric$getFallDistance() > 2.5f) {
            ((IEntityMixin) mc.thePlayer).hephaestus_Babric$setFallDistance(0.0f);
            PacketUtil.sendPacket(new Packet10Flying(true));
        }
    };
}
