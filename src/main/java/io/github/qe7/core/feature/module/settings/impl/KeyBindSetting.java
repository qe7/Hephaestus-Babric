package io.github.qe7.core.feature.module.settings.impl;

import com.google.gson.JsonObject;
import io.github.qe7.core.feature.module.settings.AbstractSetting;
import io.github.qe7.core.ui.component.AbstractComponent;
import io.github.qe7.core.ui.component.Component;
import org.lwjgl.input.Keyboard;

public final class KeyBindSetting extends AbstractSetting<Integer> {

    public KeyBindSetting(String name, int value) {
        super(name, value);
    }

    @Override
    public Component getComponent() {
        return new AbstractComponent(getSupplier(), getName()) {
            private boolean listening = false;

            @Override
            public void drawScreen(int mouseX, int mouseY, float partialTicks) {
                String displayText = listening ? "..." : Keyboard.getKeyName(getValue());

                setHeight(getTheme().getDefaultHeaderHeight());
                getTheme().renderValueComponent(this, displayText);
            }

            @Override
            public void keyTyped(char typedChar, int keyCode) {
                if (!listening) {
                    return;
                }

                switch (keyCode) {
                    case Keyboard.KEY_DELETE:
                    case Keyboard.KEY_BACK:
                        keyCode = Keyboard.KEY_NONE;
                        break;
                }

                setValue(keyCode);
                listening = false;
            }

            @Override
            public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
                if (isMouseOver(mouseX, mouseY, getHeight()) && mouseButton == 0) {
                    listening = !listening;
                } else {
                    listening = false;
                }
            }

            @Override
            public void mouseReleased(int mouseX, int mouseY, int state) {

            }
        };
    }

    @Override
    public JsonObject serialize() {
        return null;
    }

    @Override
    public void deserialize(JsonObject object) {

    }
}
