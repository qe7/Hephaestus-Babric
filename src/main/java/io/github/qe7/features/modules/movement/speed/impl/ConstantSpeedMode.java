package io.github.qe7.features.modules.movement.speed.impl;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.mode.AbstractModuleMode;
import io.github.qe7.events.player.UpdateEvent;
import io.github.qe7.features.modules.movement.speed.SpeedModule;
import io.github.qe7.mixins.accessors.IAccessorMinecraft;

public final class ConstantSpeedMode extends AbstractModuleMode<SpeedModule> {

    public ConstantSpeedMode(SpeedModule module, String name) {
        super(module, name);
    }

    @SubscribeEvent
    private final Listener<UpdateEvent> updateEventListener = event -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        ((IAccessorMinecraft) mc).getTimer().timerSpeed = 1.117F;
    };
}
