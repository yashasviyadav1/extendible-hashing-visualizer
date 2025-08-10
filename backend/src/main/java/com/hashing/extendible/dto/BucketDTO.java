package com.hashing.extendible.dto;

import java.util.List;

public class BucketDTO {
    private final int localDepth;
    private final List<EntryDTO> entries;

    public BucketDTO(int localDepth, List<EntryDTO> entries) {
        this.localDepth = localDepth;
        this.entries = entries;
    }
    public int getLocalDepth() { return localDepth; }
    public List<EntryDTO> getEntries() { return entries; }
}
