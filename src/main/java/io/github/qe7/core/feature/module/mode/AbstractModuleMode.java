package io.github.qe7.core.feature.module.mode;

import io.github.qe7.core.bus.Handler;
import io.github.qe7.core.common.Globals;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.settings.AbstractSetting;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class AbstractModuleMode<T extends AbstractModule> implements Handler, Globals {

    private final List<AbstractSetting<?>> abstractSettings = new ArrayList<>();

    protected final T module;

    @Setter
    private BooleanSupplier supplier;
    private final String name;

    @Setter
    private boolean lastState;

    public AbstractModuleMode(T module, String name) {
        this.module = module;
        this.name = name;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    @Override
    public boolean listening() {
        boolean flag = module.isEnabled() && supplier.getAsBoolean();

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

    public List<AbstractSetting<?>> getAbstractSettings() {
        return abstractSettings;
    }

    public T getModule() {
        return module;
    }

    public BooleanSupplier getSupplier() {
        return supplier;
    }

    public String getName() {
        return name;
    }

    public boolean isLastState() {
        return lastState;
    }
}
