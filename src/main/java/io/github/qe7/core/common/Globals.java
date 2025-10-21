package io.github.qe7.core.common;

import io.github.qe7.mixins.accessors.MinecraftAccessor;
import net.minecraft.client.Minecraft;

public interface Globals {

    Minecraft mc = MinecraftAccessor.getInstance();
}
