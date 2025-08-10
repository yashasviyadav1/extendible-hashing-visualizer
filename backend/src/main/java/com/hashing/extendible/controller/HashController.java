package com.hashing.extendible.controller;

import com.hashing.extendible.dto.HashStateDTO;
import com.hashing.extendible.dto.InsertRequest;
import com.hashing.extendible.service.HashService;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/hash")
@CrossOrigin(origins = "*")
public class HashController {

    private final HashService hashService;

    private static final Logger logger = Logger.getLogger(HashController.class.getName());

    public HashController(HashService hashService) {
        this.hashService = hashService;
    }

    @PostMapping("/insert")
    public void insert(@RequestBody InsertRequest request){
        logger.info(String.valueOf(request));
        hashService.insert(request.getKey(), request.getValue());
    }

    @GetMapping("/state")
    public HashStateDTO getState() {
        return hashService.getState();
    }

    @GetMapping("/search")
    public String search(@RequestParam Integer key) {
        return hashService.getValue(key);
    }
}
