package io.github.qe7.features.modules.movement;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.events.player.UpdateEvent;
import org.lwjgl.input.Keyboard;

public final class FlightModule extends AbstractModule {

    public FlightModule() {
        super("Flight", "Fly like super man", ModuleCategory.MOVEMENT);
    }

    @SubscribeEvent
    private final Listener<UpdateEvent> updateEventListener = event -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        double motionY = 0.0;

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindJump.keyCode)) {
            motionY += 0.5;
        }

        if (Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.keyCode)) {
            motionY -= 0.5;
        }

        mc.thePlayer.motionY = motionY;
        mc.thePlayer.onGround = true;
    };
}
