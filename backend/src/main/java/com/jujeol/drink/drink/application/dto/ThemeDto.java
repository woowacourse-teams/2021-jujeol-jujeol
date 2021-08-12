package com.jujeol.drink.drink.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {

    private String theme;

    public static ThemeDto create(String theme) {
        return new ThemeDto(theme);
    }
}
