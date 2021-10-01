package com.jujeol.drink.drink.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ImageFilePath {

    @Column
    private String smallImageFilePath;
    @Column
    private String mediumImageFilePath;
    @Column
    private String largeImageFilePath;

    public static ImageFilePath create(String smallImageFilePath,
        String mediumImageFilePath,
        String largeImageFilePath
    ) {
        return new ImageFilePath(
            smallImageFilePath, mediumImageFilePath, largeImageFilePath
        );
    }
}
