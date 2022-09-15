package com.jujeol.member.controller.request;

import com.jujeol.member.domain.model.ProviderName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private String code;
    private ProviderName providerName;

    @Builder
    public LoginRequest(String code, ProviderName providerName) {
        this.code = code;
        this.providerName = providerName;
    }
}
