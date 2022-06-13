package com.jujeol.member.domain.usecase.command;

import com.jujeol.member.domain.model.Biography;
import com.jujeol.member.domain.model.Nickname;
import com.jujeol.member.domain.model.Provider;
import lombok.Getter;

@Getter
public class MemberRegisterCommand {

    private Provider provider;
    private Nickname nickname;
    private Biography biography;

    private MemberRegisterCommand(Provider provider, Nickname nickname, Biography biography) {
        this.provider = provider;
        this.nickname = nickname;
        this.biography = biography;
    }

    public static MemberRegisterCommand create(Provider provider, Nickname nickname, Biography biography) {
        return new MemberRegisterCommand(provider, nickname, biography);
    }
}
