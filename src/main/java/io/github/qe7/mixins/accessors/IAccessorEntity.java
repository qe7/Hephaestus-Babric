package io.github.qe7.mixins.accessors;

import net.minecraft.src.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface IAccessorEntity {

    @Accessor("fallDistance")
    float getFallDistance();

    @Accessor("fallDistance")
    void setFallDistance(float distance);
}
