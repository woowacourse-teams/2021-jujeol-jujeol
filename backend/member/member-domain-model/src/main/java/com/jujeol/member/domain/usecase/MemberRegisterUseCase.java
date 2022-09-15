package com.jujeol.member.domain.usecase;

import com.jujeol.member.domain.exception.DuplicatedMemberException;
import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.domain.usecase.command.MemberRegisterCommand;

import java.util.Optional;

public interface MemberRegisterUseCase {

    void register(MemberRegisterCommand command) throws DuplicatedMemberException;

    interface MemberPort {
        Optional<Member> findByProvideId(String provideId);
        void insertMember(String provideId, ProviderName providerName, String nickname, String biography);
    }
}
