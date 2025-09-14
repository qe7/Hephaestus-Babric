package io.github.qe7.core.bus;

import io.github.qe7.core.manager.AbstractManager;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Taken from my past like 15 java related projects.
 * Standard use, "publishEvent" and "registerHandler".
 */
public final class EventManager extends AbstractManager<Class<? extends Handler>, Handler> {

    private final Map<Class<? extends Event>, CopyOnWriteArrayList<ListenerWrapper>> listenerCache = new ConcurrentHashMap<>();
    private final Map<Handler, CopyOnWriteArrayList<ListenerWrapper>> handlerListeners = new ConcurrentHashMap<>();
    private final Map<Class<?>, List<ListenerWrapper>> handlerRegistrationCache = new ConcurrentHashMap<>();

    private static final Comparator<ListenerWrapper> PRIORITY_COMPARATOR = Comparator.comparingInt((ListenerWrapper wrapper) -> wrapper.priority.getValue()).reversed();

    /**
     * Publishes the given event to all registered listeners for its type.
     * Listeners are invoked in order of their priority.
     *
     * @param event the event instance to publish
     * @param <T>   the type of the event
     */
    public <T extends Event> void publishEvent(@NotNull T event) {
        Class<?> eventClass = event.getClass();
        CopyOnWriteArrayList<ListenerWrapper> listeners = listenerCache.get(eventClass);
        if (listeners != null) {
            List<ListenerWrapper> sortedListeners = new CopyOnWriteArrayList<>(listeners);
            sortedListeners.sort(PRIORITY_COMPARATOR);

            for (ListenerWrapper wrapper : sortedListeners) {
                if (wrapper.handler.listening()) {
                    @SuppressWarnings("unchecked") Listener<T> listener = (Listener<T>) wrapper.listener;
                    listener.call(event);
                }
            }
        }
    }

    /**
     * Registers all event listener fields in the given handler.
     * Discovers fields annotated with {@code SubscribeEvent}, creates listener wrappers,
     * and adds them to the listener caches. Uses cached registration if available.
     *
     * @param handler the handler instance to register listeners from
     */
    public void registerHandler(@NotNull Handler handler) {
        Class<?> handlerClass = handler.getClass();
        List<ListenerWrapper> cached = handlerRegistrationCache.get(handlerClass);
        if (cached != null) {
            for (ListenerWrapper wrapper : cached) {
                addListenerToCache(wrapper);
            }
            return;
        }

        CopyOnWriteArrayList<ListenerWrapper> discovered = new CopyOnWriteArrayList<>();
        for (java.lang.reflect.Field field : handlerClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(SubscribeEvent.class)) continue;

            SubscribeEvent annotation = field.getAnnotation(SubscribeEvent.class);
            EventPriority priority = annotation.value();

            field.setAccessible(true);
            try {
                Listener<? extends Event> listener = (Listener<? extends Event>) field.get(handler);
                ParameterizedType type = (ParameterizedType) field.getGenericType();
                @SuppressWarnings("unchecked") Class<? extends Event> eventClass = (Class<? extends Event>) type.getActualTypeArguments()[0];

                ListenerWrapper wrapper = new ListenerWrapper(handler, listener, eventClass, priority);
                discovered.add(wrapper);

                addListenerToCache(wrapper);
                handlerListeners.computeIfAbsent(handler, k -> new CopyOnWriteArrayList<>()).add(wrapper);

            } catch (Exception e) {
                throw new RuntimeException("Failed to register listener " + field.getName(), e);
            }
        }
        if (!discovered.isEmpty()) {
            handlerRegistrationCache.put(handlerClass, discovered);
        }
    }

    private void addListenerToCache(@NotNull ListenerWrapper wrapper) {
        CopyOnWriteArrayList<ListenerWrapper> listeners = listenerCache.computeIfAbsent(wrapper.eventClass, k -> new CopyOnWriteArrayList<>());
        listeners.add(wrapper);
        listeners.sort(PRIORITY_COMPARATOR);
    }

    private static class ListenerWrapper {
        public final Handler handler;
        public final Listener<? extends Event> listener;
        public final Class<? extends Event> eventClass;
        public final EventPriority priority;

        public ListenerWrapper(Handler handler, Listener<? extends Event> listener, Class<? extends Event> eventClass, EventPriority priority) {
            this.handler = handler;
            this.listener = listener;
            this.eventClass = eventClass;
            this.priority = priority;
        }
    }
}