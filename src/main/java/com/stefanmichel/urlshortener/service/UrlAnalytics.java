package com.stefanmichel.urlshortener.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UrlAnalytics {
    String originalUrl;
    String shortenedUrl;
    Long counter;
}
