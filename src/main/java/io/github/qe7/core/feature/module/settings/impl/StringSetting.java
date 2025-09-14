package io.github.qe7.core.feature.module.settings.impl;

import com.google.gson.JsonObject;
import io.github.qe7.core.feature.module.settings.AbstractSetting;
import io.github.qe7.platform.ui.api.component.Component;
import io.github.qe7.platform.ui.api.component.special.TextComponent;

public final class StringSetting extends AbstractSetting<String> {

    public StringSetting(String name, String value) {
        super(name, value);
    }

    @Override
    public JsonObject serialize() {
        final JsonObject object = new JsonObject();

        object.addProperty("value", this.getValue());

        return object;
    }

    @Override
    public void deserialize(JsonObject object) {
        if (!object.has("value") || !object.get("value").isJsonPrimitive()) {
            return;
        }

        this.setValue(object.get("value").getAsString());
    }

    @Override
    public Component getComponent() {
        return new TextComponent(this.getName(), this.getSupplier(), this::getValue, this::setValue);
    }
}
