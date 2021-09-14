package com.jujeol.admin.ui.dto;

import com.jujeol.drink.drink.application.dto.DrinkRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDrinkRequest {

    private String name;
    private String englishName;
    private Double alcoholByVolume;
    private MultipartFile image;
    private String categoryKey;
    private String description;

    //todo
    public DrinkRequestDto toDto() {
        return DrinkRequestDto
                .create(name, englishName, alcoholByVolume, "", categoryKey, description);
    }
}
