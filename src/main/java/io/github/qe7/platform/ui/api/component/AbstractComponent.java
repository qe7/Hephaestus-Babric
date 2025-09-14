package io.github.qe7.platform.ui.api.component;

import io.github.qe7.core.feature.module.ModuleManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.features.modules.render.ClickGUIModule;
import io.github.qe7.platform.ui.api.Theme;
import io.github.qe7.platform.ui.impl.themes.DefaultTheme;

import java.util.function.Supplier;

public abstract class AbstractComponent implements Component {

    private static final ClickGUIModule CLICK_GUI = (ClickGUIModule) ManagerFactory.get(ModuleManager.class).get(ClickGUIModule.class);
    private final Supplier<Boolean> visibilitySupplier;

    protected String name;
    protected float x, y, width, height;
    protected int alpha;

    public AbstractComponent(Supplier<Boolean> visibilitySupplier, String name) {
        this.visibilitySupplier = visibilitySupplier;
        this.name = name;
    }

    public AbstractComponent(Supplier<Boolean> visibilitySupplier) {
        this.visibilitySupplier = visibilitySupplier;
    }

    public AbstractComponent() {
        this(() -> true, "Component");
    }

    @Override
    public boolean isVisible() {
        return visibilitySupplier.get();
    }

    public ClickGUIModule getClickGui() {
        return CLICK_GUI;
    }

    public Theme getTheme() {
        return new DefaultTheme();
    }

    public Supplier<Boolean> getVisibilitySupplier() {
        return visibilitySupplier;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
}
