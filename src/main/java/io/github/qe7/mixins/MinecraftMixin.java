package io.github.qe7.mixins;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.TickEvent;
import io.github.qe7.events.KeyPressEvent;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public final class MinecraftMixin {

    @Inject(method = "runTick", at = @At("HEAD"))
    private void runTick(CallbackInfo ci) {
        ManagerFactory.get(EventManager.class).publishEvent(new TickEvent());
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z", shift = At.Shift.AFTER, ordinal = 1))
    private void afterKeyboardNext(CallbackInfo ci) {
        int key = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();

        if (Keyboard.getEventKeyState()) ManagerFactory.get(EventManager.class).publishEvent(new KeyPressEvent(key));
    }
}
