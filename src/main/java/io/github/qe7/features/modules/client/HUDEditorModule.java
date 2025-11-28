package io.github.qe7.features.modules.client;

import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.ModuleCategory;
import io.github.qe7.events.player.UpdateEvent;
import io.github.qe7.platform.ui.hudeditor.HUDEditorScreen;
import org.lwjgl.input.Keyboard;

public final class HUDEditorModule extends AbstractModule {

    private HUDEditorScreen hudEditorScreen;

    public HUDEditorModule() {
        super("HUD Editor", "Displays a clickable ui to manage hud features", ModuleCategory.CLIENT);

        this.getKeyBindSetting().setValue(Keyboard.KEY_GRAVE);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (hudEditorScreen == null) {
            hudEditorScreen = new HUDEditorScreen();
            hudEditorScreen.loadComponents();
        }
        mc.displayGuiScreen(hudEditorScreen);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (mc.currentScreen instanceof HUDEditorScreen) {
            mc.displayGuiScreen(null);
        }
    }

    @SubscribeEvent
    private final Listener<UpdateEvent> updateEventListener = event -> {
        // check if the GUI is still open, if not disable the module
        // ensures we don't reach a false enabled state. - Shae
        if (!(mc.currentScreen instanceof HUDEditorScreen)) {
            this.setEnabled(false);
        }
    };
}
