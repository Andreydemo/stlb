package com.demosoft.stlb.client.collection;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Andrii Korkoshko on 07.02.2016.
 */
public class SimpleMap<K, V> implements Map<K, V>, Serializable {

    private Object vals[][] = new Object[5][];
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (vals != null) {
            for (int i = 0; i < size; i++) {
                if (vals[i][1].equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (vals != null) {
            for (int i = 0; i < size; i++) {
                if (vals[i][0].equals(key)) {
                    return (V) vals[i][1];
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (size + 1 >= vals.length) {
            defineMemory();
        }
        if (!isEmpty()) {
            for (int i = 0; i < size; i++) {
                if (vals[i][0].equals(key)) {
                    Object old = vals[i][1];
                    vals[i][1] = value;
                    return (V) old;
                }
            }
        }

        vals[size] = new Object[2];
        vals[size][0] = key;
        vals[size][1] = value;
        size++;
        return null;
    }

    private void defineMemory() {
        Object[][] newVals = new Object[(int) (size * 1.5)][];
        System.arraycopy(vals, 0, newVals, 0,
                size);
        vals = newVals;
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> kvEntry : m.entrySet()) {
            this.put(kvEntry.getKey(), kvEntry.getValue());
        }
    }

    @Override
    public void clear() {
        vals = new Object[5][];
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                set.add(new AbstractMap.SimpleEntry<K, V>((K) vals[i][0], (V) vals[i][1]));
            }
        }
        return set;
    }
}
