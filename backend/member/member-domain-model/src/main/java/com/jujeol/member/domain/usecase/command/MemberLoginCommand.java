package com.jujeol.member.domain.usecase.command;

import com.jujeol.member.domain.model.Provider;
import lombok.Getter;

@Getter
public class MemberLoginCommand {

    private Provider provider;

    public MemberLoginCommand(Provider provider) {
        this.provider = provider;
    }

    public static MemberLoginCommand create(Provider provider) {
        return new MemberLoginCommand(provider);
    }
}
