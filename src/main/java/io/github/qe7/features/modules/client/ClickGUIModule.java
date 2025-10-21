package io.github.qe7.features.modules.client;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.events.UpdateEvent;
import io.github.qe7.platform.ui.ClickGuiScreen;
import lombok.Getter;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@Getter
public final class ClickGUIModule extends AbstractModule {

    private final Color primaryColor = new Color(132, 148, 255, 150);
    private final Color secondaryColor = new Color(132, 148, 255, 30);

    private final Color backgroundColor = new Color(0, 0, 0, 146);

    private final Color primaryTextColor = new Color(255, 255, 255, 255);
    private final Color secondaryTextColor = new Color(169, 169, 169, 255);

    private ClickGuiScreen clickGuiScreen;

    public ClickGUIModule() {
        super("Click GUI", "Displays a clickable interface to manage features", ModuleCategory.CLIENT);

        this.getKeyBindSetting().setValue(Keyboard.KEY_RSHIFT);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (clickGuiScreen == null) {
            clickGuiScreen = new ClickGuiScreen();
            clickGuiScreen.loadComponents();
        }
        mc.displayGuiScreen(clickGuiScreen);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (mc.currentScreen instanceof ClickGuiScreen) {
            mc.displayGuiScreen(null);
        }
    }

    @SubscribeEvent
    private final Listener<UpdateEvent> updateEventListener = event -> {
        // check if the GUI is still open, if not disable the module
        // ensures we don't reach a false enabled state. - qe7
        if (!(mc.currentScreen instanceof ClickGuiScreen)) {
            this.setEnabled(false);
        }
    };
}
