package com.jujeol.member.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class PreferenceDto {

    private double preferenceRate;

    public static PreferenceDto of(double preferenceRate) {
        return new PreferenceDto(preferenceRate);
    }
}


