package io.github.qe7.core.feature.module.settings.impl;

import com.google.gson.JsonObject;
import io.github.qe7.core.feature.module.settings.AbstractSetting;
import io.github.qe7.platform.ui.api.component.Component;
import io.github.qe7.platform.ui.api.component.slider.SliderComponent;
import io.github.qe7.platform.ui.api.component.slider.SliderHandler;
import lombok.Getter;

@Getter
public final class FloatSetting extends AbstractSetting<Float> {

    private final float min, max;

    public FloatSetting(String name, float value, float min, float max, float increment) {
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

        this.setValue(object.get("value").getAsFloat());
    }

    @Override
    public Component getComponent() {
        return new SliderComponent(this.getName(), this.getSupplier(), SliderHandler.simpleFloatHandler(
                this::getValue,
                this::setValue,
                this.min,
                this.max));
    }
}
