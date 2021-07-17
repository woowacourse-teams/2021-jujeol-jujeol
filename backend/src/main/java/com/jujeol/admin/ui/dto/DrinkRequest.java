package com.jujeol.admin.ui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DrinkRequest {

    private String name;
    private String englishName;
    private String imageUrl;
    private CategoryRequest category;
    private Double alcoholByVolume;
}
