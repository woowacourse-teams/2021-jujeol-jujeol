package com.jujeol.member.rds.repository;

import com.jujeol.member.domain.model.*;
import com.jujeol.member.domain.usecase.MemberRegisterUseCase;
import com.jujeol.member.rds.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberDomainRepository implements MemberRegisterUseCase.MemberPort {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findByProvideId(String provideId) {
        return memberRepository.findByProvideId(provideId)
            .map(memberEntity -> Member.builder()
                .id(memberEntity.getId())
                .provider(Provider.create(memberEntity.getProvideId(), memberEntity.getProviderName()))
                .nickname(Nickname.create(memberEntity.getNickname()))
                .biography(Biography.create(memberEntity.getBiography()))
                .build());
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
}
