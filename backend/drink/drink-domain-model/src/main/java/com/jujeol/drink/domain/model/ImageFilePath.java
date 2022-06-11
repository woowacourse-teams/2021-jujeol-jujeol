package com.jujeol.drink.domain.model;

import lombok.Getter;

@Getter
public class ImageFilePath {
    private String smallImageFilePath;
    private String mediumImageFilePath;
    private String largeImageFilePath;

    private ImageFilePath(String smallImageFilePath, String mediumImageFilePath, String largeImageFilePath) {
        this.smallImageFilePath = smallImageFilePath;
        this.mediumImageFilePath = mediumImageFilePath;
        this.largeImageFilePath = largeImageFilePath;
    }

    public static ImageFilePath create(String smallImageFilePath, String mediumImageFilePath, String largeImageFilePath) {
        return new ImageFilePath(smallImageFilePath, mediumImageFilePath, largeImageFilePath);
    }
}
