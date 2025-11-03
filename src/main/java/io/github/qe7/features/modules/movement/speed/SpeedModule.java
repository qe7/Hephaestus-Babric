package io.github.qe7.features.modules.movement.speed;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.core.feature.settings.impl.ModeSetting;
import io.github.qe7.events.ScreenEvent;
import io.github.qe7.features.modules.movement.speed.impl.ConstantSpeedMode;
import io.github.qe7.features.modules.movement.speed.impl.PulseSpeedMode;
import io.github.qe7.mixins.accessors.IAccessorMinecraft;

public final class SpeedModule extends AbstractModule {

    private final ModeSetting mode = new ModeSetting("Mode", "Constant", new ConstantSpeedMode(this, "Constant"), new PulseSpeedMode(this, "Pulse"));

    public SpeedModule() {
        super("Speed", "Allows you to travel at speeds normally not reachable", ModuleCategory.MOVEMENT);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        ((IAccessorMinecraft) mc).getTimer().timerSpeed = 1.0F;
    }

    @SubscribeEvent
    private final Listener<ScreenEvent> screenEventListener = event -> {
        this.setSuffix(this.mode.getValue().getName());
    };
}
