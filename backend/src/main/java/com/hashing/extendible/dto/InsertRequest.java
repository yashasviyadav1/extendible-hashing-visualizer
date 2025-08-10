package com.hashing.extendible.dto;

public class InsertRequest {
    private Integer key;
    private String value;

    public InsertRequest() {
    }

    public InsertRequest(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}