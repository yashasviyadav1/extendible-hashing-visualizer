package com.hashing.extendible.service;

import com.hashing.extendible.dto.BucketDTO;
import com.hashing.extendible.dto.EntryDTO;
import com.hashing.extendible.dto.HashStateDTO;
import com.hashing.extendible.utils.Bucket;
import com.hashing.extendible.utils.ExtendibleHashing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashService {

    private final ExtendibleHashing<Integer, String> table;

    public HashService() {
        this.table = new ExtendibleHashing<>(2);
        // dummy entries for display purposes
        table.insert(4, "New York");
        table.insert(2, "San Francisco");
//        table.insert(7, "Chicago");
//        table.insert(5, "Los Angeles");
        table.insert(3, "Chandigarh, India");
//        table.insert(6, "Calcutta, India");
    }

    public void insert(Integer key, String value) {
        table.insert(key, value);
    }

    public String getValue(Integer key) {
        if(key == null) {
            return "Invalid key";
        }
        String value = table.get(key);
        return value != null ? value : "Invalid Key";
    }

    public HashStateDTO getState() {
        List<BucketDTO> directory = new ArrayList<>(); // this will hold the directory of buckets to send to frontend
        for (Bucket<Integer, String> eachBucket : table.getDirectory()) {
            List<EntryDTO> entries = eachBucket.getEntries().stream()
                    .map(e -> new EntryDTO(
                            String.valueOf(e.getKey()),
                            String.valueOf(e.getValue())
                    ))
                    .collect(Collectors.toList());
            directory.add(new BucketDTO(eachBucket.getLocalDepth(), entries));
        }
        return new HashStateDTO(table.getGlobalDepth(), directory);
    }

}
