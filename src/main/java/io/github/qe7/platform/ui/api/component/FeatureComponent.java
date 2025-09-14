package io.github.qe7.platform.ui.api.component;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class FeatureComponent extends ParentComponent {

    private final BooleanSupplier supplier;
    private final Consumer<Boolean> action;

    public FeatureComponent(String name, BooleanSupplier supplier, Consumer<Boolean> action) {
        super(name);

        this.supplier = supplier != null ? supplier : () -> true;
        this.action = action != null ? action : b -> {
        };
    }

    public FeatureComponent(String name) {
        this(name, () -> true, b -> {
        });
    }

    public boolean isEnabled() {
        return supplier.getAsBoolean();
    }

    @Override
    public float getHeaderHeight() {
        return getTheme().getFeatureHeaderHeight();
    }

    @Override
    public float getFooterHeight() {
        return getTheme().getFeatureFooterHeight();
    }

    @Override
    public float getBorderWidth() {
        return getTheme().getFeatureBorderWidth();
    }

    @Override
    protected void renderComponent() {
        getTheme().renderFeatureComponent(this);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseOver(mouseX, mouseY, getHeaderHeight()) && mouseButton == 0) {
            action.accept(!supplier.getAsBoolean());
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
