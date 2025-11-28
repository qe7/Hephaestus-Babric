package io.github.qe7.mixins;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.render.RenderScreenEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public final class MixinGuiIngame {

    @Shadow
    private Minecraft mc;

    @Inject(method = "renderGameOverlay(FZII)V", at = @At("TAIL"))
    private void afterRenderGameOverlay(float f, boolean bl, int i, int j, CallbackInfo ci) {
        GL11.glPushMatrix();

        final ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        ManagerFactory.get(EventManager.class).publishEvent(new RenderScreenEvent(scaledResolution));

        GL11.glPopMatrix();
    }
}
