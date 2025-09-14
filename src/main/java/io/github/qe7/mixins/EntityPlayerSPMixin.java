package io.github.qe7.mixins;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.UpdateEvent;
import net.minecraft.src.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public final class EntityPlayerSPMixin {

    @Inject(method = "onLivingUpdate", at = @At("HEAD"))
    public void onLivingUpdate(CallbackInfo ci) {
        ManagerFactory.get(EventManager.class).publishEvent(new UpdateEvent());
    }
}
