package com.jujeol.drink.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ImageUrl {
    @Column
    private String imageUrl;

    public ImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
