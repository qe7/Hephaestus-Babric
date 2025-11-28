package io.github.qe7.core.feature.hudelement;

import io.github.qe7.core.bus.EventManager;
import io.github.qe7.core.bus.Handler;
import io.github.qe7.core.feature.settings.AbstractSetting;
import io.github.qe7.core.manager.AbstractManager;
import io.github.qe7.core.manager.ManagerFactory;

import java.lang.reflect.Field;
import java.util.*;

public final class HUDElementManager extends AbstractManager<Class<? extends AbstractHUDElement>, AbstractHUDElement> implements Handler {

    private final Map<AbstractHUDElement, List<AbstractSetting<?>>> settings = new HashMap<>();

    public HUDElementManager() {
        List<AbstractHUDElement> elementList = new ArrayList<>();

        this.registerAbstractHUDElement(elementList);

        System.out.println("Loaded " + this.size() + " elements.");
        ManagerFactory.get(EventManager.class).registerHandler(this);
    }

    private void registerAbstractHUDElement(List<AbstractHUDElement> abstractModuleList) {
        for (AbstractHUDElement element : abstractModuleList) {
            try {
                add(element.getClass(), element);
                ManagerFactory.get(EventManager.class).registerHandler(element);

                for (Field declaredField : element.getClass().getDeclaredFields()) {
                    if (declaredField.getType().getSuperclass() != null && declaredField.getType().getSuperclass().equals(AbstractSetting.class)) {
                        declaredField.setAccessible(true);

                        try {
                            this.registerAbstractSetting(element, (AbstractSetting<?>) declaredField.get(element));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }

    public List<AbstractHUDElement> getElementsAsList() {
        return new ArrayList<>(this.map.values());
    }

    public void registerAbstractSetting(final AbstractHUDElement element, final AbstractSetting<?> setting) {
        settings.putIfAbsent(element, new ArrayList<>());
        settings.get(element).add(setting);
    }

    public List<AbstractSetting<?>> getSettingsByElement(AbstractHUDElement element) {
        return settings.getOrDefault(element, Collections.emptyList());
    }
}
