package com.jujeol.member.domain.usecase;

import com.jujeol.member.domain.exception.NotFoundAuthException;
import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.domain.model.Token;
import com.jujeol.member.domain.usecase.command.AuthLoginCommand;

import java.util.Optional;

public interface AuthLoginUseCase {

    Token login(AuthLoginCommand authLoginCommand) throws NotFoundAuthException;

    interface MemberPort {
        Optional<Member> findByProviderNameAndProvideId(ProviderName providerName, String provideId);
    }
}
