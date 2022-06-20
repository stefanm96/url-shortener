package com.stefanmichel.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShortenedUrlDto {
    String originalUrl;
    String shortenedUrl;
}
