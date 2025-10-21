package io.github.qe7.features.modules.combat;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.core.feature.settings.impl.BooleanSetting;
import io.github.qe7.core.feature.settings.impl.FloatSetting;
import io.github.qe7.core.feature.settings.impl.IntSetting;
import io.github.qe7.events.UpdateEvent;
import io.github.qe7.toolbox.Stopwatch;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public final class ForceFieldModule extends AbstractModule {

    private final BooleanSetting swing = new BooleanSetting("Swing", true);

    private final FloatSetting range = new FloatSetting("Range", 4.2f, 1.0f, 6.0f, 0.1f);

    private final IntSetting attacksPerSecond = new IntSetting("Attacks/s", 2, 1, 20, 1);
    private final IntSetting maxTargets = new IntSetting("Max Targets", 3, 1, 20, 1);

    private final Stopwatch stopwatch = new Stopwatch();

    private List<EntityLiving> targetList = new ArrayList<>();

    public ForceFieldModule() {
        super("Force Field", "Automatically attacks entities in a given radius", ModuleCategory.COMBAT);
    }

    @SubscribeEvent
    private final Listener<UpdateEvent> updateEventListener = event -> {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }

        List<EntityLiving> filteredEntities = (List<EntityLiving>) mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityLiving).collect(Collectors.toList());

        filteredEntities.removeIf(entityLiving -> entityLiving.getDistanceToEntity(mc.thePlayer) > range.getValue() || entityLiving == mc.thePlayer || entityLiving.health <= 0);

        filteredEntities.sort(Comparator.comparingDouble(entity -> entity.getDistanceSqToEntity(mc.thePlayer)));

        targetList = new ArrayList<>(filteredEntities.subList(0, Math.min(maxTargets.getValue(), filteredEntities.size())));

        if (stopwatch.hasTimeElapsed(1000L / attacksPerSecond.getValue(), TimeUnit.MILLISECONDS, true)) {
            targetList.forEach(this::handleAttack);
        }
    };

    private void handleAttack(Entity target) {
        if (swing.getValue()) {
            mc.thePlayer.swingItem();
        }

        mc.thePlayer.attackTargetEntityWithCurrentItem(target);
    }
}
