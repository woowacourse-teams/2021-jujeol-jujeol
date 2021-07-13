package com.jujeol.member.application.dto;

import com.jujeol.member.domain.ProviderName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenRequest {

    private String code;
    private ProviderName providerName;
}
