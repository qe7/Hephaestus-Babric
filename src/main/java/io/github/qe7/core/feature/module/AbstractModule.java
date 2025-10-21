package io.github.qe7.core.feature.module;

import io.github.qe7.core.bus.Handler;
import io.github.qe7.core.common.*;
import io.github.qe7.core.feature.module.settings.AbstractSetting;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.FeatureComponent;
import io.github.qe7.core.ui.component.special.KeyBindComponent;
import io.github.qe7.core.ui.component.special.ToggleComponent;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.input.Keyboard;

@Getter
@Setter
public abstract class AbstractModule implements Global, Nameable, Displayable, Descriptionable, Hideable, Toggleable, Handler {

    private final ModuleCategory category;

    private final String name, description;
    private String suffix = null;

    private boolean hidden, drawn, enabled;

    private int keyBind = Keyboard.KEY_NONE;

    public AbstractModule(final String name, final String description, final ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.drawn = true;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public boolean isHidden() {
        return this.hidden;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;

        if (enabled) onEnable();
        else onDisable();
    }

    @Override
    public void toggle() {
        setEnabled(!this.enabled);
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean listening() {
        return this.isEnabled();
    }

    @Override
    public Component getComponent() {
        FeatureComponent component = new FeatureComponent(getName(), this::isEnabled, this::setEnabled);

        component.children.add(new KeyBindComponent("KeyBind", () -> true, this::getKeyBind, this::setKeyBind));

        component.children.add(new ToggleComponent("Drawn", () -> true, this::isDrawn, this::setDrawn));

        for (AbstractSetting<?> abstractSetting : ManagerFactory.get(ModuleManager.class).getSettingsByModule(this)) {
            if (abstractSetting.getComponent() != null) {
                component.children.add(abstractSetting.getComponent());
            }
        }

        return component;
    }
}
