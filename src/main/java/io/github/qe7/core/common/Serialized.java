package io.github.qe7.core.common;

import com.google.gson.JsonObject;

public interface Serialized {

    JsonObject serialize();

    void deserialize(JsonObject object);
}