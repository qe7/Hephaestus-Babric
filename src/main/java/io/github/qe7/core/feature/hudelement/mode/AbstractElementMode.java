package io.github.qe7.core.feature.hudelement.mode;

import io.github.qe7.core.bus.Handler;
import io.github.qe7.core.common.Globals;
import io.github.qe7.core.feature.hudelement.AbstractHUDElement;
import io.github.qe7.core.feature.settings.AbstractSetting;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class AbstractElementMode<T extends AbstractHUDElement> implements Handler, Globals {

    @Getter
    private final List<AbstractSetting<?>> abstractSettings = new ArrayList<>();

    protected final T element;

    @Setter
    @Getter
    private BooleanSupplier supplier;

    @Getter
    private final String name;

    @Getter
    @Setter
    private boolean lastState;

    public AbstractElementMode(T element, String name) {
        this.element = element;
        this.name = name;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    @Override
    public boolean listening() {
        boolean flag = element.isEnabled() && supplier.getAsBoolean();

        if (lastState != flag) {
            if (flag) {
                onEnable();
            } else {
                onDisable();
            }

            lastState = flag;
        }

        return flag;
    }

    public T getModule() {
        return element;
    }
}
