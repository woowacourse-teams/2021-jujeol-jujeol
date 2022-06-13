package com.jujeol.member.domain.service;

import com.jujeol.member.domain.exception.DuplicatedMemberException;
import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.domain.usecase.MemberRegisterUseCase;
import com.jujeol.member.domain.usecase.command.MemberRegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberCommandExecutor implements MemberRegisterUseCase {

    private final MemberRegisterUseCase.MemberPort memberPort;

    @Override
    @Transactional
    public void register(MemberRegisterCommand command) throws DuplicatedMemberException {
        String provideId = command.getProvider().getProvideId();
        ProviderName providerName = command.getProvider().getProviderName();
        String nickname = command.getNickname().value();
        String biography = command.getBiography().value();

        Optional<Member> optionalMember = memberPort.findByProvideId(provideId);
        if (optionalMember.isPresent()) {
            throw new DuplicatedMemberException();
        }

        memberPort.insertMember(provideId, providerName, nickname, biography);
    }
}
