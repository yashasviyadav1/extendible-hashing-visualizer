package com.hashing.extendible.utils;

/**
 * A generic class representing a key-value pair.
 * This is used to store data within the buckets of the hash table.
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 */
public class Entry<K, V> {
    private final K key;
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "key: " + key +
                ", value: " + value +
                "}";
    }
}
