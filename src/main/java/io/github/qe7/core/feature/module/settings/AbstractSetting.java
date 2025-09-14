package io.github.qe7.core.feature.module.settings;

import io.github.qe7.core.common.Displayable;
import io.github.qe7.core.common.Serialized;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

@Getter
public abstract class AbstractSetting<T> implements Serialized, Displayable {

    private final String name;
    @Setter
    private T value;

    private Supplier<Boolean> supplier = () -> true;

    public AbstractSetting(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public boolean shouldHide() {
        return supplier != null && !supplier.get();
    }

    public <V extends AbstractSetting<?>> V supplyIf(Supplier<Boolean> supplier) {
        this.supplier = supplier;
        return (V) this;
    }
}
