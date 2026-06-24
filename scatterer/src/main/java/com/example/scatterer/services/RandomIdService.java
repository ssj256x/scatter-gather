package com.example.scatterer.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RandomIdService {

    private static final String[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".split("");
    private static final int ID_SIZE = 6;

    private final Random random = new Random();

    public List<String> generateRandomIds(int size) {

        List<String> randomIds = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            randomIds.add(generateRandomId());
        }
        return randomIds;
    }

    public String generateRandomId() {
        StringBuilder randomId = new StringBuilder();
        for (int j = 0; j < ID_SIZE; j++) {
            randomId.append(CHARS[Math.abs(random.nextInt()) % CHARS.length]);
        }
        return randomId.toString();
    }
}
