package com.hashing.extendible.utils;

/**
 * Utility class for hashing operations.
 * This class provides static methods to compute well-distributed hash codes for different types of keys.
 * By declaring all functions as static, they belong to the class itself, not an object instance.
 * This allows them to be called like {@code HashUtils.hashCode(key)} instead of {@code new HashUtils().hashCode(key)},
 * which is faster and more memory-efficient.
 */
class HashUtils {

    /**
     * A prime constant used in the polynomial rolling hash function for strings to ensure better hash distribution.
     */
    private static final int PRIME_CONSTANT = 31;

    /**
     * Hashes a String using a polynomial rolling hash function.
     * The final hash is XORed with a right-shifted version of itself to ensure that the
     * most significant bits (MSB) are not lost when the index is calculated using a bitwise AND with a mask.
     * This improves the distribution of hash values.
     *
     * @param key The string to be hashed.
     * @return The calculated 32-bit hash code.
     */
    private static int hashString(String key) {
        // polynomial rolling hash function for strings
        if (key == null) {
            return 0;
        }
        int hash = 0;
        for (char ch : key.toCharArray()) {
            hash = (hash * PRIME_CONSTANT) + (int) ch;
        }
        hash ^= (hash >>> 16); // Mix in the higher bits, XOR with right shifted value to reduce collisions (so that MSB bits are never lost), because & with mask for finding index will only consider the dth bits
        return hash;
    }

    /**
     * Hashes an integer. It performs an XOR operation with a right-shifted version of itself
     * to improve the distribution of the hash code, especially for keys that have patterns.
     *
     * @param key The integer to be hashed.
     * @return The calculated 32-bit hash code.
     */
    private static int hashInteger(int key) {
        return key ^ (key >>> 16); // XOR with right shifted value to reduce collisions
    }

    /**
     * Provides a fallback hash mechanism for object types that are not explicitly handled.
     * It defaults to using Java's built-in {@code hashCode()} method.
     *
     * @param key The object to be hashed.
     * @return The object's default hash code, or 0 if the key is null.
     */
    private static int hashFallback(Object key) {
        return key == null ? 0 : key.hashCode();
    }

    /**
     * since the function overloading is a compile-time feature, and out key is a generic type K, so at compile time
     * but since func overloading takes place at compile time, the compiler cannot determine which function to call for
     * type K, so we need to provide a single dispatcher method that can handle all types of keys.
     * @param key
     * @return
     */
    public static int hashCode(Object key) {
        if (key instanceof String) {
            return hashString((String) key);
        } else if (key instanceof Integer) {
            return hashInteger((Integer) key);
        } else {
            return hashFallback(key);
        }
    }
}
