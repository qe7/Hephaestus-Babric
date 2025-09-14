package io.github.qe7.core.manager;

import java.util.HashMap;

public abstract class AbstractManager<T, V> {

    protected final HashMap<T, V> map = new HashMap<>();

    public void add(T key, V value) {
        map.put(key, value);
    }

    public V get(T key) {
        return map.get(key);
    }

    public void remove(T key) {
        map.remove(key);
    }

    public void clear() {
        map.clear();
    }

    public boolean contains(T key) {
        return map.containsKey(key);
    }

    public int size() {
        return map.size();
    }
}
