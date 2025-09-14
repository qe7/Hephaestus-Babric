package io.github.qe7.core.feature.module.settings.impl;

import com.google.gson.JsonObject;
import io.github.qe7.core.feature.module.settings.AbstractSetting;
import io.github.qe7.core.feature.module.settings.impl.interfaces.IEnumSetting;
import io.github.qe7.platform.ui.api.component.Component;
import io.github.qe7.platform.ui.api.component.special.EnumComponent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public final class EnumSetting<T extends IEnumSetting> extends AbstractSetting<T> {

    private final List<IEnumSetting> values;

    public EnumSetting(String name, T value) {
        super(name, value);
        this.values = new ArrayList<>(Arrays.asList(value.getClass().getEnumConstants()));
    }

    public void cycleForward() {
        int currentIndex = values.indexOf(getValue());
        if (currentIndex == values.size() - 1) {
            setValue((T) values.get(0));
        } else {
            setValue((T) values.get(currentIndex + 1));
        }
    }

    public void cycleBackward() {
        int currentIndex = values.indexOf(getValue());
        if (currentIndex == 0) {
            setValue((T) values.get(values.size() - 1));
        } else {
            setValue((T) values.get(currentIndex - 1));
        }
    }

    public void setValueByName(String name) {
        for (IEnumSetting value : values) {
            if (value.getName().equalsIgnoreCase(name)) {
                setValue((T) value);
                return;
            }
        }
    }

    public void setCastedValue(IEnumSetting property) {
        setValue((T) property);
    }

    public T getValueByIndex(int index) {
        return (T) values.get(index);
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

        for (IEnumSetting value : values) {
            if (value.getName().equals(name)) {
                this.setValue((T) value);
                break;
            }
        }
    }

    @Override
    public Component getComponent() {
        return new EnumComponent<>(this.getName(), this);
    }
}
