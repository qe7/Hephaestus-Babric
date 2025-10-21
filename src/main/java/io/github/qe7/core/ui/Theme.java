package io.github.qe7.core.ui;

import io.github.qe7.core.common.Global;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.features.modules.render.ClickGUIModule;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.FeatureComponent;
import io.github.qe7.core.ui.component.ParentComponent;
import io.github.qe7.core.ui.component.WindowComponent;
import io.github.qe7.core.ui.component.special.EnumComponent;
import io.github.qe7.core.ui.component.special.KeyBindComponent;
import io.github.qe7.core.ui.component.special.ModeComponent;
import io.github.qe7.core.ui.component.special.ToggleComponent;
import io.github.qe7.toolbox.render.font.FontManager;
import io.github.qe7.toolbox.render.font.FontType;
import io.github.qe7.toolbox.render.font.renderer.TTFRenderer;

public interface Theme extends Global {

    String getName();

    void load();

    float getWindowHeaderHeight();

    float getWindowFooterHeight();

    float getWindowBorderWidth();

    float getFeatureHeaderHeight();

    float getFeatureFooterHeight();

    float getFeatureBorderWidth();

    float getDefaultHeaderHeight();

    float getDefaultFooterHeight();

    float getDefaultBorderWidth();

    float getWindowTextOffset();

    float getFeatureTextOffset();

    float getDefaultTextOffset();

    void renderParentComponent(ParentComponent parentComponent);

    void renderWindowComponent(WindowComponent windowComponent);

    void renderFeatureComponent(FeatureComponent featureComponent);

    void renderToggleComponent(ToggleComponent toggleComponent, boolean value);

    void renderKeyBindComponent(KeyBindComponent keyBindComponent, int keyCode, boolean listening);

    void renderEnumComponent(EnumComponent<?> enumComponent);

    void renderModeComponent(ModeComponent modeComponent);

    void renderSliderComponent(Component component, String value, float fill);

    void renderTextComponent(Component component, String text);

    ClickGUIModule getClickGUIModule();

    default void drawString(String text, float x, float y, boolean primaryColor, boolean rightAligned) {
        final TTFRenderer fontRenderer = ManagerFactory.get(FontManager.class).getFontRenderer(FontType.TAHOMA, 18);

        final int color = primaryColor
                ? getClickGUIModule().getPrimaryTextColor().getRGB()
                : getClickGUIModule().getSecondaryTextColor().getRGB();

        final int alignOffset = (int) (rightAligned
                ? x - fontRenderer.getWidth(text)
                : x);

        fontRenderer.drawStringWithShadow(text, alignOffset, (int) y, color);
    }
}
