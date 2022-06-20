package com.stefanmichel.urlshortener.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShortUrlEntity {

    @Id
    private String hash;

    @Column
    private String url;

    @Column
    private Long counter = 0L;

    public ShortUrlEntity(String hash, String url) {
        this.hash = hash;
        this.url = url;
    }

    public Long incrementCounter() {
        return counter += 1;
    }
}
