package io.github.qe7.features.modules.client;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.core.feature.settings.impl.BooleanSetting;
import io.github.qe7.core.feature.settings.impl.ColorSetting;
import io.github.qe7.core.feature.settings.impl.IntSetting;
import io.github.qe7.events.UpdateEvent;
import io.github.qe7.platform.ui.clickgui.ClickGUIScreen;
import lombok.Getter;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@Getter
public final class ClickGUIModule extends AbstractModule {

    private final IntSetting width = new IntSetting("Width", 120, 80, 140, 1);

    private final ColorSetting primaryColor = new ColorSetting("Primary Color", new Color(132, 148, 255, 150));
    private final ColorSetting secondaryColor = new ColorSetting("Secondary Color", new Color(132, 148, 255, 30));

    private final ColorSetting backgroundColor = new ColorSetting("Background Color", new Color(0, 0, 0, 146));

    private final ColorSetting primaryTextColor = new ColorSetting("Primary Text Color", new Color(255, 255, 255, 255));
    private final ColorSetting secondaryTextColor = new ColorSetting("Secondary Text Color", new Color(169, 169, 169, 255));

    private ClickGUIScreen clickGuiScreen;

    public ClickGUIModule() {
        super("Click GUI", "Displays a clickable interface to manage features", ModuleCategory.CLIENT);

        this.getKeyBindSetting().setValue(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (clickGuiScreen == null) {
            clickGuiScreen = new ClickGUIScreen();
            clickGuiScreen.loadComponents();
        }
        mc.displayGuiScreen(clickGuiScreen);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (mc.currentScreen instanceof ClickGUIScreen) {
            mc.displayGuiScreen(null);
        }
    }

    @SubscribeEvent
    private final Listener<UpdateEvent> updateEventListener = event -> {
        // check if the GUI is still open, if not disable the module
        // ensures we don't reach a false enabled state. - Shae
        if (!(mc.currentScreen instanceof ClickGUIScreen)) {
            this.setEnabled(false);
        }
    };
}
