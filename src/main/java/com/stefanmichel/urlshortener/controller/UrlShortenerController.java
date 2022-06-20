package com.stefanmichel.urlshortener.controller;

import com.stefanmichel.urlshortener.dto.AnalyticsDto;
import com.stefanmichel.urlshortener.dto.AnalyticsRequestDto;
import com.stefanmichel.urlshortener.dto.ShortUrlRequestDto;
import com.stefanmichel.urlshortener.dto.ShortenedUrlDto;
import com.stefanmichel.urlshortener.exception.CustomHashUnavailableException;
import com.stefanmichel.urlshortener.exception.HashNotFoundException;
import com.stefanmichel.urlshortener.service.UrlAnalytics;
import com.stefanmichel.urlshortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.net.URL;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @RequestMapping(value = "/short", method = POST)
    public ResponseEntity<?> shortUrl(@Valid @RequestBody ShortUrlRequestDto shortUrlRequestDto) {
        try {
            String shortUrl = urlShortenerService.shortUrl(shortUrlRequestDto.getOriginalUrl(), shortUrlRequestDto.getCustomHash());

            ShortenedUrlDto shortenedUrlDto = new ShortenedUrlDto(shortUrlRequestDto.getOriginalUrl(), shortUrl);
            return new ResponseEntity<>(shortenedUrlDto, HttpStatus.ACCEPTED);
        } catch (CustomHashUnavailableException e) {
            return ResponseEntity
                .badRequest()
                .body(e.getMessage());
        }
    }

    @RequestMapping(value = "/{hash}", method = GET)
    public void redirect(@PathVariable String hash, HttpServletResponse httpServletResponse) {
        String originalUrl = urlShortenerService.originalUrl(hash);

        httpServletResponse.setHeader("Location", originalUrl);
        httpServletResponse.setStatus(302);
    }

    @RequestMapping(value = "/analytics", method = POST)
    public ResponseEntity<?> analytics(@Valid @RequestBody AnalyticsRequestDto analyticsRequestDto) {
        String hash = getHash(analyticsRequestDto.getUrl());

        try {
            UrlAnalytics urlAnalytics = urlShortenerService.analytics(hash);

            AnalyticsDto analyticsDto = new AnalyticsDto(urlAnalytics.getOriginalUrl(), urlAnalytics.getShortenedUrl(), urlAnalytics.getCounter());
            return ResponseEntity.ok()
                .body(analyticsDto);
        } catch (HashNotFoundException e) {
            return ResponseEntity.status(404).body("URL not found!");
        }
    }

    private String getHash(String url) {
        return StringUtils.substringAfterLast(StringUtils.removeEnd(url, "/"), "/");
    }
}
