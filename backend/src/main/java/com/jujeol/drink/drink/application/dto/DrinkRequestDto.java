package com.jujeol.drink.drink.application.dto;

import com.jujeol.drink.category.domain.Category;
import com.jujeol.drink.drink.domain.Drink;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DrinkRequestDto {

    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private MultipartFile image;
    private String categoryKey;
    private String description;

    public static DrinkRequestDto create(
            String name, String englishName, Double alcoholByVolume,
            MultipartFile image, String categoryKey, String description
    ) {
        return new DrinkRequestDto(
                name,
                englishName,
                alcoholByVolume,
                image,
                categoryKey,
                description
        );
    }

    public Drink toEntity(Category category, String imagePath) {
        return Drink.create(
                name,
                englishName,
                alcoholByVolume,
                imagePath,
                0.0,
                category,
                description
        );
    }
}
