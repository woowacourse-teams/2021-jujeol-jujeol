package com.jujeol.drink.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ThemeDto {

    private String theme;

    public static ThemeDto create(String theme) {
        return new ThemeDto(theme);
    }
}
