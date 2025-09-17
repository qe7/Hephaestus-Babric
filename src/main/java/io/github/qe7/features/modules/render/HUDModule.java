package io.github.qe7.features.modules.render;

import io.github.qe7.HephaestusMod;
import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.core.feature.module.ModuleManager;
import io.github.qe7.core.feature.module.settings.impl.BooleanSetting;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.ScreenEvent;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ScaledResolution;

import java.util.Comparator;
import java.util.List;

public final class HUDModule extends AbstractModule {

    private final BooleanSetting displayWatermark = new BooleanSetting("Display Watermark", true);

    private final BooleanSetting displayModuleList = new BooleanSetting("Display Module List", true);

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

        if (this.displayModuleList.getValue()) {
            final List<AbstractModule> moduleList = ManagerFactory.get(ModuleManager.class).getEnabledModules();

            moduleList.sort(Comparator.comparingInt(abstractModule -> -fontRenderer.getStringWidth(abstractModule.getName() + (abstractModule.getSuffix() != null ? " " + abstractModule.getSuffix() : ""))));

            int offset = 2;
            for (AbstractModule abstractModule : moduleList) {
                if (!abstractModule.isDrawn()) {
                    continue;
                }

                final String display = abstractModule.getName() + (abstractModule.getSuffix() != null ? " " + "§7" + abstractModule.getSuffix() : "");

                fontRenderer.drawStringWithShadow(display, event.getScaledResolution().getScaledWidth() - fontRenderer.getStringWidth(display) - 2, offset, -1);
                offset += 9;
            }
        }
    };
}
