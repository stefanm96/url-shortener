package com.stefanmichel.urlshortener.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class AnalyticsRequestDto {

    @NotEmpty
    @URL
    String url;
}
