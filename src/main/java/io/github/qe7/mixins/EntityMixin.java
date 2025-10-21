package io.github.qe7.mixins;

import io.github.qe7.toolbox.mixin.IEntityMixin;
import net.minecraft.src.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public final class EntityMixin implements IEntityMixin {

    @Shadow
    private float fallDistance;

    @Override
    public float hephaestus_Babric$getFallDistance() {
        return fallDistance;
    }

    @Override
    public void hephaestus_Babric$setFallDistance(float distance) {
        this.fallDistance = distance;
    }
}
