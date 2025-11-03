package io.github.qe7.mixins;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.GuiMouseReleasedEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiScreen;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    @Shadow
    protected Minecraft mc;

    @Shadow
    public int width;

    @Shadow
    public int height;

    @Shadow
    protected abstract void mouseClicked(int i, int j, int k);

    @Shadow
    protected abstract void mouseMovedOrUp(int i, int j, int k);

    /**
     * @author qe7
     * @reason Adding mouseReleased method.
     */
    @Overwrite
    public void handleMouseInput() {
        int i = (Mouse.getEventX() * width) / mc.displayWidth;
        int j = height - (Mouse.getEventY() * height) / mc.displayHeight - 1;
        int k = Mouse.getEventButton();

        if (Mouse.getEventButtonState()) {
            mouseClicked(i, j, k);
        } else {
            if (k != -1) {
                ManagerFactory.get(EventManager.class).publishEvent(new GuiMouseReleasedEvent(i, j, k));
            }
            mouseMovedOrUp(i, j, k);
        }
    }
}
