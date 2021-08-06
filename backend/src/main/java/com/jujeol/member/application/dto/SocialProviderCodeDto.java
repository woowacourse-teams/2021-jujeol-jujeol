package com.jujeol.member.application.dto;

import com.jujeol.member.domain.ProviderName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class SocialProviderCodeDto {

    private String code;
    private ProviderName providerName;

    public static SocialProviderCodeDto create(String code, ProviderName providerName){
        return new SocialProviderCodeDto(code, providerName);
    }
}

