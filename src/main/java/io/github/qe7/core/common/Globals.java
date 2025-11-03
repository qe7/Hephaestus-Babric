package io.github.qe7.core.common;

import io.github.qe7.mixins.accessors.IAccessorMinecraft;
import net.minecraft.client.Minecraft;

public interface Globals {

    Minecraft mc = IAccessorMinecraft.getInstance();
}
