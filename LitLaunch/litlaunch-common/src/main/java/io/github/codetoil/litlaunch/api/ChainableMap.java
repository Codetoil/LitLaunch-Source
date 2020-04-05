package io.github.codetoil.litlaunch.api;

import java.util.HashMap;

public class ChainableMap<K, V> extends HashMap<K, V> {
    public static <K, V> ChainableMap<K, V> newMap() {
        return new ChainableMap<K, V>();
    }

    public ChainableMap<K, V> putChain(K key, V value) {
        this.put(key, value);
        return this;
    }

    public ChainableMap<K, V> cloneMap() {
        ChainableMap<K, V> map = ((ChainableMap<K, V>) super.clone());
        map.putAll(this);
        return map;
    }
}
