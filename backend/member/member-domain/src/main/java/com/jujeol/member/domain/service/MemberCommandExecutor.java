package com.jujeol.member.domain.service;

import com.jujeol.member.domain.exception.DuplicatedMemberException;
import com.jujeol.member.domain.exception.DuplicatedNicknameException;
import com.jujeol.member.domain.exception.NotExistMemberException;
import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.domain.usecase.MemberRegisterUseCase;
import com.jujeol.member.domain.usecase.MemberUpdateUseCase;
import com.jujeol.member.domain.usecase.command.MemberRegisterCommand;
import com.jujeol.member.domain.usecase.command.MemberUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberCommandExecutor implements
    MemberRegisterUseCase,
    MemberUpdateUseCase {

    private final MemberRegisterUseCase.MemberPort registerMemberPort;
    private final MemberUpdateUseCase.MemberPort updateMemberPort;

    @Override
    @Transactional
    public void register(MemberRegisterCommand command) {
        String provideId = command.getProvider().getProvideId();
        ProviderName providerName = command.getProvider().getProviderName();
        String nickname = command.getNickname().value();
        String biography = command.getBiography().value();

        Optional<Member> optionalMember = registerMemberPort.findByProvideId(provideId);
        if (optionalMember.isPresent()) {
            throw new DuplicatedMemberException();
        }

        registerMemberPort.insertMember(provideId, providerName, nickname, biography);
    }

    @Override
    @Transactional
    public void update(MemberUpdateCommand command) {
        updateMemberPort.findById(command.getId()).orElseThrow(NotExistMemberException::new);
        if (updateMemberPort.existsByNickname(command.getNickname().value())) {
            throw new DuplicatedNicknameException();
        }

        updateMemberPort.update(command.getId(), command.getNickname().value(), command.getBio().value());
    }
}
