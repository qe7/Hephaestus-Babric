package io.github.qe7.core.feature.module.settings.impl;

import com.google.gson.JsonObject;
import io.github.qe7.core.feature.module.settings.AbstractSetting;
import io.github.qe7.core.ui.component.Component;
import io.github.qe7.core.ui.component.slider.SliderComponent;
import io.github.qe7.core.ui.component.slider.SliderHandler;
import lombok.Getter;

@Getter
public final class IntSetting extends AbstractSetting<Integer> {

    private final int min, max;

    public IntSetting(String name, int value, int min, int max, int increment) {
        super(name, value);
        this.min = min;
        this.max = max;
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

        this.setValue(object.get("value").getAsInt());
    }

    @Override
    public Component getComponent() {
        return new SliderComponent(this.getName(), this.getSupplier(), SliderHandler.simpleIntHandler(
                this::getValue,
                this::setValue,
                this.min,
                this.max));
    }
}
