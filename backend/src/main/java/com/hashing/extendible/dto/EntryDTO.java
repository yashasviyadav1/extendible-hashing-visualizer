package com.hashing.extendible.dto;

public class EntryDTO {
    private final String key;
    private final String value;

    public EntryDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() { return key; }
    public String getValue() { return value; }
}
