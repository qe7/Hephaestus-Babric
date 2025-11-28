package io.github.qe7.features.modules.render;

import io.github.qe7.HephaestusMod;
import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.core.feature.module.ModuleManager;
import io.github.qe7.core.feature.settings.impl.BooleanSetting;
import io.github.qe7.core.feature.settings.impl.ColorSetting;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.render.RenderScreenEvent;
import io.github.qe7.mixins.accessors.IAccessorMinecraft;
import io.github.qe7.toolbox.ChatUtil;
import io.github.qe7.toolbox.render.RenderUtil;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiChat;
import net.minecraft.src.ScaledResolution;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class HUDModule extends AbstractModule {

    private final BooleanSetting displayWatermark = new BooleanSetting("Watermark", true);
    private final BooleanSetting displayVersion = new BooleanSetting("Version", true).supplyIf(displayWatermark::getValue);

    private final BooleanSetting displayModuleList = new BooleanSetting("Module List", true);
    private final BooleanSetting displaySuffix = new BooleanSetting("Suffix", true).supplyIf(displayModuleList::getValue);
    private final BooleanSetting modulesUseCustomColor = new BooleanSetting("Modules Custom Color", false).supplyIf(displayModuleList::getValue);

    private final BooleanSetting displayFPS = new BooleanSetting("FPS", true);

    private final BooleanSetting displaySpeed = new BooleanSetting("Speed", true);
    private final BooleanSetting speedInKMH = new BooleanSetting("Speed in km/h", true).supplyIf(displaySpeed::getValue);

    private final BooleanSetting displayCoordinates = new BooleanSetting("Coordinates", true);
    private final BooleanSetting netherCoordinates = new BooleanSetting("Nether Coordinates", true).supplyIf(displayCoordinates::getValue);

    private final ColorSetting colorSetting = new ColorSetting("Color", new Color(132, 148, 255));

    private final List<Long> fpsCounter = new ArrayList<>();

    public HUDModule() {
        super("HUD", "Displays information related to the client", ModuleCategory.RENDER);

        this.setEnabled(true);
    }

    @SubscribeEvent
    private final Listener<RenderScreenEvent> renderScreenEventListener = event -> {
        if (mc.gameSettings.showDebugInfo) return;

        final FontRenderer fontRenderer = mc.fontRenderer;
        final ScaledResolution scaledResolution = event.getScaledResolution();

        fpsCounter.add(System.currentTimeMillis());
        fpsCounter.removeIf(time -> System.currentTimeMillis() - time > 1000);

        int topLeftHeight = 2;
        int topRightHeight = 2;
        int fontHeight = 9;
        int defaultOffset = fontHeight + 1;
        int chatOffset = mc.currentScreen instanceof GuiChat ? 14 : 0;
        int bottomLeftHeight = scaledResolution.getScaledHeight() - 2 - fontHeight - chatOffset;
        int bottomRightHeight = scaledResolution.getScaledHeight() - 2 - fontHeight - chatOffset;

        if (displayWatermark.getValue()) {
            final String watermark = HephaestusMod.INSTANCE.getName();
            RenderUtil.drawStringOutlined(watermark, 2, 2, colorSetting.getValue());

            if (displayVersion.getValue()) {
                final String version = HephaestusMod.INSTANCE.getVersion();
                fontRenderer.drawStringWithShadow(version, 2 + fontRenderer.getStringWidth(watermark + " "), topLeftHeight, -1);
            }
            topLeftHeight += defaultOffset;
        }

        if (displayModuleList.getValue()) {
            for (AbstractModule module : ManagerFactory.get(ModuleManager.class).getEnabledModules()) {
                if (!module.isDrawn()) {
                    continue;
                }

                String moduleName = module.getName();

                if (displaySuffix.getValue() && module.getSuffix() != null && !module.getSuffix().isEmpty()) {
                    moduleName += " " + ChatUtil.Formatting.WHITE + module.getSuffix();
                }

                final int stringWidth = fontRenderer.getStringWidth(moduleName);
                fontRenderer.drawStringWithShadow(moduleName, scaledResolution.getScaledWidth() - stringWidth - 2, topRightHeight, modulesUseCustomColor.getValue() ? colorSetting.getValue().getRGB() : module.getCategory().getColor());
                topRightHeight += defaultOffset;
            }
        }

        if (displayCoordinates.getValue()) {
            String coordinates = getCoordinates();
            fontRenderer.drawStringWithShadow(coordinates, 2, bottomLeftHeight, colorSetting.getValue().getRGB());
            bottomLeftHeight -= defaultOffset;
        }

        if (displaySpeed.getValue()) {
            String speed = getSpeed();
            fontRenderer.drawStringWithShadow(speed, scaledResolution.getScaledWidth() - fontRenderer.getStringWidth(speed) - 2, bottomRightHeight, colorSetting.getValue().getRGB());
            bottomRightHeight -= defaultOffset;
        }

        if (displayFPS.getValue()) {
            String fps = "FPS: " + ChatUtil.Formatting.WHITE + fpsCounter.size();
            fontRenderer.drawStringWithShadow(fps, scaledResolution.getScaledWidth() - fontRenderer.getStringWidth(fps) - 2, bottomRightHeight, colorSetting.getValue().getRGB());
            bottomRightHeight -= defaultOffset;
        }
    };

    private @NotNull String getSpeed() {
        double deltaX = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double deltaZ = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;

        double horizontalSpeed = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) * ((IAccessorMinecraft) mc).getTimer().ticksPerSecond;

        String speed;
        if (speedInKMH.getValue()) {
            speed = "Speed: " + ChatUtil.Formatting.WHITE + String.format("%.2f km/h", horizontalSpeed * 3.6);
        } else {
            speed = "Speed: " + ChatUtil.Formatting.WHITE + String.format("%.2f m/s", horizontalSpeed);
        }
        return speed;
    }

    private @NotNull String getCoordinates() {
        String coordinates = "XYZ: " + ChatUtil.Formatting.WHITE + (int) mc.thePlayer.posX + ", " + (int) mc.thePlayer.posY + ", " + (int) mc.thePlayer.posZ;

        // current_coords (opposite_dem_coords) calculated by dividing by 8 if in the nether and times by 8 if in the overworld
        if (netherCoordinates.getValue()) {
            if (mc.thePlayer.dimension == -1) { // Nether
                coordinates += " " + ChatUtil.Formatting.GRAY + "(" + ChatUtil.Formatting.WHITE + ((int) (mc.thePlayer.posX * 8) + ", " + (int) (mc.thePlayer.posZ * 8)) + ChatUtil.Formatting.GRAY + ")";
            } else { // Overworld
                coordinates += " " + ChatUtil.Formatting.GRAY + "(" + ChatUtil.Formatting.WHITE + ((int) (mc.thePlayer.posX / 8) + ", " + (int) (mc.thePlayer.posZ / 8)) + ChatUtil.Formatting.GRAY + ")";
            }
        }
        return coordinates;
    }
}
