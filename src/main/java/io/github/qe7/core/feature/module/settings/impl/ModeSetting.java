package io.github.qe7.core.feature.module.settings.impl;

import com.google.gson.JsonObject;
import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.feature.module.mode.AbstractModuleMode;
import io.github.qe7.core.feature.module.settings.AbstractSetting;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.special.ModeComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ModeSetting extends AbstractSetting<AbstractModuleMode<?>> {

    private final List<AbstractModuleMode<?>> modes = new ArrayList<>();

    public ModeSetting(String name, String value, AbstractModuleMode<?>... modes) {
        super(name, new ArrayList<>(Arrays.asList(modes)).stream().filter(mode -> mode.getName().equals(value)).findAny().orElse(modes[0]));

        for (AbstractModuleMode<?> mode : modes) {
            mode.setSupplier(() -> mode.getModule().isEnabled() && mode.equals(getValue()));

            this.modes.add(mode);
            ManagerFactory.get(EventManager.class).registerHandler(mode);
        }
    }

    public void cycleForward() {
        int currentIndex = modes.indexOf(getValue());
        if (currentIndex == modes.size() - 1) {
            setValue(modes.get(0));
        } else {
            setValue(modes.get(currentIndex + 1));
        }
    }

    public void cycleBackward() {
        int currentIndex = modes.indexOf(getValue());
        if (currentIndex == 0) {
            setValue(modes.get(modes.size() - 1));
        } else {
            setValue(modes.get(currentIndex - 1));
        }
    }

    @Override
    public JsonObject serialize() {
        final JsonObject object = new JsonObject();

        object.addProperty("value", this.getValue().getName());

        return object;
    }

    @Override
    public void deserialize(JsonObject object) {
        if (!object.has("value") || !object.get("value").isJsonPrimitive()) {
            return;
        }

        final String name = object.get("value").getAsString();

        for (AbstractModuleMode<?> mode : modes) {
            if (mode.getName().equals(name)) {
                this.setValue(mode);
                break;
            }
        }
    }

    @Override
    public Component getComponent() {
        return new ModeComponent(this.getName(), this);
    }
}
