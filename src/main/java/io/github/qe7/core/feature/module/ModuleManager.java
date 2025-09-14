package io.github.qe7.core.feature.module;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.bus.Handler;
import io.github.qe7.core.bus.Listener;
import io.github.qe7.core.bus.SubscribeEvent;
import io.github.qe7.core.feature.module.settings.AbstractSetting;
import io.github.qe7.core.manager.AbstractManager;
import io.github.qe7.core.manager.ManagerFactory;
import io.github.qe7.events.KeyPressEvent;
import io.github.qe7.features.modules.render.ClickGUIModule;
import io.github.qe7.features.modules.render.HUDModule;

import java.lang.reflect.Field;
import java.util.*;

public final class ModuleManager extends AbstractManager<Class<? extends AbstractModule>, AbstractModule> implements Handler {

    private final Map<AbstractModule, List<AbstractSetting<?>>> settings = new HashMap<>();

    public ModuleManager() {
        List<AbstractModule> moduleList = new ArrayList<>();

        moduleList.add(new ClickGUIModule());
        moduleList.add(new HUDModule());

        this.registerAbstractModule(moduleList);

        System.out.println("Loaded " + this.size() + " modules.");
        ManagerFactory.get(EventManager.class).registerHandler(this);
    }

    private void registerAbstractModule(List<AbstractModule> abstractModuleList) {
        for (AbstractModule module : abstractModuleList) {
            try {
                add(module.getClass(), module);
                ManagerFactory.get(EventManager.class).registerHandler(module);

                for (Field declaredField : module.getClass().getDeclaredFields()) {
                    if (declaredField.getType().getSuperclass() != null && declaredField.getType().getSuperclass().equals(AbstractSetting.class)) {
                        declaredField.setAccessible(true);

                        try {
                            this.registerAbstractSetting(module, (AbstractSetting<?>) declaredField.get(module));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    public List<AbstractModule> getModulesByCategory(ModuleCategory category) {
        List<AbstractModule> modules = new ArrayList<>();
        for (AbstractModule module : this.map.values()) {
            if (module.getCategory() == category) {
                modules.add(module);
            }
        }
        return modules;
    }

    public List<AbstractModule> getEnabledModules() {
        List<AbstractModule> enabledModules = new ArrayList<>();
        for (AbstractModule module : this.map.values()) {
            if (module.isEnabled()) {
                enabledModules.add(module);
            }
        }
        return enabledModules;
    }

    public void registerAbstractSetting(final AbstractModule module, final AbstractSetting<?> setting) {
        settings.putIfAbsent(module, new ArrayList<>());
        settings.get(module).add(setting);
    }

    public List<AbstractSetting<?>> getSettingsByModule(AbstractModule module) {
        return settings.getOrDefault(module, Collections.emptyList());
    }

    @SubscribeEvent
    private final Listener<KeyPressEvent> keyPressEventListener = event -> {
        for (AbstractModule module : this.map.values()) {
            if (module.getKeyBind() == event.getKeyCode()) {
                module.toggle();
            }
        }
    };
}
