package com.jujeol.drink.drink.domain;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
class ImageFilePath {

    @Column
    private String smallImageFilePath;
    @Column
    private String mediumImageFilePath;
    @Column
    private String largeImageFilePath;

    public static ImageFilePath create(List<String> imageFilePaths) {
        return new ImageFilePath(imageFilePaths.get(0), imageFilePaths.get(1), imageFilePaths.get(2));
    }
}
