package io.github.qe7.features.modules.render;

import io.github.qe7.HephaestusMod;
import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.core.feature.module.settings.impl.BooleanSetting;
import io.github.qe7.events.ScreenEvent;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ScaledResolution;

public final class HUDModule extends AbstractModule {

    private final BooleanSetting displayWatermark = new BooleanSetting("Display Watermark", true);

    public HUDModule() {
        super("HUD", "Displays information related to the client", ModuleCategory.RENDER);

        this.setEnabled(true);
    }

    @SubscribeEvent
    private final Listener<ScreenEvent> renderScreenEventListener = event -> {
        if (mc.gameSettings.showDebugInfo) return;

        final FontRenderer fontRenderer = mc.fontRenderer;
        final ScaledResolution scaledResolution = event.getScaledResolution();

        if (this.displayWatermark.getValue()) {
            fontRenderer.drawStringWithShadow(HephaestusMod.INSTANCE.getName() + " " + HephaestusMod.INSTANCE.getVersion(), 2, 2, -1);
        }
    };
}
