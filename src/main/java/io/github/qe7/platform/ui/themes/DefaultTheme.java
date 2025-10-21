package io.github.qe7.platform.ui.themes;

import io.github.qe7.core.common.Global;
import io.github.qe7.core.feature.module.ModuleManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.features.modules.render.ClickGUIModule;
import io.github.qe7.core.ui.Theme;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.FeatureComponent;
import io.github.qe7.core.ui.component.ParentComponent;
import io.github.qe7.core.ui.component.WindowComponent;
import io.github.qe7.core.ui.component.special.EnumComponent;
import io.github.qe7.core.ui.component.special.KeyBindComponent;
import io.github.qe7.core.ui.component.special.ModeComponent;
import io.github.qe7.core.ui.component.special.ToggleComponent;
import io.github.qe7.toolbox.render.GuiUtil;
import org.lwjgl.input.Keyboard;

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
        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getWindowHeaderHeight(), getClickGUIModule().getBackgroundColor().getRGB());

        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getWindowHeaderHeight(), getClickGUIModule().getPrimaryColor().getRGB());

        if (component.isOpen()) {
            GuiUtil.drawRect(component.getX(), component.getY() + getWindowHeaderHeight(), component.getX() + component.getWidth(), component.getY() + component.getHeight(), getClickGUIModule().getBackgroundColor().getRGB());
        }

        drawString(component.getName(), component.getX() + getWindowTextOffset(), component.getY() + ((getWindowHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);
    }

    @Override
    public void renderFeatureComponent(FeatureComponent component) {
        if (component.isEnabled()) {
            GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getFeatureHeaderHeight(), getClickGUIModule().getPrimaryColor().getRGB());
        } else {
            GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getFeatureHeaderHeight(), getClickGUIModule().getSecondaryColor().getRGB());
        }

        drawString(component.getName(), component.getX() + getFeatureTextOffset(), component.getY() + ((getFeatureHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);
    }

    @Override
    public void renderToggleComponent(ToggleComponent component, boolean value) {
        if (value) {
            GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getPrimaryColor().getRGB());
        } else {
            GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getSecondaryColor().getRGB());
        }

        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);
    }

    @Override
    public void renderKeyBindComponent(KeyBindComponent component, int keyCode, boolean listening) {
        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getPrimaryColor().getRGB());

        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);

        if (listening) {
            drawString("Listening...", component.getX() + component.getWidth() - getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), false, true);
        } else {
            drawString(keyCode == -1 ? "None" : Keyboard.getKeyName(keyCode), component.getX() + component.getWidth() - getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), false, true);
        }
    }

    @Override
    public void renderEnumComponent(EnumComponent<?> component) {
        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getPrimaryColor().getRGB());

        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);

        drawString(component.getSetting().getValue().getName(), component.getX() + component.getWidth() - getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), false, true);
    }

    @Override
    public void renderModeComponent(ModeComponent component) {
        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getPrimaryColor().getRGB());

        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);

        drawString(component.getSetting().getValue().getName(), component.getX() + component.getWidth() - getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), false, true);
    }

    @Override
    public void renderSliderComponent(Component component, String value, float fill) {
        GuiUtil.drawRect(component.getX() + (component.getWidth() * fill), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getSecondaryColor().getRGB());

        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + (component.getWidth() * fill), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getPrimaryColor().getRGB());

        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);

        drawString(value, component.getX() + component.getWidth() - getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), false, true);
    }

    @Override
    public void renderTextComponent(Component component, String value) {
        GuiUtil.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(), component.getY() + getDefaultHeaderHeight(), getClickGUIModule().getPrimaryColor().getRGB());

        drawString(component.getName(), component.getX() + getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), true, false);

        drawString(value, component.getX() + component.getWidth() - getDefaultTextOffset(), component.getY() + ((getDefaultHeaderHeight() / 2.0f) - (9 / 2.0f)), false, true);
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
