package io.github.qe7.core.ui.component.special;

import io.github.qe7.core.ui.component.AbstractComponent;
import org.lwjgl.input.Keyboard;

import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class KeyBindComponent extends AbstractComponent {

    private final IntSupplier supplier;
    private final Consumer<Integer> action;

    private boolean listening = false;

    public KeyBindComponent(String name, Supplier<Boolean> visibilitySupplier, IntSupplier property, Consumer<Integer> propertySetter) {
        super(visibilitySupplier, name);
        this.supplier = property;
        this.action = propertySetter;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (getTheme() == null) {
            return;
        }

        setHeight(getTheme().getDefaultHeaderHeight());
        getTheme().renderKeyBindComponent(this, supplier.getAsInt(), listening);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (listening) {
            action.accept(
                (keyCode == Keyboard.KEY_DELETE || keyCode == Keyboard.KEY_BACK)
                    ? Keyboard.KEY_NONE
                    : keyCode
            );
            listening = false;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseOver(mouseX, mouseY, getHeight())) {
            listening = !listening;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}
