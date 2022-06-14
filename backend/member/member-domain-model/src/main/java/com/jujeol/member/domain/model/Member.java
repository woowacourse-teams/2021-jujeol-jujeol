package com.jujeol.member.domain.model;

import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final Provider provider;
    private final Nickname nickname;
    private final Biography biography;

    private Member(Long id, Provider provider, Nickname nickname, Biography biography) {
        this.id = id;
        this.provider = provider;
        this.nickname = nickname;
        this.biography = biography;
    }

    public static Member create(Long id, Provider provider, Nickname nickname, Biography biography) {
        return new Member(id, provider, nickname, biography);
    }

    public static Member create(Long id, String provideId, ProviderName providerName, String nickname, String biography) {
        return new Member(id, Provider.create(provideId, providerName), Nickname.create(nickname), Biography.create(biography));
    }
}
