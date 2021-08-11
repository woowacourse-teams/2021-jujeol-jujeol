package com.jujeol.preference.ui.dto;

import com.jujeol.member.member.application.dto.PreferenceDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PreferenceRequest {

    private double preferenceRate;

    public PreferenceDto toDto() {
        return PreferenceDto.create(preferenceRate);
    }
}
