package io.github.qe7.platform.ui.clickgui.themes;

import io.github.qe7.core.common.Global;
import io.github.qe7.core.feature.module.ModuleManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.core.ui.component.special.ColorComponent;
import io.github.qe7.features.modules.client.ClickGUIModule;
import io.github.qe7.core.ui.Theme;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.FeatureComponent;
import io.github.qe7.core.ui.component.ParentComponent;
import io.github.qe7.core.ui.component.WindowComponent;
import io.github.qe7.core.ui.component.special.ToggleComponent;
import io.github.qe7.toolbox.render.GuiUtil;

import java.awt.*;

public final class DefaultTheme implements Theme, Global {

    private ClickGUIModule clickGUIModule;

    @Override
    public String getName() {
        return "Default";
    }

    // TODO: Make a manager for this, this is currently not called anywhere
    @Override
    public void load() {
        clickGUIModule = (ClickGUIModule) ManagerFactory.get(ModuleManager.class).get(ClickGUIModule.class);
    }

    @Override
    public float getWindowHeaderHeight() {
        return 14f;
    }

    @Override
    public float getWindowFooterHeight() {
        return 0.5f;
    }

    @Override
    public float getWindowBorderWidth() {
        return 0.5f;
    }

    @Override
    public float getFeatureHeaderHeight() {
        return 9 + 4;
    }

    @Override
    public float getFeatureFooterHeight() {
        return 0.f;
    }

    @Override
    public float getFeatureBorderWidth() {
        return 0.5f;
    }

    @Override
    public float getDefaultHeaderHeight() {
        return 9 + 4;
    }

    @Override
    public float getDefaultFooterHeight() {
        return 0.5f;
    }

    @Override
    public float getDefaultBorderWidth() {
        return 0.5f;
    }

    @Override
    public float getWindowTextOffset() {
        return 2f;
    }

    @Override
    public float getFeatureTextOffset() {
        return 3f;
    }

    @Override
    public float getDefaultTextOffset() {
        return 2f;
    }

    @Override
    public void renderParentComponent(ParentComponent component) {
        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);
    }

    @Override
    public void renderWindowComponent(WindowComponent component) {
        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getWindowHeaderHeight(), getClickGUIModule().getBackgroundColor().getValue().getRGB());

        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getWindowHeaderHeight(), getClickGUIModule().getPrimaryColor().getValue().getRGB());

        if (component.isOpen()) {
            GuiUtil.drawRect(component.getX(), component.getY() + getWindowHeaderHeight(), component.getX() + component.getWidth(), component.getY() + component.getHeight(), getClickGUIModule().getBackgroundColor().getValue().getRGB());
        }

        drawString(component.getName(), component.getX() + getWindowTextOffset(), component.getY() + ((getWindowHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);
    }

    @Override
    public void renderFeatureComponent(FeatureComponent component) {
        if (component.isEnabled()) {
            GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getFeatureHeaderHeight(), getClickGUIModule().getPrimaryColor().getValue().getRGB());
        } else {
            GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getFeatureHeaderHeight(), getClickGUIModule().getSecondaryColor().getValue().getRGB());
        }

        drawString(component.getName(), component.getX() + getFeatureTextOffset(), component.getY() + ((getFeatureHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);
    }

    @Override
    public void renderToggleComponent(ToggleComponent component, boolean value) {
        if (value) {
            GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getPrimaryColor().getValue().getRGB());
        } else {
            GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getSecondaryColor().getValue().getRGB());
        }

        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);
    }

    @Override
    public void renderValueComponent(Component component, String value) {
        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getPrimaryColor().getValue().getRGB());

        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);

        drawString(value, component.getX() + component.getWidth() - getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), false, true);
    }

    @Override
    public void renderSliderComponent(Component component, String value, float fill) {
        GuiUtil.drawRect(component.getX() + (component.getWidth() * fill), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getSecondaryColor().getValue().getRGB());

        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + (component.getWidth() * fill), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getPrimaryColor().getValue().getRGB());

        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);

        drawString(value, component.getX() + component.getWidth() - getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), false, true);
    }

    @Override
    public void renderTextComponent(Component component, String value) {
        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getPrimaryColor().getValue().getRGB());

        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);

        drawString(value, component.getX() + component.getWidth() - getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), false, true);
    }

    @Override
    public void renderColorComponent(ColorComponent component, Color currentColor, boolean open) {
        float x = component.getX();
        float y = component.getY();
        float width = component.getWidth();
        float height = this.getFeatureHeaderHeight();

        drawString(component.getName(), x + getDefaultTextOffset(), y + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);

        float boxSize = height - 4;
        float boxX = x + width - boxSize - 2;
        float boxY = y + 2;
        GuiUtil.drawRect(boxX, boxY, boxX + boxSize, boxY + boxSize, currentColor.getRGB());
    }

    @Override
    public void renderColorPicker(ColorComponent c, float hue, float saturation, float brightness, int alpha, String hex, boolean typingHex) {
        float x = c.getX();
        float y = c.getY() + getDefaultHeaderHeight() - 3;
        float totalWidth = c.getWidth();

        float uniformSpacing = Math.max(4f, totalWidth * 0.02f);
        float sliderWidth = Math.max(6f, totalWidth * 0.05f);

        float pickerSize = totalWidth - (sliderWidth * 2f + uniformSpacing * 4f);

        float pickerX = x + uniformSpacing;
        float pickerY = y + uniformSpacing;
        float hueX = pickerX + pickerSize + uniformSpacing;
        float alphaX = hueX + sliderWidth + uniformSpacing;
        float hexY = pickerY + pickerSize + uniformSpacing;

        float totalControlsWidth = (alphaX + sliderWidth) - pickerX;

        float btnSpacing = uniformSpacing;
        float btnWidth = Math.max(20f, totalControlsWidth * 0.15f);
        float hexWidth = totalControlsWidth - (btnWidth * 2f + btnSpacing * 2f);
        float hexHeight = 14f;

        for (int i = 0; i < pickerSize; i++) {
            float s = i / pickerSize;
            for (int j = 0; j < pickerSize; j++) {
                float b = 1f - (j / pickerSize);
                int color = Color.HSBtoRGB(hue, s, b);
                GuiUtil.drawRect(pickerX + i, pickerY + j, pickerX + i + 1, pickerY + j + 1, color);
            }
        }

        float sbIndicatorX = pickerX + saturation * pickerSize;
        float sbIndicatorY = pickerY + (1f - brightness) * pickerSize;
        float sbIndicatorSize = 4f;

        GuiUtil.drawRect(sbIndicatorX - sbIndicatorSize / 2f - 0.5f, sbIndicatorY - sbIndicatorSize / 2f - 0.5f, sbIndicatorX + sbIndicatorSize / 2f + 0.5f, sbIndicatorY + sbIndicatorSize / 2f + 0.5f, 0xFF000000);
        GuiUtil.drawRect(sbIndicatorX - sbIndicatorSize / 2f, sbIndicatorY - sbIndicatorSize / 2f, sbIndicatorX + sbIndicatorSize / 2f, sbIndicatorY + sbIndicatorSize / 2f, 0xFFFFFFFF);

        for (int j = 0; j < pickerSize; j++) {
            float h = j / pickerSize;
            int color = Color.HSBtoRGB(h, 1f, 1f);
            GuiUtil.drawRect(hueX, pickerY + j, hueX + sliderWidth, pickerY + j + 1, color);
        }

        float hueIndicatorY = pickerY + hue * pickerSize;
        float hueIndicatorHeight = 2f;

        GuiUtil.drawRect(hueX - 0.5f, hueIndicatorY - hueIndicatorHeight / 2f - 0.5f, hueX + sliderWidth + 0.5f, hueIndicatorY + hueIndicatorHeight / 2f + 0.5f, 0xFF000000);
        GuiUtil.drawRect(hueX, hueIndicatorY - hueIndicatorHeight / 2f, hueX + sliderWidth, hueIndicatorY + hueIndicatorHeight / 2f, 0xFFFFFFFF);

        Color base = Color.getHSBColor(hue, saturation, brightness);
        for (int j = 0; j < pickerSize; j++) {
            float t = 1f - (j / pickerSize);
            int col = new Color(base.getRed(), base.getGreen(), base.getBlue(), (int) (t * 255)).getRGB();
            GuiUtil.drawRect(alphaX, pickerY + j, alphaX + sliderWidth, pickerY + j + 1, col);
        }

        float alphaIndicatorY = pickerY + (1f - (alpha / 255f)) * pickerSize;
        float alphaIndicatorHeight = 2f;

        GuiUtil.drawRect(alphaX - 0.5f, alphaIndicatorY - alphaIndicatorHeight / 2f - 0.5f, alphaX + sliderWidth + 0.5f, alphaIndicatorY + alphaIndicatorHeight / 2f + 0.5f, 0xFF000000);
        GuiUtil.drawRect(alphaX, alphaIndicatorY - alphaIndicatorHeight / 2f, alphaX + sliderWidth, alphaIndicatorY + alphaIndicatorHeight / 2f, 0xFFFFFFFF);

        GuiUtil.drawRect(pickerX, hexY, pickerX + hexWidth, hexY + hexHeight, new Color(20, 20, 20, 180).getRGB());
        drawString(typingHex ? c.hexBox.getIdlingText() : c.hexBox.getText(), pickerX + 4, hexY + 3, true, false);

        float copyX = pickerX + hexWidth + btnSpacing;
        GuiUtil.drawRect(copyX, hexY, copyX + btnWidth, hexY + hexHeight, new Color(30, 30, 30, 220).getRGB());
        drawString("C", copyX + btnWidth / 2f - 3, hexY + 3, true, false);

        float pasteX = copyX + btnWidth + btnSpacing;
        GuiUtil.drawRect(pasteX, hexY, pasteX + btnWidth, hexY + hexHeight, new Color(30, 30, 30, 220).getRGB());
        drawString("P", pasteX + btnWidth / 2f - 3, hexY + 3, true, false);
    }

    @Override
    public ClickGUIModule getClickGUIModule() {
        // Hacky fix
        if (clickGUIModule == null) {
            clickGUIModule = (ClickGUIModule) ManagerFactory.get(ModuleManager.class).get(ClickGUIModule.class);
        }
        return clickGUIModule;
    }
}
