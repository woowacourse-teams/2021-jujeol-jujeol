package com.jujeol.drink.drink.domain;

import java.util.Arrays;
import java.util.List;

public enum ImageSize {
    SMALL(200, "w200"),
    MEDIUM(400, "w400"),
    LARGE(600, "w600");

    ImageSize(int pixelSize, String additionalWord) {
        this.pixelSize = pixelSize;
        this.additionalWord = additionalWord;
    }

    private final int pixelSize;
    private final String additionalWord;

    public static List<ImageSize> list() {
        return Arrays.asList(ImageSize.values());
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public String getFileNameSuffix() {
        return additionalWord;
    }

    public String getFilePath() {
        return additionalWord + "/";
    }
}
