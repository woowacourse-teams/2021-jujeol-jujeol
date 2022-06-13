package com.jujeol.member.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private Long id;
    private Provider provider;
    private Nickname nickname;
    private Biography biography;

    @Builder
    public Member(Long id, Provider provider, Nickname nickname, Biography biography) {
        this.id = id;
        this.provider = provider;
        this.nickname = nickname;
        this.biography = biography;
    }
}
