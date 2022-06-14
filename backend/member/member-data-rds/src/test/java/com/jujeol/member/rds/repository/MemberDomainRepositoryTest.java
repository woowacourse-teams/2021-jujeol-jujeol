package com.jujeol.member.rds.repository;

import com.jujeol.member.RdsTestContext;
import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.model.ProviderName;
import com.jujeol.member.rds.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberDomainRepositoryTest extends RdsTestContext {

    @Autowired
    private MemberDomainRepository sut;

    @Test
    void findByProvideId() {
        // given
        String provideId = "1234";
        String nickname = "nickname";
        String biography = "hello";
        ProviderName providerName = ProviderName.KAKAO;
        MemberEntity savedMember = memberRepository.save(
            MemberEntity.builder()
                .provideId(provideId)
                .providerName(providerName)
                .nickname(nickname)
                .biography(biography)
                .build()
        );

        // when
        Optional<Member> response = sut.findByProvideId(provideId);

        // then
        assertThat(response).isNotEmpty();
        Member foundMember = response.get();
        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getBiography().value()).isEqualTo(biography);
        assertThat(foundMember.getNickname().value()).isEqualTo(nickname);
        assertThat(foundMember.getProvider().getProvideId()).isEqualTo(provideId);
        assertThat(foundMember.getProvider().getProviderName()).isEqualTo(providerName);
    }

    @Test
    void insertMember() {
        // given
        String provideId = "1234";
        String nickname = "nickname";
        String biography = "hello";
        ProviderName providerName = ProviderName.KAKAO;

        // when
        sut.insertMember(provideId, providerName, nickname, biography);

        // then
        Optional<MemberEntity> foundMember =
            memberRepository.findAll()
                .stream()
                .findAny();
        assertThat(foundMember).isNotEmpty();
        MemberEntity member = foundMember.get();
        assertThat(member.getId()).isNotNull();
        assertThat(member.getBiography()).isEqualTo(biography);
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getProvideId()).isEqualTo(provideId);
        assertThat(member.getProviderName()).isEqualTo(providerName);
    }

    @Test
    void findByProviderNameAndProvideId() {
        // given
        String provideId = "1234";
        String nickname = "nickname";
        String biography = "hello";
        ProviderName providerName = ProviderName.KAKAO;
        MemberEntity savedMember = memberRepository.save(
            MemberEntity.builder()
                .provideId(provideId)
                .providerName(providerName)
                .nickname(nickname)
                .biography(biography)
                .build()
        );

        // when
        Optional<Member> response = sut.findByProviderNameAndProvideId(providerName, provideId);

        // then
        assertThat(response).isNotEmpty();
        Member foundMember = response.get();
        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getBiography().value()).isEqualTo(biography);
        assertThat(foundMember.getNickname().value()).isEqualTo(nickname);
        assertThat(foundMember.getProvider().getProvideId()).isEqualTo(provideId);
        assertThat(foundMember.getProvider().getProviderName()).isEqualTo(providerName);
    }
}