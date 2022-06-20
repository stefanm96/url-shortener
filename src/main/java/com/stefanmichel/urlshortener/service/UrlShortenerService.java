package com.stefanmichel.urlshortener.service;

import com.stefanmichel.urlshortener.entity.ShortUrlEntity;
import com.stefanmichel.urlshortener.exception.CustomHashUnavailableException;
import com.stefanmichel.urlshortener.exception.HashNotFoundException;
import com.stefanmichel.urlshortener.generator.HashGenerator;
import com.stefanmichel.urlshortener.repository.UrlShortenerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    @Value("${url-shortener.url.base}")
    private String urlBase;

    private final UrlShortenerRepository urlShortenerRepository;
    private final HashGenerator hashGenerator;

    public String shortUrl(@NonNull String originalUrl, @Nullable String customHash) {
        String hash = getHash(customHash);

        urlShortenerRepository.save(new ShortUrlEntity(hash, originalUrl));

        return getShortUrl(hash);
    }


    public String originalUrl(String hash) {
        ShortUrlEntity shortUrlEntity = getShortUrlEntity(hash);
        shortUrlEntity.incrementCounter();
        urlShortenerRepository.save(shortUrlEntity);

        return shortUrlEntity.getUrl();
    }

    public UrlAnalytics analytics(String hash) {
        ShortUrlEntity shortUrlEntity = getShortUrlEntity(hash);
        String shortUrl = getShortUrl(shortUrlEntity.getHash());

        return new UrlAnalytics(shortUrlEntity.getUrl(), shortUrl, shortUrlEntity.getCounter());
    }

    private ShortUrlEntity getShortUrlEntity(String hash) {
        return urlShortenerRepository.findById(hash).orElseThrow(HashNotFoundException::new);
    }

    private String getHash(String customHash) {
        if (isNotBlank(customHash)) {
            if (isHashAvailable(customHash)) {
                return customHash;
            }
            throw new CustomHashUnavailableException();
        }

        return hashGenerator.generateHash();
    }

    private boolean isHashAvailable(String customHash) {
        return !urlShortenerRepository.existsById(customHash);
    }

    private String getShortUrl(String hash) {
        return urlBase + hash;
    }
}
