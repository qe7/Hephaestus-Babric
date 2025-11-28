package io.github.qe7.mixins;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.feature.module.ModuleManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.render.RenderBlockSideEvent;
import io.github.qe7.features.modules.render.XRayModule;
import net.minecraft.src.Block;
import net.minecraft.src.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public final class MixinBlock {

    @Inject(method = "shouldSideBeRendered", at = @At(value = "HEAD"), cancellable = true)
    public void shouldSideBeRendered(IBlockAccess i, int j, int k, int l, int par5, CallbackInfoReturnable<Boolean> cir) {
        RenderBlockSideEvent event = new RenderBlockSideEvent((Block) (Object) this);
        XRayModule xRayModule = (XRayModule) ManagerFactory.get(ModuleManager.class).get(XRayModule.class);
        ManagerFactory.get(EventManager.class).publishEvent(event);

        if (xRayModule.isEnabled()) {
            if (event.isCancelled()) {
                cir.setReturnValue(false);
                cir.cancel();
            } else {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }
}
