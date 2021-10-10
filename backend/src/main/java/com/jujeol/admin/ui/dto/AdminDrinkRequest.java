package com.jujeol.admin.ui.dto;

import com.jujeol.drink.drink.application.dto.DrinkRequestDto;
import com.jujeol.drink.drink.application.dto.ImageFilePathDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDrinkRequest {

    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private String categoryKey;
    private String description;

    public DrinkRequestDto toDto(ImageFilePathDto imageFilePathDto) {
        return DrinkRequestDto
            .create(name,
                englishName,
                alcoholByVolume,
                imageFilePathDto.getSmallImageFilePath(),
                imageFilePathDto.getMediumImageFilePath(),
                imageFilePathDto.getLargeImageFilePath(),
                categoryKey,
                description);
    }
}
