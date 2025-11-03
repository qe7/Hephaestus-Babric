package io.github.qe7.mixins.accessors;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface IAccessorMinecraft {

    @Accessor("theMinecraft")
    static Minecraft getInstance() {
        throw new AssertionError();
    }

    @Accessor("timer")
    Timer getTimer();
}
