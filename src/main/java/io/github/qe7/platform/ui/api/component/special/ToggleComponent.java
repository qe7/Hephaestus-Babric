package io.github.qe7.platform.ui.api.component.special;

import io.github.qe7.platform.ui.api.component.AbstractComponent;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ToggleComponent extends AbstractComponent {

    private final BooleanSupplier supplier;
    private final Consumer<Boolean> action;

    public ToggleComponent(String name, Supplier<Boolean> visibilitySupplier, BooleanSupplier property, Consumer<Boolean> propertySetter) {
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
        getTheme().renderToggleComponent(this, supplier.getAsBoolean());
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseOver(mouseX, mouseY, getHeight())) {
            action.accept(!supplier.getAsBoolean());
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }
}
