package com.hashing.extendible.dto;

import java.util.List;

public class HashStateDTO {
    private final int globalDepth;
    private final List<BucketDTO> directory;

    public HashStateDTO(int globalDepth, List<BucketDTO> directory) {
        this.globalDepth = globalDepth;
        this.directory = directory;
    }
    public int getGlobalDepth() { return globalDepth; }
    public List<BucketDTO> getDirectory() { return directory; }
}

