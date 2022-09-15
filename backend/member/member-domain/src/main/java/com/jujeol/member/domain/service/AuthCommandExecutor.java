package com.jujeol.member.domain.service;

import com.jujeol.member.domain.exception.NotFoundAuthException;
import com.jujeol.member.domain.model.Provider;
import com.jujeol.member.domain.model.Token;
import com.jujeol.member.domain.model.TokenProvider;
import com.jujeol.member.domain.usecase.AuthLoginUseCase;
import com.jujeol.member.domain.usecase.command.AuthLoginCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthCommandExecutor implements AuthLoginUseCase {

    private final TokenProvider tokenProvider;
    private final AuthLoginUseCase.MemberPort memberPort;

    @Override
    @Transactional(readOnly = true)
    public Token login(AuthLoginCommand authLoginCommand) throws NotFoundAuthException {
        Provider provider = authLoginCommand.getProvider();
        String payload = memberPort.findByProviderNameAndProvideId(provider.getProviderName(), provider.getProvideId())
            .orElseThrow(NotFoundAuthException::new)
            .getId()
            .toString();
        return Token.create(tokenProvider.createToken(payload));
    }
}
