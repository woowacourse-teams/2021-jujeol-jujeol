package com.jujeol.member.ui.dto;

import com.jujeol.member.application.dto.SocialProviderCodeDto;
import com.jujeol.member.domain.ProviderName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SocialProviderCodeRequest {

    private String code;
    private ProviderName providerName;

    public SocialProviderCodeDto toDto() {
        return SocialProviderCodeDto.create(code, providerName);
    }

    public static SocialProviderCodeRequest create(String code, ProviderName providerName) {
        return new SocialProviderCodeRequest(code, providerName);
    }
}
