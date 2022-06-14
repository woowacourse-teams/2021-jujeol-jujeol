package com.jujeol.member.domain.usecase.command;

import com.jujeol.member.domain.model.Provider;
import lombok.Getter;

@Getter
public class AuthLoginCommand {

    private Provider provider;

    public AuthLoginCommand(Provider provider) {
        this.provider = provider;
    }

    public static AuthLoginCommand create(Provider provider) {
        return new AuthLoginCommand(provider);
    }
}
