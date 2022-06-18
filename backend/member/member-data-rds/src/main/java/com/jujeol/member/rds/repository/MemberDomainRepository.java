package com.jujeol.member.rds.repository;

import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.domain.reader.MemberReader;
import com.jujeol.member.domain.usecase.AuthLoginUseCase;
import com.jujeol.member.domain.usecase.MemberRegisterUseCase;
import com.jujeol.member.domain.usecase.MemberUpdateUseCase;
import com.jujeol.member.rds.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberDomainRepository implements
    MemberReader,
    MemberRegisterUseCase.MemberPort,
    MemberUpdateUseCase.MemberPort,

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

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId).map(MemberEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Override
    @Transactional
    public void update(Long id, String nickname, String bio) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow();
        memberEntity.update(nickname, bio);
    }
}
