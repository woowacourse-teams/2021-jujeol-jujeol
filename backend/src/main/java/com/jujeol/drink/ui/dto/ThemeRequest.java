package com.jujeol.drink.ui.dto;

import com.jujeol.drink.application.dto.ThemeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeRequest {

    private String theme;

    public ThemeDto toDto() {
        return ThemeDto.create(theme);
    }
}
