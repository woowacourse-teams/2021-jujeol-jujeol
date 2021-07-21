package com.jujeol.member.application.dto;

import com.jujeol.member.domain.ProviderName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SocialProviderCodeDto {

    private String code;
    private ProviderName providerName;

    public static SocialProviderCodeDto of(String code, ProviderName providerName){
        return new SocialProviderCodeDto(code, providerName);
    }
}

