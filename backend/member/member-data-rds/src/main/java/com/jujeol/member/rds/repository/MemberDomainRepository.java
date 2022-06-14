package com.jujeol.member.rds.repository;

import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.domain.usecase.AuthLoginUseCase;
import com.jujeol.member.domain.usecase.MemberRegisterUseCase;
import com.jujeol.member.rds.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberDomainRepository implements
    MemberRegisterUseCase.MemberPort,

    AuthLoginUseCase.MemberPort {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findByProvideId(String provideId) {
        return memberRepository.findByProvideId(provideId)
            .map(MemberEntity::toDomain);
    }

    @Override
    @Transactional
    public void insertMember(String provideId, ProviderName providerName, String nickname, String biography) {
        MemberEntity memberEntity = MemberEntity.builder()
            .provideId(provideId)
            .providerName(providerName)
            .nickname(nickname)
            .biography(biography)
            .build();
        memberRepository.save(memberEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findByProviderNameAndProvideId(ProviderName providerName, String provideId) {
        return memberRepository.findByProviderNameAndProvideId(providerName, provideId)
            .map(MemberEntity::toDomain);
    }
}
