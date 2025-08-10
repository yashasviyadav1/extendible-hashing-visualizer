# Extendible Hashing
- Documentation for [<u><b>'Dictionary' Abstract Data Structure</b></u>](https://github.com/yashasviyadav1/dsa-questions/blob/main/Core-Concepts/Dictionaries.md)
- Single File [<u><b>Plain Java Implementation</b></u>](https://github.com/yashasviyadav1/dsa-questions/blob/main/Core-Concepts/ExtendibleHashingImplementation.java)

![image](https://camo.githubusercontent.com/9b83451d6e50b6317ed46f49f1ffc2d6e9c1027c0e6f7aedbeb8450af3f771e0/68747470733a2f2f6d656469612e6765656b73666f726765656b732e6f72672f77702d636f6e74656e742f75706c6f6164732f32303139303830363136303031352f42617369632d5374727563747572652d6f662d457874656e6469626c652d48617368696e672e706e67)
## What is Extendible Hashing?
Extendible hashing is a **dynamic hashing** technique that adjusts its structure as the dataset grows or shrinks, avoiding the performance limitations of **static hashing**.

In static hashing, the size of the hash table is fixed. Once it becomes full, collisions increase, causing degraded performance. Extendible hashing solves this by **expanding and splitting only when necessary**, without a complete table rebuild.

---

## Why Extendible Hashing is Better
- **Dynamic Growth**: Directory size doubles only when needed, keeping space usage efficient.
- **Reduced Collisions**: Bucket splits are localized; only the bucket that overflows is split.
- **Better Distribution**: Keys are distributed based on higher-order bits of their hash value, minimizing clustering.
- **No Full Rehashing**: Unlike static hashing, existing keys remain in place unless their bucket is split.

---

## Core Concepts Used in the Implementation

### 1. **Buckets**
- Each bucket has:
    - A **local depth** (number of bits used from the hash to determine placement in that bucket).
    - A **fixed capacity** of **2 entries** in this implementation.
- A bucket overflows when trying to insert a new key-value pair beyond its capacity.
- Buckets can be **shared** between multiple directory entries if their local depth is smaller than the global depth.

---

### 2. **Directory**
- The directory is an array of references (pointers) to buckets.
- **Global depth** = number of bits from the hash value used to index into the directory.
- When a bucket split increases its local depth beyond the current global depth, the **directory size doubles**.
- Each directory index maps to a bucket based on the **binary representation of the hash value**.

---

### 3. **Hash Utility**
- Generates a hash value from keys.
- Supports **integers** and **strings**:
    - **Integers**: Directly hashed using modulo bit-masking.
    - **Strings**: Converted into a numeric hash by summing character codes with positional multipliers.
- **Mixing Higher Bits**:
    - Instead of relying only on lower bits (which often cause clustering), higher bits of the hash are used when directory depth increases.
    - This ensures that when splitting, keys are evenly redistributed, greatly reducing collisions.

---

### 4. **Collision Resolution**
- Handled by **splitting buckets** rather than linear probing or chaining.
- Higher bits from the hash are used when the directory depth increases, ensuring that newly split buckets receive distinct key ranges.
- This prevents repeated collisions that would occur if only lower bits were used.

---

## ✂ Splitting Strategies in Extendible Hashing

There are **two types of splits**:

### **1. Local Split (No Directory Doubling)**
- Occurs when:
    - The bucket’s **local depth < global depth**.
- Only the overflowing bucket is split into two new buckets.
- The directory entries pointing to the old bucket are split between the two new buckets.
- Local depth of both new buckets = old local depth + 1.

### **2. Global Split (Directory Doubling)**
- Occurs when:
    - The bucket’s **local depth == global depth**.
- Steps:
    1. **Double the directory size** (global depth + 1).
    2. Redistribute existing directory pointers.
    3. Perform a **local split** on the overflowing bucket.

---

##  Approach to Bucket Splitting in This Implementation

When a bucket overflows:
1. Check if local depth == global depth.
    - If yes → **double the directory size** and increment global depth.
2. Create two new buckets with:
    - Local depth = old local depth + 1.
3. Redistribute entries from the old bucket into the two new buckets based on the **new bit position** from the hash.
4. Update directory pointers so each entry points to the correct new bucket.

---


## Bit Operations Used

### 1. Extracting the Directory Index
`index = hashValue & ((1 << globalDepth) - 1);`
- **Purpose**: Selects the lowest `globalDepth` bits of the hash value to determine which directory entry to use.
- **How**:
    - `(1 << globalDepth)` → Creates a bitmask with a `1` followed by `globalDepth` zeros.
    - Subtracting `1` turns it into a mask with `globalDepth` ones.
    - `&` with `hashValue` extracts only those bits.

---

### 2. Determining Bucket Assignment During Split
`bit = (hashValue >> localDepth) & 1;`
- **Purpose**: Checks the next higher-order bit beyond the current `localDepth` to decide if the entry goes to the **new bucket** or stays in the old one.
- **How**:
    - `>> localDepth` shifts the hash value right by `localDepth` bits.
    - `& 1` isolates the lowest bit of the shifted value.

---

### 3. Doubling the Directory
`for (int i = 0; i < oldSize; i++) { newDir[i] = oldDir[i]; newDir[i + oldSize] = oldDir[i]; }`
- **Purpose**: Duplicates the directory pointers when `globalDepth` increases.
- **How**:
    - `oldSize` = current directory length.
    - The first half of `newDir` is a copy of `oldDir`.
    - The second half is also a copy of `oldDir` (pointing to the same buckets initially).

---

## Advantages Over Static Hashing
- Grows gracefully without massive rehashing.
- Minimizes collisions by using more bits when necessary.
- Memory-efficient by sharing buckets across multiple directory entries until a split is needed.