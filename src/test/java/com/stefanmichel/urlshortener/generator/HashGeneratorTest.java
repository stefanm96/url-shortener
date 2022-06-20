package com.stefanmichel.urlshortener.generator;

import com.stefanmichel.urlshortener.repository.UrlShortenerRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HashGeneratorTest {

    private UrlShortenerRepository urlShortenerRepositoryMock;
    private HashGenerator hashGenerator;

    @BeforeEach
    void setUp() {
        urlShortenerRepositoryMock = mock(UrlShortenerRepository.class);
        hashGenerator = new HashGenerator(urlShortenerRepositoryMock);
    }

    @Test
    void shouldGenerateHash() {
        when(urlShortenerRepositoryMock.existsById(anyString()))
            .thenReturn(false);

        String hash = hashGenerator.generateHash();

        assertTrue(isNotBlank(hash));
    }

    @Test
    void shouldThrowExceptionWhenMaxedOutCollision() {
        when(urlShortenerRepositoryMock.existsById(anyString()))
            .thenReturn(true);

        assertThrows(RuntimeException.class, () -> hashGenerator.generateHash());
    }
}