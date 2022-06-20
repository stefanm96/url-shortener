package com.stefanmichel.urlshortener.repository;

import com.stefanmichel.urlshortener.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepository extends JpaRepository<ShortUrlEntity, String> {
}
