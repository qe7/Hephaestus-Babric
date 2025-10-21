package io.github.qe7.core.feature.settings.impl;

import com.google.gson.JsonObject;
import io.github.qe7.core.feature.settings.AbstractSetting;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.special.ColorComponent;

import java.awt.*;

public final class ColorSetting extends AbstractSetting<Color> {

    public ColorSetting(String name, Color value) {
        super(name, value);
    }

    @Override
    public Component getComponent() {
        return new ColorComponent(getName(), () -> true, this::getValue, this::setValue);
    }

    @Override
    public JsonObject serialize() {
        final JsonObject object = new JsonObject();

        object.addProperty("value", this.getValue().getRGB());

        return object;
    }

    @Override
    public void deserialize(JsonObject object) {
        if (!object.has("value") || !object.get("value").isJsonPrimitive()) {
            return;
        }

        this.setValue(object.get("value").getAsString().isEmpty() ? new Color(255, 255, 255) : new Color(object.get("value").getAsInt(), true));
    }
}
