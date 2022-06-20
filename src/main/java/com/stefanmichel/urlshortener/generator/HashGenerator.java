package com.stefanmichel.urlshortener.generator;

import com.stefanmichel.urlshortener.repository.UrlShortenerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class HashGenerator {

    private static final int HASH_SIZE = 6;
    private static final int MAX_COLLISIONS = 6;

    private final UrlShortenerRepository urlShortenerRepository;

    public String generateHash() {
        for (int i = 0; i < MAX_COLLISIONS; i++) {
            String hashCandidate = RandomStringUtils.randomAlphanumeric(HASH_SIZE);

            if (!urlShortenerRepository.existsById(hashCandidate)) {
                return hashCandidate;
            }
        }
        throw new RuntimeException("Max Collisions reached while hashing.");
    }
}
