package com.hashing.extendible.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Extendible Hashing implementation.
 * This class provides a way to store key-value pairs in a hash table that can grow dynamically.
 * Comprises of a 'directory' that maps keys(index) to buckets.
 * Each bucket can hold a limited number of entries, defined by the bucket capacity.
 * Its part of 'Dynamic Hashing' as compared to Open Addressing or Chaining (which are static).
 * When a bucket becomes full, it splits and the directory is updated accordingly.
 * There are 2 main types of splitting:
 * 1. Hard Split: When the bucket is full and its local depth is equal to the global depth, the directory size is doubled.
 * 2. Soft Split: When the bucket is full but its local depth is less than the global Depth, the bucket is split and the directory is updated to point to the new bucket.
 *
 * @param <K> Type of keys
 * @param <V> Type of values
 */

public class ExtendibleHashing<K, V> {

    private final List<Bucket<K, V>> directory;
    private int globalDepth;
    private final int bucketCapacity;

    public ExtendibleHashing(int bucketCapacity) {
        this.globalDepth = 1;
        this.bucketCapacity = bucketCapacity;
        this.directory = new ArrayList<>((int) Math.pow(2, globalDepth));
        this.directory.add(new Bucket<>(globalDepth, bucketCapacity));
        this.directory.add(new Bucket<>(globalDepth, bucketCapacity));
    }


    /**
     * Calculates the index of the bucket in the directory for a given key.
     * The index is determined by the least significant 'd' bits of the key's hash code,
     * where 'd' is the current global depth. This is achieved efficiently using a bitwise AND
     * with a mask, ensuring the index is always within the directory's bounds.
     *
     * @param key The key whose bucket index is to be found.
     * @return The index in the directory.
     */
    private int getBucketIndex(K key) {
        int hash = HashUtils.hashCode(key);
        int mask = (1 << globalDepth) - 1; // these 2 steps are equivalent to doing (hash % 2^globalDepth) or hash % (size of directory) that we usually do in hashing to find index, bitwise & is more efficient then modulus operation
        return hash & mask;
    }

    public V get(K key) {
        int index = getBucketIndex(key);
        Optional<V> value = directory.get(index).get(key);
        return value.orElse(null);
    }

    /**
     * Inserts a key-value pair into the hash table.
     * If the target bucket is full, it triggers a split operation.
     *
     * @param key   The key to insert.
     * @param value The value to associate with the key.
     * @return {@code true} upon successful insertion.
     */
    public boolean insert(K key, V value) {
        int index = getBucketIndex(key);
        Bucket<K, V> bucket = directory.get(index);
        if (bucket.isNotFull()) {
            return bucket.insert(key, value);
        } else if (bucket.getLocalDepth() == globalDepth) {
            doubleDirectorySize();
            splitBucket(index, bucket);
            return insert(key, value); // recursive call to try to insert after splitting
        } else {
            splitBucket(index, bucket);
            return insert(key, value);
        }
    }

    /**
     * Doubles the size of the directory when a bucket splits and its local depth equals the global depth.
     * It increments the global depth and duplicates the existing pointers to fill the new half of the directory.
     */
    public void doubleDirectorySize() {
        int currentSize = directory.size();
        globalDepth++;
        for (int i = 0; i < currentSize; i++) { // duplicate existing buckets, to double the size
            directory.add(directory.get(i));
        }
    }

    /**
     * Splits an overflowed bucket and redistributes its entries.
     * It creates a new bucket and updates the directory pointers to correctly map to the old and new buckets.
     *
     * @param indexOfBucketToSplit The directory index that led to the bucket to be split.
     * @param bucketToSplit        The bucket that is full and needs to be split.
     */
    public void splitBucket(int indexOfBucketToSplit, Bucket<K, V> bucketToSplit) {
        int initialLocalDepth = bucketToSplit.getLocalDepth();
        Bucket<K, V> newBucket = new Bucket<>(initialLocalDepth + 1, bucketCapacity);
        List<Entry<K, V>> oldEntries = new ArrayList<>(bucketToSplit.getEntries());
        bucketToSplit.getEntries().clear();
        bucketToSplit.incrementLocalDepth();

        // Rewiring directory to point to the old bucket (to split) and new bucket
        for (int i = 0; i < directory.size(); i++) {
            if (directory.get(i) == bucketToSplit) { // from this we are clear that we need to split this bucket, but to make sure if old bucket to assign or new is below logic
                if ((i & (1 << initialLocalDepth)) != 0) {
                    directory.set(i, newBucket);
                }
            }
        }

        // re insert the old entries
        for (Entry<K, V> entry : oldEntries) {
            insert(entry.getKey(), entry.getValue());
        }
    }

    public List<Bucket<K, V>> getDirectory() {
        return directory;
    }

    public int getGlobalDepth() {
        return globalDepth;
    }

    public void display() {
        System.out.println("--------Directory--------");
        System.out.println("Global Depth: " + globalDepth);
        for (int i = 0; i < directory.size(); i++) {
            System.out.println("i[" + i + "] -> " + directory.get(i));
        }
        System.out.println("-------------------------");
    }
}
