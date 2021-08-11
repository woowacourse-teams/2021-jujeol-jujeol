package com.jujeol.member.auth.domain;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Provider {

    @Column
    private String provideId;
    @Column
    @Enumerated(EnumType.STRING)
    private ProviderName providerName;

    public static Provider create(String provideId, ProviderName providerName) {
        return new Provider(provideId, providerName);
    }
}
