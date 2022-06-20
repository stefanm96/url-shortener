package com.stefanmichel.urlshortener.service;

import com.stefanmichel.urlshortener.entity.ShortUrlEntity;
import com.stefanmichel.urlshortener.exception.CustomHashUnavailableException;
import com.stefanmichel.urlshortener.generator.HashGenerator;
import com.stefanmichel.urlshortener.repository.UrlShortenerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UrlShortenerServiceTest {

    private UrlShortenerRepository urlShortenerRepositoryMock;
    private HashGenerator hashGeneratorMock;
    private UrlShortenerService urlShortenerService;

    @BeforeEach
    void setUp() {
        urlShortenerRepositoryMock = mock(UrlShortenerRepository.class);
        hashGeneratorMock = mock(HashGenerator.class);
        urlShortenerService = new UrlShortenerService(urlShortenerRepositoryMock, hashGeneratorMock);
    }

    @Test
    void shouldUseCustomHash() {
        String url = "https://www.abc.com/def";
        String expectedHash = "hash";

        when(urlShortenerRepositoryMock.existsById(expectedHash))
            .thenReturn(false);

        urlShortenerService.shortUrl(url, expectedHash);

        ArgumentCaptor<ShortUrlEntity> argumentCaptor = ArgumentCaptor.forClass(ShortUrlEntity.class);
        verify(urlShortenerRepositoryMock).save(argumentCaptor.capture());
        verify(urlShortenerRepositoryMock).existsById(expectedHash);

        ShortUrlEntity shortUrlEntity = argumentCaptor.getValue();

        assertEquals(expectedHash, shortUrlEntity.getHash());
        assertEquals(url, shortUrlEntity.getUrl());
    }

    @Test
    void shouldNotUseCustomHash() {
        String url = "https://www.abc.com/def";
        String expectedHash = "a";

        when(urlShortenerRepositoryMock.existsById(expectedHash))
            .thenReturn(true);

        assertThrows(CustomHashUnavailableException.class, () -> urlShortenerService.shortUrl(url, expectedHash));

        verify(urlShortenerRepositoryMock).existsById(expectedHash);
    }

    @Test
    void shouldUseGeneratedHash() {
        String url = "https://www.abc.com/def";
        String expectedHash = "Hash1";

        when(hashGeneratorMock.generateHash())
            .thenReturn(expectedHash);

        urlShortenerService.shortUrl(url, null);

        ArgumentCaptor<ShortUrlEntity> argumentCaptor = ArgumentCaptor.forClass(ShortUrlEntity.class);
        verify(urlShortenerRepositoryMock).save(argumentCaptor.capture());

        ShortUrlEntity shortUrlEntity = argumentCaptor.getValue();

        assertEquals(expectedHash, shortUrlEntity.getHash());
        assertEquals(url, shortUrlEntity.getUrl());
    }
}