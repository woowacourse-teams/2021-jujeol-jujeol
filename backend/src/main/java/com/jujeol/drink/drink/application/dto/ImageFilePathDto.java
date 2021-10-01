package com.jujeol.drink.drink.application.dto;

import com.jujeol.drink.drink.domain.ImageFilePath;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageFilePathDto {

    private String smallImageFilePath;
    private String mediumImageFilePath;
    private String largeImageFilePath;

    public static ImageFilePathDto create(
        String smallImageFilePath,
        String mediumImageFilePath,
        String largeImageFilePath
    ) {
        return new ImageFilePathDto(smallImageFilePath, mediumImageFilePath, largeImageFilePath);
    }

    public ImageFilePath toEntity() {
        return ImageFilePath
            .create(smallImageFilePath, mediumImageFilePath, largeImageFilePath);
    }
}
