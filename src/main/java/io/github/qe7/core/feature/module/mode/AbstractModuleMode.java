package io.github.qe7.core.feature.module.mode;

import io.github.qe7.core.bus.Handler;
import io.github.qe7.core.common.Global;
import io.github.qe7.core.feature.module.AbstractModule;
import io.github.qe7.core.feature.module.settings.AbstractSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class AbstractModuleMode<T extends AbstractModule> implements Handler, Global {

    private final List<AbstractSetting<?>> abstractSettings = new ArrayList<>();

    protected final T module;
    private BooleanSupplier supplier;
    private final String name;
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

    public void setSupplier(BooleanSupplier supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public boolean isLastState() {
        return lastState;
    }

    public void setLastState(boolean lastState) {
        this.lastState = lastState;
    }
}
