package com.hashing.extendible.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a data bucket in the extendible hash table.
 * Each bucket has a fixed capacity and a local depth.
 *
 * @param <K> The type of the keys stored in this bucket.
 * @param <V> The type of the values stored in this bucket.
 */
public class Bucket<K, V> {
    private int localDepth;
    private final int capacity;
    private final List<Entry<K, V>> entries;

    public Bucket(int localDepth, int capacity) {
        this.localDepth = localDepth;
        this.capacity = capacity;
        this.entries = new ArrayList<>(capacity);
    }

    public List<Entry<K, V>> getEntries() {
        return entries;
    }

    public Optional<V> get(K key) {
        for (Entry<K, V> entry : entries) {
            if (entry.getKey().equals(key)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    public boolean insert(K key, V value) {
        if (entries.size() < capacity) {
            entries.add(new Entry<>(key, value));
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return entries.size() >= capacity;
    }

    public boolean isNotFull() {
        return !isFull();
    }

    public void incrementLocalDepth() {
        localDepth++;
    }

    public int getLocalDepth() {
        return localDepth;
    }

    @Override
    public String toString() {
        return "Bucket--[" +
                "localDepth=" + localDepth +
                ", entries=" + entries +
                "]";
    }
}
