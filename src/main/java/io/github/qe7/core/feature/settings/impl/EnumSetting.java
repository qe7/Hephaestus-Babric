package io.github.qe7.core.feature.settings.impl;

import com.google.gson.JsonObject;
import io.github.qe7.core.feature.settings.AbstractSetting;
import io.github.qe7.core.feature.settings.impl.interfaces.IEnumSetting;
import io.github.qe7.core.ui.component.AbstractComponent;
import io.github.qe7.core.ui.component.Component;
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
        return new AbstractComponent(getSupplier(), getName()) {
            @Override
            public void drawScreen(int mouseX, int mouseY, float partialTicks) {
                setHeight(getTheme().getDefaultHeaderHeight());
                getTheme().renderValueComponent(this, getValue().getName());
            }

            @Override
            public void keyTyped(char typedChar, int keyCode) {

            }

            @Override
            public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
                if (isMouseOver(mouseX, mouseY, getHeight())) {
                    switch (mouseButton) {
                        case 0: {
                            cycleForward();
                            break;
                        }
                        case 1: {
                            cycleBackward();
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(int mouseX, int mouseY, int state) {

            }
        };
    }
}
