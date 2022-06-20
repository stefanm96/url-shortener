package com.stefanmichel.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AnalyticsDto {
    String originalUrl;
    String shortenedUrl;
    Long counter;
}
