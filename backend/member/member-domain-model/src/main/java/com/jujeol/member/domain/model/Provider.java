package com.jujeol.member.domain.model;

import lombok.Getter;

@Getter
public class Provider {

    private final String provideId;
    private final ProviderName providerName;

    private Provider(String provideId, ProviderName providerName) {
        this.provideId = provideId;
        this.providerName = providerName;
    }

    public static Provider create(String provideId, ProviderName providerName) {
        return new Provider(provideId, providerName);
    }
}
