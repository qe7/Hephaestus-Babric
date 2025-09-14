package io.github.qe7.core.helper;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class HelperFactory {

    private static final Map<Class<?>, AbstractHelper> HELPERS = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T extends AbstractHelper> @NotNull T get(Class<T> clazz) {
        T manager = (T) HELPERS.get(clazz);
        if (manager == null) {
            throw new IllegalStateException("Helper not registered: " + clazz.getSimpleName());
        }
        return manager;
    }

    public static <T extends AbstractHelper> void register(Class<T> clazz, T manager) {
        if (HELPERS.containsKey(clazz)) {
            throw new IllegalStateException("Helper already registered: " + clazz.getSimpleName());
        }
        HELPERS.put(clazz, manager);
    }

    public static boolean isRegistered(Class<?> clazz) {
        return HELPERS.containsKey(clazz);
    }
}
