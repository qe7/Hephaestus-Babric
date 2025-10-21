package io.github.qe7.features.modules.movement.speed.impl;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.mode.AbstractModuleMode;
import io.github.qe7.events.UpdateEvent;
import io.github.qe7.features.modules.movement.speed.SpeedModule;
import io.github.qe7.toolbox.mixin.IMinecraftMixin;

public final class PulseSpeedMode extends AbstractModuleMode<SpeedModule> {

    private int pulseTick = 0;

    public PulseSpeedMode(SpeedModule module, String name) {
        super(module, name);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        this.pulseTick = 0;
    }

    @SubscribeEvent
    private final Listener<UpdateEvent> updateEventListener = event -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        if (pulseTick <= 35) {
            ((IMinecraftMixin) mc).hephaestus_Babric$getTimer().timerSpeed = 2.0F;
        } else if (pulseTick == 176) {
            pulseTick = 0;
        } else {
            ((IMinecraftMixin)mc).hephaestus_Babric$getTimer().timerSpeed = 1.0F;
        }

        pulseTick++;
    };
}
